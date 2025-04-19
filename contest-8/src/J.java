import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class J {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] input = reader.readLine().split(" ");
    int target = Integer.parseInt(input[0]);
    int up = Integer.parseInt(input[1]);
    int down = Integer.parseInt(input[2]);
    int in = Integer.parseInt(input[3]);
    int costOut = Integer.parseInt(input[4]);
    int teleCnt = Integer.parseInt(input[5]);

    List<List<Integer>> teleports = new ArrayList<>();
    for (int t = 0; t < teleCnt; t++) {
      input = reader.readLine().split(" ");
      int cnt = Integer.parseInt(input[0]);
      List<Integer> floors = new ArrayList<>();
      for (int j = 1; j <= cnt; j++) {
        floors.add(Integer.parseInt(input[j]));
      }
      teleports.add(floors);
    }

    int maxFloor = 1_000_000;
    List<Integer>[] floorTeleports = new ArrayList[maxFloor + 1];
    for (int t = 0; t < teleCnt; t++) {
      for (int floor : teleports.get(t)) {
        if (floorTeleports[floor] == null) {
          floorTeleports[floor] = new ArrayList<>();
        }
        floorTeleports[floor].add(t);
      }
    }

    int[] dist = new int[maxFloor + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[1] = 0;

    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    heap.add(new int[] {0, 1});

    boolean[] usedTeleport = new boolean[teleCnt];

    while (!heap.isEmpty()) {
      int[] cur = heap.poll();
      int curCost = cur[0];
      int floor = cur[1];

      if (curCost > dist[floor]) continue;

      if (floor < maxFloor && dist[floor + 1] > curCost + up) {
        dist[floor + 1] = curCost + up;
        heap.add(new int[] {dist[floor + 1], floor + 1});
      }

      if (floor > 1 && dist[floor - 1] > curCost + down) {
        dist[floor - 1] = curCost + down;
        heap.add(new int[] {dist[floor - 1], floor - 1});
      }

      if (floorTeleports[floor] != null) {
        for (int t : floorTeleports[floor]) {
          if (!usedTeleport[t]) {
            usedTeleport[t] = true;
            for (int tf : teleports.get(t)) {
              int newCost = curCost + in + costOut;
              if (dist[tf] > newCost) {
                dist[tf] = newCost;
                heap.add(new int[] {newCost, tf});
              }
            }
          }
        }
      }
    }

    System.out.println(dist[target]);
  }
}
