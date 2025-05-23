import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class K {
    static final long INF = Long.MAX_VALUE;
    static List<Node>[] edges;
    static long[] potential;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        int size = Integer.parseInt(input[0]);
        int query = Integer.parseInt(input[1]);

        edges = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < query; i++) {
            String[] line = reader.readLine().split(" ");
            int from = Integer.parseInt(line[0]) - 1;
            int to = Integer.parseInt(line[1]) - 1;
            int cap = Integer.parseInt(line[2]);
            int cost = Integer.parseInt(line[3]);

            Node forward = new Node(to, edges[to].size(), cap, cost);
            Node backward = new Node(from, edges[from].size(), 0, -cost);

            edges[from].add(forward);
            edges[to].add(backward);
            forward.revIndex = edges[to].size() - 1;
            backward.revIndex = edges[from].size() - 1;
        }

        System.out.println(minCostMaxFlow(size - 1, size));
    }

    static long minCostMaxFlow(int sink, int size) {
        long cost = 0;
        potential = new long[size];
        long[] dist = new long[size];
        Node[] prev = new Node[size];
        int[] prevVertex = new int[size];

        while (dist[sink] != INF) {
            Arrays.fill(dist, INF);
            dist[0] = 0;

            PriorityQueue<long[]> heap = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
            heap.add(new long[]{0, 0});

            while (!heap.isEmpty()) {
                long[] cur = heap.poll();
                int u = (int) cur[0];
                long d = cur[1];

                if (d > dist[u]) {
                    continue;
                }

                for (int i = 0; i < edges[u].size(); i++) {
                    Node edge = edges[u].get(i);
                    if (edge.flow < edge.capacity) {
                        long costWithPotential = edge.cost + potential[u] - potential[edge.to];
                        if (dist[edge.to] > dist[u] + costWithPotential) {
                            dist[edge.to] = dist[u] + costWithPotential;
                            prev[edge.to] = edge;
                            prevVertex[edge.to] = u;
                            heap.add(new long[]{edge.to, dist[edge.to]});
                        }
                    }
                }
            }

            for (int i = 0; i < size; i++) {
                if (dist[i] < INF) {
                    potential[i] += dist[i];
                }
            }

            long delta = INF;
            for (int v = sink; v != 0; v = prevVertex[v]) {
                Node edge = prev[v];
                delta = Math.min(delta, edge.capacity - edge.flow);
            }

            for (int v = sink; v != 0; v = prevVertex[v]) {
                Node edge = prev[v];
                edge.flow += delta;
                edges[edge.to].get(edge.revIndex).flow -= delta;
                cost += delta * edge.cost;
            }
        }

        return cost;
    }

}

class Node {
    int to;
    int revIndex;
    long capacity;
    long flow;
    long cost;

    Node(int to, int revIndex, int capacity, int cost) {
        this.to = to;
        this.revIndex = revIndex;
        this.capacity = capacity;
        this.flow = 0;
        this.cost = cost;
    }
}
