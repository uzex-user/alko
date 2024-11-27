import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class C {

    public static final int EIGHT = 8;
    public static final int INT = 256;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());
        byte[][] matrix = new byte[size][8];

        fillMatrix(matrix, size);

        sortBytes(matrix);

        printMatrix(matrix);
    }

    private static void fillMatrix(byte[][] matrix, int size) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int index = 0;
        while (index < size) {
            byte[] byteRow = new BigInteger(reader.readLine()).toByteArray();
            System.arraycopy(
                    byteRow,
                    Math.max(byteRow.length - EIGHT, 0),
                    matrix[index],
                    EIGHT - Math.min(byteRow.length, EIGHT),
                    Math.min(byteRow.length, EIGHT));
            index++;
        }
    }

    private static void sortBytes(byte[][] matrix) {
        for (int bytePos = 7; bytePos >= 0; bytePos--) {
            countingSort(matrix, bytePos);
        }
    }

    private static void countingSort(byte[][] matrix, int pos) {
        int[] counter = new int[INT];

        for (byte[] row : matrix) {
            int value = row[pos];
            if (value >= 0) {
                counter[value]++;
            } else {
                counter[INT + value]++;
            }
        }

        for (int i = 1; i < INT; i++) {
            counter[i] += counter[i - 1];
        }

        byte[][] sortedMatrix = new byte[matrix.length][8];

        for (int i = matrix.length - 1; i >= 0; i--) {
            byte[] currentRow = matrix[i];
            int byteValue = currentRow[pos];
            if (byteValue >= 0) {
                sortedMatrix[--counter[byteValue]] = currentRow;
            } else {
                sortedMatrix[--counter[INT + byteValue]] = currentRow;
            }
        }

        System.arraycopy(sortedMatrix, 0, matrix, 0, matrix.length);
    }

    private static void printMatrix(byte[][] matrix) {
        for (byte[] row : matrix) {
            System.out.print(new BigInteger(1, row) + " ");
        }
        System.out.println();
    }
}
