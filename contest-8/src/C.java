import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class C {
  static class Edge {
    int to;
    int cost;
    int time;

    public Edge(int to, int cost, int time) {
      this.to = to;
      this.cost = cost;
      this.time = time;
    }
  }

  static List<Edge>[] graph;
  static long[][] dp;
  static int[][] prev;

  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    String[] line = r.readLine().split(" ");
    int n = Integer.parseInt(line[0]);
    int m = Integer.parseInt(line[1]);
    int maxTime = Integer.parseInt(line[2]);

    graph = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int i = 0; i < m; i++) {
      line = r.readLine().split(" ");
      int u = Integer.parseInt(line[0]) - 1;
      int v = Integer.parseInt(line[1]) - 1;
      int cost = Integer.parseInt(line[2]);
      int time = Integer.parseInt(line[3]);

      graph[u].add(new Edge(v, cost, time));
      graph[v].add(new Edge(u, cost, time));
    }

    dp = new long[n][maxTime + 1];
    prev = new int[n][maxTime + 1];
    for (int i = 0; i < n; i++) {
      Arrays.fill(dp[i], Long.MAX_VALUE);
    }
    for (int i = 0; i < n; i++) {
      Arrays.fill(prev[i], -1);
    }
    dp[0][0] = 0;

    PriorityQueue<long[]> heap = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
    heap.offer(new long[] {0, 0, 0});

    while (!heap.isEmpty()) {
      long[] cur = heap.poll();
      long cost = cur[0];
      int u = (int) cur[1];
      int t = (int) cur[2];

      if (dp[u][t] < cost) {
        continue;
      }

      for (Edge e : graph[u]) {
        int v = e.to;
        int newTime = t + e.time;
        long newCost = cost + e.cost;

        if (newTime <= maxTime && dp[v][newTime] > newCost) {
          dp[v][newTime] = newCost;
          prev[v][newTime] = u * 10000 + t;
          heap.offer(new long[] {newCost, v, newTime});
        }
      }
    }

    long min = Long.MAX_VALUE;
    int bestTime = -1;
    for (int t = 0; t <= maxTime; t++) {
      if (dp[n - 1][t] < min) {
        min = dp[n - 1][t];
        bestTime = t;
      }
    }

    if (min == Long.MAX_VALUE) {
      System.out.println(-1);
    } else {
      System.out.println(min);
      List<Integer> path = new ArrayList<>();
      int node = n - 1;
      int time = bestTime;

      while (node != -1) {
        path.add(node + 1);
        int p = prev[node][time];
        if (p == -1) break;
        int prevNode = p / 10000;
        int prevTime = p % 10000;
        node = prevNode;
        time = prevTime;
      }

      Collections.reverse(path);
      System.out.println(path.size());
      for (int v : path) {
        System.out.print(v + " ");
      }
    }
  }
}
