import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(reader.readLine());

    int[][] a = new int[n][n];

    for (int i = 0; i < n; i++) {
      String[] line = reader.readLine().split(" ");
      for (int j = 0; j < n; j++) {
        a[i][j] = Integer.parseInt(line[j]);
      }
    }

    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (a[i][k] == 1 && a[k][j] == 1) {
            a[i][j] = 1;
          }
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(a[i][j] + " ");
      }
      System.out.println();
    }
  }
}
