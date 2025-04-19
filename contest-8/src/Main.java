import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        int[][] lectures = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                lectures[i][j] = Integer.parseInt(line[j]);
            }
        }

        String[] input = reader.readLine().split(" ");
        int[] cost = new int[n];
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.parseInt(input[i]);
        }

        boolean[] visited = new boolean[n];
        int result = 0;
        PriorityQueue<Edge> heap = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.weight, e2.weight));

        for (int i = 0; i < n; i++) {
            heap.add(new Edge(i, -1, cost[i]));
        }

        while (!heap.isEmpty()) {
            Edge edge = heap.poll();
            int u = edge.u;
            int v = edge.v;

            if (visited[u] || (v != -1 && visited[v])) {
                continue;
            }

            result += edge.weight;
            visited[u] = true;
            if (v != -1) {
                for (int i = 0; i < n; i++) {
                    if (!visited[i]) {
                        heap.add(new Edge(i, u, lectures[u][i]));
                    }
                }
            }
        }

        System.out.println(result);
    }

    static class Edge {
        int u;
        int v;
        int weight;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.weight = w;
        }
    }
}
