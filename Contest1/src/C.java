import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class C {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        int size = readInt();
        byte[][] matrix = new byte[size][8];

        fillMatrix(matrix, size);

        sortBytes(matrix);

        printMatrix(matrix);
    }

    private static void fillMatrix(byte[][] matrix, int size) throws Exception {
        int index = 0;
        while (index < size) {
            byte[] byteRow = new BigInteger(readString()).toByteArray();
            System.arraycopy(
                    byteRow,
                    Math.max(byteRow.length - 8, 0),
                    matrix[index],
                    8 - Math.min(byteRow.length, 8),
                    Math.min(byteRow.length, 8));
            index++;
        }
    }

    private static void sortBytes(byte[][] matrix) {
        for (int bytePos = 7; bytePos >= 0; bytePos--) {
            countingSort(matrix, bytePos);
        }
    }

    private static void countingSort(byte[][] matrix, int pos) {
        int[] counter = new int[256];

        for (byte[] row : matrix) {
            int value = row[pos];
            if (value >= 0) {
                counter[value]++;
            } else {
                counter[256 + value]++;
            }
        }

        for (int i = 1; i < 256; i++) {
            counter[i] += counter[i - 1];
        }

        byte[][] sortedMatrix = new byte[matrix.length][8];

        for (int i = matrix.length - 1; i >= 0; i--) {
            byte[] currentRow = matrix[i];
            int byteValue = currentRow[pos];
            if (byteValue >= 0) {
                sortedMatrix[--counter[byteValue]] = currentRow;
            } else {
                sortedMatrix[--counter[256 + byteValue]] = currentRow;
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

    private static int readInt() throws Exception {
        return Integer.parseInt(readString());
    }

    private static String readString() throws Exception {
        return reader.readLine().trim();
    }
}
