import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class D {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    String[] input = reader.readLine().split(" ");
    int n = Integer.parseInt(input[0]);
    int m = Integer.parseInt(input[1]);
    color = new int[n + 1];
    graph = new ArrayList[n + 1];
    reversedGraph = new ArrayList[n + 1];
    result = new int[n + 1];

    for (int i = 0; i <= n; i++) {
      graph[i] = new ArrayList<>();
      reversedGraph[i] = new ArrayList<>();
      color[i] = 0;
    }

    for (int i = 0; i < m; i++) {
      input = reader.readLine().split(" ");
      int u = Integer.parseInt(input[0]);
      int v = Integer.parseInt(input[1]);
      graph[u].add(v);
      reversedGraph[v].add(u);
    }

    for (int i = 1; i <= n; i++) {
      if (color[i] == 0) {
        dfs(i);
      }
    }

    Collections.reverse(toutOrder);

    color = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      color[i] = 0;
    }

    List<List<Integer>> sccList = new ArrayList<>();
    for (int node : toutOrder) {
      if (color[node] == 0) {
        List<Integer> scc = new ArrayList<>();
        dfsReversed(node, scc);
        sccList.add(scc);
      }
    }

    int cnt = 1;
    for (List<Integer> scc : sccList) {
      for (int v : scc) {
        result[v] = cnt;
      }
      cnt++;
    }

    System.out.println(sccList.size());
    for (int i = 1; i <= n; i++) {
      System.out.print(result[i] + " ");
    }
  }

  static List<Integer>[] graph;
  static List<Integer>[] reversedGraph;
  static int[] color;
  static List<Integer> toutOrder = new ArrayList<>();
  static int[] result;

  static void dfs(int startNode) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(startNode);

    while (!stack.isEmpty()) {
      int node = stack.pop();

      if (color[node] == 0) {
        color[node] = 1;
        stack.push(node);

        for (int neighbor : graph[node]) {
          if (color[neighbor] == 0) {
            stack.push(neighbor);
          }
        }
      } else if (color[node] == 1) {
        color[node] = 2;
        toutOrder.add(node);
      }
    }
  }

  static void dfsReversed(int startNode, List<Integer> scc) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(startNode);

    while (!stack.isEmpty()) {
      int node = stack.pop();

      if (color[node] == 0) {
        color[node] = 1;
        stack.push(node);

        for (int neighbor : reversedGraph[node]) {
          if (color[neighbor] == 0) {
            stack.push(neighbor);
          }
        }
      } else if (color[node] == 1) {
        color[node] = 2;
        scc.add(node);
      }
    }
  }
}
