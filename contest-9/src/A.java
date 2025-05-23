import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A {
    static List<Integer>[] graph;
    static int[] mt;
    static boolean[] used;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        int leftShare = Integer.parseInt(input[0]);
        int rightShare = Integer.parseInt(input[1]);

        mt = new int[rightShare];
        Arrays.fill(mt, -1);
        graph = new ArrayList[leftShare];
        for (int i = 0; i < leftShare; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < leftShare; i++) {
            input = reader.readLine().split(" ");

            for (String elem : input) {
                if (!elem.equals("0")) {
                    graph[i].add(Integer.parseInt(elem) - 1);
                }
            }
        }

        for (int v = 0; v < leftShare; v++) {
            used = new boolean[leftShare];
            Arrays.fill(used, false);
            tryKuhn(v);
        }

        int res = 0;
        for (int i = 0; i < rightShare; i++) {
            if (mt[i] != -1) {
                res += 1;
            }
        }

        System.out.println(res);

        for (int i = 0; i < rightShare; i++) {
            if (mt[i] != -1) {
                System.out.println((mt[i] + 1) + " " + (i + 1));
            }
        }
    }

    static boolean tryKuhn(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;

        for (int to : graph[v]) {
            if (mt[to] == -1 || tryKuhn(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }
}
