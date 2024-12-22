import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

class FenwickTree2D {
    private List<Integer> yCoords = new ArrayList<>();
    private List<Long> weights = new ArrayList<>();
    private int size;

    private static int lowerBit(int num) {
        return num & (num + 1);
    }

    private static int nextIndex(int num) {
        return num | (num + 1);
    }

    private int findIndex(int targetY) {
        int left = 0;
        int right = size;

        while (right - left > 1) {
            int middle = (right + left) >> 1;
            if (yCoords.get(middle) > targetY) {
                right = middle;
            } else {
                left = middle;
            }
        }

        if (yCoords.get(left) > targetY) {
            return left - 1;
        }
        return left;
    }

    public void addCoordinate(int value) {
        yCoords.add(value);
    }

    public void updateWeight(int y, long delta) {
        int index = findIndex(y);
        while (index < weights.size()) {
            weights.set(index, weights.get(index) + delta);
            index = nextIndex(index);
        }
    }

    public long getWeight(int y) {
        int index = findIndex(y);
        long result = 0;
        while (index >= 0) {
            result += weights.get(index);
            index = lowerBit(index) - 1;
        }
        return result;
    }

    public void build() {
        Collections.sort(yCoords);
        yCoords = new ArrayList<>(new TreeSet<>(yCoords));
        size = yCoords.size();
        weights = new ArrayList<>(Collections.nCopies(size, 0L));
    }
}

public class K {
    private static List<Integer> xCoords = new ArrayList<>();

    private static int lowerBit(int num) {
        return num & (num + 1);
    }

    private static int nextIndex(int num) {
        return num | (num + 1);
    }

    private static int findIndex(int targetX) {
        int left = 0;
        int right = xCoords.size();

        while (right - left > 1) {
            int middle = (right + left) >> 1;
            if (xCoords.get(middle) > targetX) {
                right = middle;
            } else {
                left = middle;
            }
        }

        if (xCoords.get(left) > targetX) {
            return left - 1;
        }
        return left;
    }

    private static void update2DFenwick(List<FenwickTree2D> fenwick, int x, int y, long delta) {
        int indexX = findIndex(x);
        while (indexX < fenwick.size()) {
            fenwick.get(indexX).updateWeight(y, delta);
            indexX = nextIndex(indexX);
        }
    }

    private static long query2DFenwick(List<FenwickTree2D> fenwick, int x, int y) {
        int indexX = findIndex(x);
        long result = 0;
        while (indexX >= 0) {
            result += fenwick.get(indexX).getWeight(y);
            indexX = lowerBit(indexX) - 1;
        }
        return result;
    }

    private static void addCoordinateToFenwick(List<FenwickTree2D> fenwick, int x, int y) {
        int indexX = findIndex(x);
        while (indexX < fenwick.size()) {
            fenwick.get(indexX).addCoordinate(y);
            indexX = nextIndex(indexX);
        }
    }

    private static void buildFenwick(List<FenwickTree2D> fenwick) {
        for (FenwickTree2D tree : fenwick) {
            tree.build();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        List<Pair<Integer, Integer>> points = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            int weight = Integer.parseInt(input[2]);
            points.add(new Pair<>(x, y));
            weights.add(weight);
            xCoords.add(x);
        }

        Collections.sort(xCoords);
        xCoords = new ArrayList<>(new TreeSet<>(xCoords));

        List<FenwickTree2D> fenwickTree = new ArrayList<>();
        for (int i = 0; i < xCoords.size(); i++) {
            fenwickTree.add(new FenwickTree2D());
        }

        int i = 0;
        while (i < n) {
            addCoordinateToFenwick(fenwickTree, points.get(i).getKey(), points.get(i).getValue());
            i++;
        }

        buildFenwick(fenwickTree);

        i = 0;
        while (i < n) {
            update2DFenwick(
                fenwickTree, points.get(i).getKey(), points.get(i).getValue(), weights.get(i));
            i++;
        }

        int q = Integer.parseInt(reader.readLine());
        while (q-- > 0) {
            String[] query = reader.readLine().split(" ");
            String command = query[0];
            int param1 = Integer.parseInt(query[1]);
            int param2 = Integer.parseInt(query[2]);
            if (command.equals("get")) {
                System.out.println(query2DFenwick(fenwickTree, param1, param2));
            } else if (command.equals("change")) {
                update2DFenwick(
                    fenwickTree,
                    points.get(param1 - 1).getKey(),
                    points.get(param1 - 1).getValue(),
                    param2 - weights.get(param1 - 1));
                weights.set(param1 - 1, param2);
            }
        }
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
