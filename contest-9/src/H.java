import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class H {
    static final int INF = Integer.MAX_VALUE;

    static int n;
    static int m;
    static List<NodeEdge>[] edges;
    static List<NodeEdge> originalEdges = new ArrayList<>();

    static int[] level;
    static int[] ptr;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] first = reader.readLine().split(" ");

        n = Integer.parseInt(first[0]);
        m = Integer.parseInt(first[1]);

        edges = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            String[] line = reader.readLine().split(" ");
            int from = Integer.parseInt(line[0]) - 1;
            int to = Integer.parseInt(line[1]) - 1;
            int cap = Integer.parseInt(line[2]);

            NodeEdge forward = new NodeEdge(to, edges[to].size(), cap);
            NodeEdge backward = new NodeEdge(from, edges[from].size(), 0);

            edges[from].add(forward);
            edges[to].add(backward);

            originalEdges.add(forward);
        }

        long maxFlow = dinic(0, n - 1);
        System.out.println(maxFlow);

        for (NodeEdge e : originalEdges) {
            System.out.println(e.flow);
        }
    }

    static boolean bfs(int start, int end) {
        level = new int[n];
        Arrays.fill(level, -1);
        level[start] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (NodeEdge e : edges[u]) {
                if (e.capacity > e.flow && level[e.to] == -1) {
                    level[e.to] = level[u] + 1;
                    queue.add(e.to);
                }
            }
        }

        return level[end] != -1;
    }

    static int dfs(int u, int end, int flowToPush) {
        if (u == end || flowToPush == 0) {
            return flowToPush;
        }

        for (; ptr[u] < edges[u].size(); ptr[u]++) {
            NodeEdge e = edges[u].get(ptr[u]);

            if (level[e.to] == level[u] + 1 && e.capacity > e.flow) {
                int pushed = dfs(e.to, end, Math.min(flowToPush, e.capacity - e.flow));
                if (pushed > 0) {
                    e.flow += pushed;
                    edges[e.to].get(e.revIndex).flow -= pushed;
                    return pushed;
                }
            }
        }

        return 0;
    }

    static long dinic(int start, int end) {
        long totalFlow = 0L;

        while (bfs(start, end)) {
            ptr = new int[n];

            int pushed;
            while ((pushed = dfs(start, end, INF)) > 0) {
                totalFlow += pushed;
            }
        }

        return totalFlow;
    }
}

class NodeEdge {
    int to;
    int revIndex;
    int capacity;
    int flow;

    NodeEdge(int to, int revIndex, int capacity) {
        this.to = to;
        this.revIndex = revIndex;
        this.capacity = capacity;
        this.flow = 0;
    }
}
