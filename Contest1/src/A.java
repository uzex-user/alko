import java.util.Arrays;
import java.util.Scanner;

public class A {
    private static long k = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        long[][] arr = new long[number][2];

        for (int i = 0; i < number; i++) {
            arr[i][0] = scanner.nextLong();
            arr[i][1] = scanner.nextLong();
        }

        arr = mergeSort(arr);

        long start = arr[0][0];
        long end = arr[0][1];
        long k = 1;
        long[][] arrayOut = new long[number][2];

        for (int i = 1; i < number; i++) {
            if (end >= arr[i][0]) {
                end = Math.max(end, arr[i][1]);
            } else {
                arrayOut[(int) (k - 1)][0] = start;
                arrayOut[(int) (k - 1)][1] = end;
                k += 1;
                start = arr[i][0];
                end = arr[i][1];
            }
        }
        arrayOut[(int) (k - 1)][0] = start;
        arrayOut[(int) (k - 1)][1] = end;

        System.out.println(k);
        for (int i = 0; i < k; i++) {
            System.out.println(arrayOut[i][0] + " " + arrayOut[i][1]);
        }
    }

    public static long[][] merge(long[][] first, long[][] second) {
        int i = 0;
        int j = 0;
        int n = first.length;
        int m = second.length;
        long[][] C = new long[n + m][2];

        while (i < n || j < m) {
            if ((i != n) && (j == m || (first[i][0] <= second[j][0]))) {
                C[i + j] = first[i];
                i++;
            } else {
                C[i + j] = second[j];
                j++;
            }
        }

        return C;
    }

    public static long[][] mergeSort(long[][] array) {
        if (array.length <= 1) {
            return array;
        }

        int mid = array.length / 2;
        long[][] left = Arrays.copyOfRange(array, 0, mid);
        long[][] right = Arrays.copyOfRange(array, mid, array.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }
}
