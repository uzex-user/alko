import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class B {
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
  static long[] infectionTime;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] nmk = reader.readLine().split(" ");
    int n = Integer.parseInt(nmk[0]);
    int m = Integer.parseInt(nmk[1]);
    int k = Integer.parseInt(nmk[2]);

    String[] infectedStr = reader.readLine().split(" ");
    List<Integer> infected = new ArrayList<>();
    for (String num : infectedStr) {
      infected.add(Integer.parseInt(num) - 1);
    }

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
      graph[v].add(new Edge(u, c));
    }

    String[] st = reader.readLine().split(" ");
    int s = Integer.parseInt(st[0]) - 1;
    int t = Integer.parseInt(st[1]) - 1;

    setInfection(n, infected);

    solution(n, s);

    if (distance[t] < infectionTime[t]) {
      System.out.println(distance[t]);
    } else {
      System.out.println(-1);
    }
  }

  static void setInfection(int n, List<Integer> infected) {
    infectionTime = new long[n];
    Arrays.fill(infectionTime, Long.MAX_VALUE);

    PriorityQueue<long[]> heap = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

    for (int root : infected) {
      infectionTime[root] = 0;
      heap.offer(new long[] {0, root});
    }

    while (!heap.isEmpty()) {
      long[] current = heap.poll();
      long time = current[0];
      int u = (int) current[1];

      if (time > infectionTime[u]) {
        continue;
      }

      for (Edge edge : graph[u]) {
        int v = edge.to;
        long newTime = time + edge.weight;
        if (newTime < infectionTime[v]) {
          infectionTime[v] = newTime;
          heap.offer(new long[] {newTime, v});
        }
      }
    }
  }

  static void solution(int n, int start) {
    distance = new long[n];
    Arrays.fill(distance, Long.MAX_VALUE);
    distance[start] = 0;

    PriorityQueue<long[]> heap = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
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

        if (newDist < distance[v] && newDist < infectionTime[v]) {
          distance[v] = newDist;
          heap.offer(new long[] {newDist, v});
        }
      }
    }
  }
}
