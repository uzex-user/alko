import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class A {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(reader.readLine());

    while (t-- > 0) {
      String[] nm = reader.readLine().split(" ");
      int n = Integer.parseInt(nm[0]);
      int m = Integer.parseInt(nm[1]);

      graph = new ArrayList[n];
      for (int i = 0; i < n; i++) {
        graph[i] = new ArrayList<>();
      }

      for (int i = 0; i < m; i++) {
        String[] parts = reader.readLine().split(" ");
        int u = Integer.parseInt(parts[0]);
        int v = Integer.parseInt(parts[1]);
        int c = Integer.parseInt(parts[2]);

        if (u == v) continue;

        graph[u].add(new Edge(v, c));
        graph[v].add(new Edge(u, c));
      }

      int s = Integer.parseInt(reader.readLine());

      solution(n, s);

      for (int i = 0; i < n; i++) {
        System.out.print(distance[i] + " ");
      }
      System.out.println();
    }
  }

  private static final long LONG = 2009000999L;
  static class Edge {
    int to;
    int weight;

    public Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
  }

  static List<Edge>[] graph;
  static long[] distance;

  static void solution(int n, int start) {
    distance = new long[n];
    Arrays.fill(distance, LONG);
    distance[start] = 0;

    PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
    heap.offer(new long[] {0, start});

    while (!heap.isEmpty()) {
      long[] current = heap.poll();
      long dist = current[0];
      int u = (int) current[1];

      if (dist > distance[u]) {
        continue;
      }

      for (Edge edge : graph[u]) {
        int v = edge.to;
        long newDist = dist + edge.weight;
        if (newDist < distance[v]) {
          distance[v] = newDist;
          heap.offer(new long[] {newDist, v});
        }
      }
    }
  }
}
