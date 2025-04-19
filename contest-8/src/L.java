import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class L {
  static int[] parent;
  static int[] size;

  static int find(int v) {
    if (parent[v] == v) {
      return v;
    } else {
      parent[v] = find(parent[v]);
      return parent[v];
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = reader.readLine();
    String[] inputFst = input.split(" ");
    int n = Integer.parseInt(inputFst[0]);
    int m = Integer.parseInt(inputFst[1]);
    int q = Integer.parseInt(inputFst[2]);

    HashMap<Long, Boolean> edgeExist = new HashMap<>();
    ArrayList<String> queries = new ArrayList<>();

    for (int i = 0; i < m; i++) {
      input = reader.readLine();
      String[] parts = input.split(" ");
      int u = Integer.parseInt(parts[0]);
      int v = Integer.parseInt(parts[1]);

      if (u > v) {
        int t = u;
        u = v;
        v = t;
      }

      long key = (long) u * 100000 + v;
      edgeExist.put(key, true);
    }

    for (int i = 0; i < q; i++) {
      String query = reader.readLine();
      queries.add(query);

      String[] parts = query.split(" ");
      if (parts[0].equals("cut")) {
        int u = Integer.parseInt(parts[1]);
        int v = Integer.parseInt(parts[2]);

        if (u > v) {
          int t = u;
          u = v;
          v = t;
        }

        long key = (long) u * 100000 + v;
        edgeExist.remove(key);
      }
    }

    parent = new int[n + 1];
    size = new int[n + 1];

    for (int i = 1; i <= n; i++) {
      parent[i] = i;
      size[i] = 1;
    }

    for (Map.Entry<Long, Boolean> entry : edgeExist.entrySet()) {
      long key = entry.getKey();
      int u = (int) (key / 100000);
      int v = (int) (key % 100000);

      int a = find(u);
      int b = find(v);

      if (a != b) {
        if (size[a] < size[b]) {
          int t = a;
          a = b;
          b = t;
        }
        parent[b] = a;
        size[a] += size[b];
      }
    }

    ArrayList<String> answer = new ArrayList<>();
    for (int i = q - 1; i >= 0; i--) {
      String query = queries.get(i);
      String[] parts = query.split(" ");

      if (parts[0].equals("ask")) {
        int u = Integer.parseInt(parts[1]);
        int v = Integer.parseInt(parts[2]);

        if (find(u) == find(v)) {
          answer.add("YES");
        } else {
          answer.add("NO");
        }
      } else {
        int u = Integer.parseInt(parts[1]);
        int v = Integer.parseInt(parts[2]);

        if (u > v) {
          int t = u;
          u = v;
          v = t;
        }

        int a = find(u);
        int b = find(v);

        if (a != b) {
          if (size[a] < size[b]) {
            int t = a;
            a = b;
            b = t;
          }
          parent[b] = a;
          size[a] += size[b];
        }
      }
    }

    Collections.reverse(answer);
    for (String res : answer) {
      System.out.println(res);
    }
  }
}
