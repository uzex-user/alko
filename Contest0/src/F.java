import java.util.Scanner;

public class F {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int l = scanner.nextInt();
        int[][] arrayA = new int[n][l];
        int[][] arrayB = new int[m][l];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l; j++) {
                arrayA[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                arrayB[i][j] = scanner.nextInt();
            }
        }
        int q = scanner.nextInt();
        for (int u = 0; u < q; u++) {
            int i = scanner.nextInt() - 1;
            int j = scanner.nextInt() - 1;
            int left = 0;
            int right = l - 1;
            int minimum = Integer.MAX_VALUE;
            int minimumK = -1;

            while (left <= right) {
                int mid = (left + right) / 2;
                int maximumOfValue = Math.max(arrayA[i][mid], arrayB[j][mid]);

                if (maximumOfValue < minimum) {
                    minimum = maximumOfValue;
                    minimumK = mid + 1;
                }

                if (arrayA[i][mid] < arrayB[j][mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            System.out.println(minimumK);
        }
        scanner.close();
    }
}

