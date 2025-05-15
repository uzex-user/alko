import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B {
    static int n;
    static int m;
    static int a;
    static int b;
    static char[][] board;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static List<Integer>[] graph;
    static int[] mt;
    static boolean[] used;
    static int[][] cellId;
    static int cellCount;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);
        a = Integer.parseInt(input[2]);
        b = Integer.parseInt(input[3]);

        board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = reader.readLine().toCharArray();
        }

        if (2 * b <= a) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (board[i][j] == '*') {
                        count++;
                    }
                }
            }
            System.out.println(count * b);
            return;
        }

        cellId = new int[n][m];
        cellCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '*') {
                    cellId[i][j] = cellCount++;
                }
            }
        }

        graph = new ArrayList[cellCount];
        for (int i = 0; i < cellCount; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '*' && (i + j) % 2 == 0) {
                    int u = cellId[i][j];
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dx[d];
                        int nj = j + dy[d];
                        if (ni >= 0 && nj >= 0 && ni < n && nj < m && board[ni][nj] == '*') {
                            int v = cellId[ni][nj];
                            graph[u].add(v);
                        }
                    }
                }
            }
        }

        mt = new int[cellCount];
        Arrays.fill(mt, -1);
        int matchCount = 0;
        for (int v = 0; v < cellCount; v++) {
            if ((getCellCoord(v)[0] + getCellCoord(v)[1]) % 2 == 0) {
                used = new boolean[cellCount];
                if (tryKuhn(v)) {
                    matchCount++;
                }
            }
        }

        int dominoCost = matchCount * a;
        int remaining = cellCount - matchCount * 2;
        int squareCost = remaining * b;
        System.out.println(dominoCost + squareCost);
    }

    static boolean tryKuhn(int v) {
        if (used[v]) {
            return false;
        }

        used[v] = true;
        for (int to : graph[v]) {
            if (mt[to] == -1 || tryKuhn(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }

    static int[] getCellCoord(int id) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '*' && cellId[i][j] == id) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }
}
