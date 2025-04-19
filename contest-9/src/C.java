import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int m;
    static List<Integer>[] adj;
    static int[] pairU;
    static int[] pairV;
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        adj = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokenizer.nextToken());
            int v = Integer.parseInt(tokenizer.nextToken());
            adj[u].add(v);
        }

        pairU = new int[n + 1];
        pairV = new int[n + 1];
        dist = new int[n + 1];

        int matching = hopcroftKarp();
        System.out.println(n - matching);
    }

    private static int hopcroftKarp() {
        int result = 0;
        while (bfs()) {
            for (int u = 1; u <= n; u++) {
                if (pairU[u] == 0 && dfs(u)) {
                    result++;
                }
            }
        }
        return result;
    }

    private static boolean bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int u = 1; u <= n; u++) {
            if (pairU[u] == 0) {
                dist[u] = 0;
                queue.add(u);
            } else {
                dist[u] = Integer.MAX_VALUE;
            }
        }

        int distNil = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (dist[u] < distNil) {
                for (int v : adj[u]) {
                    if (pairV[v] == 0) {
                        distNil = dist[u] + 1;
                    } else if (dist[pairV[v]] == Integer.MAX_VALUE) {
                        dist[pairV[v]] = dist[u] + 1;
                        queue.add(pairV[v]);
                    }
                }
            }
        }
        return distNil != Integer.MAX_VALUE;
    }

    private static boolean dfs(int u) {
        for (int v : adj[u]) {
            int pu = pairV[v];
            if (pu == 0 || dist[pu] == dist[u] + 1 && dfs(pu)) {
                pairU[u] = v;
                pairV[v] = u;
                return true;
            }
        }
        dist[u] = Integer.MAX_VALUE;
        return false;
    }
}
