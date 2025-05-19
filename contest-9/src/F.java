import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class F {
    static final int MAX_VERTICES = 500;
    static long[][] capacityMatrix = new long[MAX_VERTICES][MAX_VERTICES];
    static long[][] flowMatrix = new long[MAX_VERTICES][MAX_VERTICES];
    static List<Integer>[] graph = new ArrayList[MAX_VERTICES];
    static int[][] inputEdges;
    static int vertexCount;
    static int edgeCount;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] header = reader.readLine().split(" ");
        vertexCount = Integer.parseInt(header[0]);
        edgeCount = Integer.parseInt(header[1]);

        inputEdges = new int[edgeCount][3];
        for (int i = 0; i < vertexCount; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int ei = 0; ei < edgeCount; ei++) {
            String[] parts = reader.readLine().split(" ");
            int from = Integer.parseInt(parts[0]) - 1;
            int to = Integer.parseInt(parts[1]) - 1;
            int capacity = Integer.parseInt(parts[2]);

            capacityMatrix[from][to] += capacity;
            capacityMatrix[to][from] += capacity;

            graph[from].add(to);
            graph[to].add(from);

            inputEdges[ei][0] = from;
            inputEdges[ei][1] = to;
            inputEdges[ei][2] = capacity;
        }

        long maxFlow = computeMaxFlow(vertexCount - 1);

        boolean[] visited = new boolean[vertexCount];
        dfsMarkReachable(0, visited);

        List<Integer> cutEdges = new ArrayList<>();
        for (int ei = 0; ei < edgeCount; ei++) {
            int u = inputEdges[ei][0];
            int v = inputEdges[ei][1];
            if (visited[u] != visited[v]) {
                cutEdges.add(ei + 1);
            }
        }

        Collections.sort(cutEdges);
        System.out.println(cutEdges.size() + " " + maxFlow);
        for (int i = 0; i < cutEdges.size(); i++) {
            System.out.print(cutEdges.get(i));
            if (i < cutEdges.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    static long computeMaxFlow(int sink) {
        long flowValue = 0;
        int[] parent = new int[vertexCount];

        while (parent[sink] != -1) {
            Arrays.fill(parent, -1);
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(0);
            parent[0] = 0;

            while (!queue.isEmpty() && parent[sink] == -1) {
                int u = queue.poll();
                for (int v : graph[u]) {
                    if (parent[v] == -1
                        && capacityMatrix[u][v] > flowMatrix[u][v]) {
                        parent[v] = u;
                        queue.add(v);
                    }
                }
            }

            long addFlow = Long.MAX_VALUE;
            for (int v = sink; v != 0; v = parent[v]) {
                int u = parent[v];
                addFlow = Math.min(addFlow,
                    capacityMatrix[u][v] - flowMatrix[u][v]);
            }

            for (int v = sink; v != 0; v = parent[v]) {
                int u = parent[v];
                flowMatrix[u][v] += addFlow;
                flowMatrix[v][u] -= addFlow;
            }

            flowValue += addFlow;
        }
        return flowValue;
    }

    static void dfsMarkReachable(int u, boolean[] visited) {
        visited[u] = true;
        for (int v : graph[u]) {
            if (!visited[v]
                && capacityMatrix[u][v] > flowMatrix[u][v]) {
                dfsMarkReachable(v, visited);
            }
        }
    }
}
