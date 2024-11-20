import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        long q = Long.parseLong(reader.readLine());
        BinaryHeap heap = new BinaryHeap(1000000);

        for (long i = 0; i < q; i++) {
            String[] input = reader.readLine().split(" ");

            switch (input[0]) {
                case "insert":
                    heap.insert(Long.parseLong(input[1]), i + 1);
                    break;
                case "getMin":
                    System.out.println(heap.getMin());
                    break;
                case "extractMin":
                    heap.extractMin();
                    break;
                case "decreaseKey":
                    heap.decreaseKey(Long.parseLong(input[1]), Long.parseLong(input[2]));
                    break;
            }
        }
    }
}

class BinaryHeap {
    private long[][] heap;
    private long size;
    private long[] indices;

    public BinaryHeap(long capacity) {
        heap = new long[(int) capacity + 1][2];
        size = 0;
        indices = new long[(int) capacity + 1];
    }

    public void insert(long x, long i) {
        heap[(int) ++size][0] = x;
        heap[(int) size][1] = i;
        indices[(int) i] = size;
        siftUp(size);
    }

    public long getMin() {
        return heap[1][0];
    }

    public void extractMin() {
        heap[1][0] = heap[(int) size][0];
        heap[1][1] = heap[(int) size][1];
        indices[(int) heap[(int) size][1]] = 1;
        size--;
        siftDown(1);
    }

    public void decreaseKey(long i, long delta) {
        long ind = indices[(int) i];
        heap[(int) ind][0] -= delta;
        siftUp(ind);
        long j = ind;
        while (j > 1) {
            indices[(int) heap[(int) j][1]] = j;
            j = j / 2;
        }
    }

    private void siftUp(long i) {
        while (i > 1 && heap[(int) i][0] < heap[(int) i / 2][0]) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    private void siftDown(long i) {
        while (2 * i <= size && i <= size) {
            long j = 2 * i;
            if (j < size && heap[(int) j + 1][0] < heap[(int) j][0]) {
                j++;
            }
            if (heap[(int) i][0] > heap[(int) j][0]) {
                swap(i, j);
                i = j;
            } else {
                break;
            }
        }
    }

    private void swap(long i, long j) {
        long[] temp = heap[(int) i];
        heap[(int) i] = heap[(int) j];
        heap[(int) j] = temp;
        indices[(int) heap[(int) i][1]] = i;
        indices[(int) heap[(int) j][1]] = j;
    }
}
