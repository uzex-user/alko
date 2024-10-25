import java.util.Arrays;
import java.util.Scanner;

public class B {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long k = scanner.nextLong() - 1;
        long[] array = new long[n];
        array[0] = scanner.nextLong();
        array[1] = scanner.nextLong();

        for (int i = 2; i < n; i++) {
            array[i] = element(array[i - 1], array[i - 2]);
        }

        System.out.println(QuickSelect(array, (int) k));
    }

    public static long element(long last, long lastLast) {
        return (long) ((last * 123 + lastLast * 45) % (Math.pow(10, 7) + 4321));
    }

    public static long[][] part(long[] array, int x) {
        long[] smaller = Arrays.stream(array).filter(num -> num < x).toArray();
        long[] equal = Arrays.stream(array).filter(num -> num == x).toArray();
        long[] greater = Arrays.stream(array).filter(num -> num > x).toArray();
        return new long[][] {smaller, equal, greater};
    }

    public static long QuickSelect(long[] array, int k) {
        if (array.length == 1) {
            return array[0];
        }
        int x = (int) (Math.random() * (array.length));
        long[][] currValues = part(array, (int) array[x]);
        long[] l = currValues[0];
        long[] m = currValues[1];
        long[] r = currValues[2];

        if (k < l.length) {
            return QuickSelect(l, k);
        }
        if (k < l.length + m.length) {
            return array[x];
        }
        return QuickSelect(r, (int) (k - l.length - m.length));
    }
}
