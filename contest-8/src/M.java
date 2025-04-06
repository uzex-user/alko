import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class M {
  static int[] parent;
  static int[] size;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] line = reader.readLine().split(" ");

    int n = Integer.parseInt(line[0]);
    int m = Integer.parseInt(line[1]);

    ArrayList<Edge> edges = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      String[] edgeLine = reader.readLine().split(" ");
      int u = Integer.parseInt(edgeLine[0]);
      int v = Integer.parseInt(edgeLine[1]);
      int w = Integer.parseInt(edgeLine[2]);
      edges.add(new Edge(u, v, w));
    }

    parent = new int[n + 1];
    size = new int[n + 1];

    for (int i = 1; i <= n; i++) {
      parent[i] = i;
      size[i] = 1;
    }

    Collections.sort(edges, (e1, e2) -> Integer.compare(e1.weight, e2.weight));

    int min = 0;
    for (Edge e : edges) {
      int u = e.u;
      int v = e.v;

      int rootU = u;
      while (rootU != parent[rootU]) {
        rootU = parent[rootU];
      }

      int rootV = v;
      while (rootV != parent[rootV]) {
        rootV = parent[rootV];
      }

      if (rootU != rootV) {
        if (size[rootU] < size[rootV]) {
          int tmp = rootU;
          rootU = rootV;
          rootV = tmp;
        }
        parent[rootV] = rootU;
        size[rootU] += size[rootV];
        min += e.weight;
      }
    }

    System.out.println(min);
  }
}

class Edge {
  int u;
  int v;
  int weight;

  Edge(int u, int v, int w) {
    this.u = u;
    this.v = v;
    this.weight = w;
  }
}
