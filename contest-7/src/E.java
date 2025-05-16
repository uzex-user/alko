import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class E {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] input = reader.readLine().split(" ");
    int n = Integer.parseInt(input[0]);
    int m = Integer.parseInt(input[1]);

    graph = new ArrayList[n + 1];
    tin = new int[n + 1];
    low = new int[n + 1];
    visited = new boolean[n + 1];
    edgeCount = new HashMap<>();

    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<>();
    }

    for (int i = 1; i <= m; i++) {
      String[] uv = reader.readLine().split(" ");
      int u = Integer.parseInt(uv[0]);
      int v = Integer.parseInt(uv[1]);

      if (u == v) {
        continue;
      }

      String key = Math.min(u, v) + "," + Math.max(u, v);
      edgeCount.put(key, edgeCount.getOrDefault(key, 0) + 1);

      graph[u].add(new Edge(v, i));
      graph[v].add(new Edge(u, i));
    }

    for (int i = 1; i <= n; i++) {
      if (!visited[i]) {
        iterativeDFS(i);
      }
    }

    Collections.sort(bridges);
    System.out.println(bridges.size());
    for (int id : bridges) {
      System.out.print(id + " ");
    }
  }

  static List<Edge>[] graph;
  static int[] tin, low;
  static boolean[] visited;
  static int timer = 0;
  static List<Integer> bridges = new ArrayList<>();
  static Map<String, Integer> edgeCount;

  static void iterativeDFS(int start) {
    Deque<StackFrame> stack = new ArrayDeque<>();
    stack.push(new StackFrame(start, -1, 0));
    tin[start] = low[start] = ++timer;
    visited[start] = true;

    while (!stack.isEmpty()) {
      StackFrame frame = stack.peek();
      int v = frame.v;
      int parentEdgeId = frame.parentEdgeId;

      if (frame.idx == graph[v].size()) {
        stack.pop();
        if (!stack.isEmpty()) {
          StackFrame parent = stack.peek();
          low[parent.v] = Math.min(low[parent.v], low[v]);

          if (low[v] > tin[parent.v]) {
            String key = Math.min(parent.v, v) + "," + Math.max(parent.v, v);
            if (edgeCount.getOrDefault(key, 0) == 1) {
              bridges.add(frame.parentEdgeId);
            }
          }
        }
        continue;
      }

      Edge edge = graph[v].get(frame.idx);
      frame.idx++;

      int to = edge.to;
      if (edge.id == parentEdgeId) {
        continue;
      }

      if (!visited[to]) {
        visited[to] = true;
        tin[to] = low[to] = ++timer;
        stack.push(new StackFrame(to, edge.id, 0));
      } else {
        low[v] = Math.min(low[v], tin[to]);
      }
    }
  }
}

class Edge {
  int to;
  int id;

  Edge(int to, int id) {
    this.to = to;
    this.id = id;
  }
}

class StackFrame {
  int v;
  int parentEdgeId;
  int idx;

  StackFrame(int v, int parentEdgeId, int idx) {
    this.v = v;
    this.parentEdgeId = parentEdgeId;
    this.idx = idx;
  }
}
