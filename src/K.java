import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        int[] array = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] dp = new int[n + 1];
        int[] pos = new int[n + 1];
        int[] prev = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = Integer.MAX_VALUE;

        int length = 0;

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = length;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (dp[mid] >= array[i]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            dp[left] = array[i];
            pos[left] = i;
            prev[i] = pos[left - 1];
            if (left > length) {
                length = left;
            }
        }

        int[] result = new int[length];
        int p = pos[length];
        for (int i = length - 1; i >= 0; i--) {
            result[i] = p + 1;
            p = prev[p];
        }

        System.out.println(length);
        for (int i = 0; i < length; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
