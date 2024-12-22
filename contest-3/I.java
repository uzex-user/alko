import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class I {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = reader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int q = Integer.parseInt(firstLine[1]);

        int[] array = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        SegmentTreeI segmentTree = new SegmentTreeI(array);

        for (int i = 0; i < q; i++) {
            String[] query = reader.readLine().split(" ");
            int left = Integer.parseInt(query[0]) - 1;
            int right = Integer.parseInt(query[1]) - 1;
            int x = Integer.parseInt(query[2]);
            int y = Integer.parseInt(query[3]);
            writer.println(segmentTree.query(left, right, x, y));
        }

        writer.close();
    }
}

class SegmentTreeI {
    private final List<Integer>[] tree;
    private final int n;

    public SegmentTreeI(int[] array) {
        n = array.length;
        tree = new List[4 * n];
        build(array, 0, 0, n - 1);
    }

    private void build(int[] array, int node, int start, int end) {
        if (start == end) {
            tree[node] = new ArrayList<>();
            tree[node].add(array[start]);
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;
            build(array, leftChild, start, mid);
            build(array, rightChild, mid + 1, end);
            tree[node] = merge(tree[leftChild], tree[rightChild]);
        }
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }
        return merged;
    }

    public int query(int left, int right, int first, int second) {
        return query(0, 0, n - 1, left, right, first, second);
    }

    private int query(int node, int start, int end, int left, int right, int first, int second) {
        if (start > right || end < left) {
            return 0;
        }

        if (start >= left && end <= right) {
            return countInRange(tree[node], first, second);
        }

        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        return query(leftChild, start, mid, left, right, first, second)
            + query(rightChild, mid + 1, end, left, right, first, second);
    }

    private int upperBound(List<Integer> list, int value) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (list.get(mid) > value) high = mid;
            else low = mid + 1;
        }
        return low;
    }

    private int lowerBound(List<Integer> list, int value) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (list.get(mid) >= value) high = mid;
            else low = mid + 1;
        }
        return low;
    }

    private int countInRange(List<Integer> list, int x, int y) {
        int left = lowerBound(list, x);
        int right = upperBound(list, y);
        return right - left;
    }
}
