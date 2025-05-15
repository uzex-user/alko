import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class E {
public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(reader.readLine());

    distance = new long[n];
    parent = new int[n];

    for (int i = 0; i < n; i++) {
      String[] parts = reader.readLine().split(" ");
      for (int j = 0; j < n; j++) {
        int c = Integer.parseInt(parts[j]);
        if (c != 100000) {
          edges.add(new Edge(i, j, c));
        }
      }
    }

    if (solution(n)) {
    } else {
      System.out.println("NO");
    }
  }

  static class Edge {
    int from;
    int to;
    int weight;

    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  static List<Edge> edges = new ArrayList<>();
  static long[] distance;
  static int[] parent;

  static boolean solution(int n) {
    Arrays.fill(distance, 0);
    Arrays.fill(parent, -1);

    int x = -1;
    for (int i = 0; i < n; i++) {
      x = -1;
      for (Edge edge : edges) {
        if (distance[edge.to] > distance[edge.from] + edge.weight) {
          distance[edge.to] = distance[edge.from] + edge.weight;
          parent[edge.to] = edge.from;
          x = edge.to;
        }
      }
    }

    if (x == -1) {
      return false;
    } else {
      for (int i = 0; i < n; i++) {
        x = parent[x];
      }

      List<Integer> cycle = new ArrayList<>();
      int cur = x;
      cycle.add(cur + 1);

      while (true) {
        cur = parent[cur];
        cycle.add(cur + 1);
        if (cur == x) {
          break;
        }
      }

      System.out.println("YES");
      System.out.println(cycle.size());
      for (int i = cycle.size() - 1; i >= 0; i--) {
        System.out.print(cycle.get(i) + " ");
      }
      System.out.println();
      return true;
    }
  }
}
