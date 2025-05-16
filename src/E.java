import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class E {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int[] input = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    int count = input[0];
    int weight = input[1];
    long[] data = Arrays.stream(reader.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
    long[] prices =
        Arrays.stream(reader.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

    long[][] dp = new long[count + 1][weight + 1];

    for (int i = 1; i < count + 1; i++) {
      for (int j = 0; j < weight + 1; j++) {
        dp[i][j] = dp[i - 1][j];
        if (data[i - 1] <= j) {
          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - (int) data[i - 1]] + prices[i - 1]);
        }
      }
    }

    long res = 0;
    int bestWeight = 0;
    for (int i = 0; i < weight + 1; i++) {
      if (dp[count][i] > res) {
        res = dp[count][i];
        bestWeight = i;
      }
    }

    int w = bestWeight;
    List<Integer> result = new ArrayList<>();
    for (int i = count; i > 0 && w > 0; i--) {
      if (dp[i][w] != dp[i - 1][w]) {
        result.add(i);
        w -= (int) data[i - 1];
      }
    }

    Collections.reverse(result);

    for (int element : result) {
      System.out.println(element);
    }
  }
}
