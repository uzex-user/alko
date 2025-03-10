import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int level = scanner.nextInt();
        int balls = scanner.nextInt();

        if (level == 1) {
            System.out.println(0);
            return;
        }

        if (balls == 0) {
            System.out.println(-1);
            return;
        }

        balls = Math.min(balls, 16);
        long[][] dp = new long[balls + 1][level + 1];

        for (int i = 1; i < balls + 1; i++) {
            dp[i][1] = 0;
            dp[i][0] = 0;
        }

        for (int i = 1; i < level + 1; i++) {
            dp[1][i] = i - 1;
        }

        for (int i = 2; i < balls + 1; i++) {
            for (int j = 2; j < level + 1; j++) {
                int left = 1;
                int right = j;
                dp[i][j] = Integer.MAX_VALUE;

                while (left <= right) {
                    int mid = left + (right - left) / 2;

                    long tmp = Math.max(dp[i - 1][mid], dp[i][j - mid]) + 1;

                    if (tmp < dp[i][j]) {
                        dp[i][j] = tmp;
                    }

                    if (dp[i - 1][mid] < dp[i][j - mid]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }

        System.out.println(dp[balls][level]);
    }
}
