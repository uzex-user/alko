import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static int h;
    static int w;
    static int nodeCount;
    static int source;
    static int target;

    static int[][][] edgeIds;
    static List<Edge>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = reader.readLine().split(" ");
        h = Integer.parseInt(parts[0]);
        w = Integer.parseInt(parts[1]);

        parts = reader.readLine().split(" ");
        int b = Integer.parseInt(parts[0]);
        int p = Integer.parseInt(parts[1]);

        boolean[][] blocked = new boolean[h][w];
        boolean[][] optional = new boolean[h][w];
        edgeIds = new int[h][w][2];

        for (int i = 0; i < b; i++) {
            parts = reader.readLine().split(" ");
            int x = Integer.parseInt(parts[0]) - 1;
            int y = Integer.parseInt(parts[1]) - 1;
            blocked[x][y] = true;
        }

        for (int i = 0; i < p; i++) {
            parts = reader.readLine().split(" ");
            int x = Integer.parseInt(parts[0]) - 1;
            int y = Integer.parseInt(parts[1]) - 1;
            optional[x][y] = true;
        }

        parts = reader.readLine().split(" ");
        int sx = Integer.parseInt(parts[0]) - 1;
        int sy = Integer.parseInt(parts[1]) - 1;

        parts = reader.readLine().split(" ");
        int tx = Integer.parseInt(parts[0]) - 1;
        int ty = Integer.parseInt(parts[1]) - 1;

        nodeCount = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (!blocked[i][j]) {
                    edgeIds[i][j][0] = nodeCount++;
                    edgeIds[i][j][1] = nodeCount++;
                }
            }
        }

        graph = new ArrayList[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            graph[i] = new ArrayList<>();
        }

        source = edgeIds[sx][sy][0];
        target = edgeIds[tx][ty][1];

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (blocked[i][j]) {
                    continue;
                }

                int in = edgeIds[i][j][0];
                int out = edgeIds[i][j][1];
                int cap = optional[i][j] ? 1 : INF;

                graph[in].add(new Edge(out, graph[out].size(), cap));
                graph[out].add(new Edge(in, graph[in].size() - 1, 0));

                for (int d = 0; d < 4; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];

                    if (ni >= 0 && ni < h && nj >= 0 && nj < w && !blocked[ni][nj]) {
                        int neighborIn = edgeIds[ni][nj][0];
                        graph[out].add(new Edge(neighborIn, graph[neighborIn].size(), INF));
                        graph[neighborIn].add(new Edge(out, graph[out].size() - 1, 0));
                    }
                }
            }
        }

        int flow = dinic(source, target);

        if (flow >= INF) {
            System.out.println(-1);
            return;
        }

        System.out.println(flow);

        boolean[] visited = new boolean[nodeCount];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Edge e : graph[u]) {
                if (e.cap > 0 && !visited[e.to]) {
                    visited[e.to] = true;
                    queue.add(e.to);
                }
            }
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (blocked[i][j] || !optional[i][j]) {
                    continue;
                }

                int in = edgeIds[i][j][0];
                int out = edgeIds[i][j][1];

                if (visited[in] && !visited[out]) {
                    result.add(new int[]{i + 1, j + 1});
                }
            }
        }

        for (int[] coords : result) {
            System.out.println(coords[0] + " " + coords[1]);
        }
    }

    static int bfs(int[] level, int s, int t) {
        Arrays.fill(level, -1);
        level[s] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Edge e : graph[u]) {
                if (e.cap > 0 && level[e.to] == -1) {
                    level[e.to] = level[u] + 1;
                    queue.add(e.to);
                }
            }
        }

        return level[t];
    }

    static int dfs(int u, int t, int flow, int[] level, int[] ptr) {
        if (u == t || flow == 0) {
            return flow;
        }

        List<Edge> edges = graph[u];
        for (; ptr[u] < edges.size(); ptr[u]++) {
            Edge e = edges.get(ptr[u]);
            if (level[e.to] == level[u] + 1 && e.cap > 0) {
                int pushed = dfs(e.to, t, Math.min(flow, e.cap), level, ptr);
                if (pushed > 0) {
                    e.cap -= pushed;
                    graph[e.to].get(e.rev).cap += pushed;
                    return pushed;
                }
            }
        }

        return 0;
    }

    static int dinic(int s, int t) {
        int flow = 0;
        int[] level = new int[nodeCount];
        int[] ptr = new int[nodeCount];

        while (bfs(level, s, t) != -1) {
            Arrays.fill(ptr, 0);
            int pushed;
            while ((pushed = dfs(s, t, INF, level, ptr)) > 0) {
                flow += pushed;
            }
        }

        return flow;
    }
}

class Edge {
    int to;
    int rev;
    int cap;

    Edge(int to, int rev, int cap) {
        this.to = to;
        this.rev = rev;
        this.cap = cap;
    }
}
