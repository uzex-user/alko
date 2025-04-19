import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class D {
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

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] nm = reader.readLine().split(" ");
    int n = Integer.parseInt(nm[0]);
    int m = Integer.parseInt(nm[1]);

    graph = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int i = 0; i < m; i++) {
      String[] parts = reader.readLine().split(" ");
      int u = Integer.parseInt(parts[0]) - 1;
      int v = Integer.parseInt(parts[1]) - 1;
      int c = Integer.parseInt(parts[2]);

      graph[u].add(new Edge(v, c));
    }

    solution(n, 0);

    for (int i = 0; i < n; i++) {
      if (distance[i] > 1e9) {
        System.out.print(30000 + " ");
      } else {
        System.out.print(distance[i] + " ");
      }
    }
  }

  static void solution(int n, int start) {
    distance = new long[n];
    Arrays.fill(distance, (long) 1e18);
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
