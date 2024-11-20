import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(reader.readLine());
        DualHeap heap = new DualHeap(200000);
        for (int i = 0; i < q; i++) {
            String[] input = reader.readLine().split(" ");
            switch (input[0]) {
                case "insert":
                    heap.insert(Long.parseLong(input[1]));
                    break;
                case "extract_min":
                    heap.extractMin();
                    break;
                case "extract_max":
                    heap.extractMax();
                    break;
                case "clear":
                    heap.clear();
                    break;
                case "size":
                    heap.size();
                    break;
                case "get_min":
                    heap.getMin();
                    break;
                case "get_max":
                    heap.getMax();
                    break;
            }
        }
    }
}

class DualHeap {
    private MinHeap minHeap;
    private MaxHeap maxHeap;
    private int size;

    public DualHeap(int capacity) {
        minHeap = new MinHeap(capacity);
        maxHeap = new MaxHeap(capacity);
        size = 0;
    }

    public void insert(long x) {
        minHeap.insert(x);
        maxHeap.insert(x);
        size++;
        System.out.println("ok");
    }

    public void getMin() {
        if (size == 0) {
            System.out.println("error");
        } else {
            System.out.println(minHeap.peek());
        }
    }

    public void extractMin() {
        if (size == 0) {
            System.out.println("error");
        } else {
            long min = minHeap.extract();
            maxHeap.remove(min);
            size--;
            System.out.println(min);
        }
    }

    public void getMax() {
        if (size == 0) {
            System.out.println("error");
        } else {
            System.out.println(maxHeap.peek());
        }
    }

    public void extractMax() {
        if (size == 0) {
            System.out.println("error");
        } else {
            long max = maxHeap.extract();
            minHeap.remove(max);
            size--;
            System.out.println(max);
        }
    }

    public void clear() {
        minHeap.clear();
        maxHeap.clear();
        size = 0;
        System.out.println("ok");
    }

    public void size() {
        System.out.println(size);
    }
}

class MinHeap {
    private long[] heap;
    private int size;

    public MinHeap(int capacity) {
        heap = new long[capacity + 1];
        size = 0;
    }

    public void insert(long x) {
        heap[++size] = x;
        siftUp(size);
    }

    public long peek() {
        return heap[1];
    }

    public long extract() {
        long min = heap[1];
        heap[1] = heap[size--];
        siftDown(1);
        return min;
    }

    public void remove(long x) {
        for (int i = (size + 1) / 2; i <= size; i++) {
            if (heap[i] == x) {
                heap[i] = heap[size--];
                if (i <= size) {
                    siftDown(i);
                    siftUp(i);
                }
                break;
            }
        }
    }

    public void clear() {
        size = 0;
    }

    private void siftUp(int index) {
        while (index > 1 && heap[index] < heap[index / 2]) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    private void siftDown(int index) {
        while (2 * index <= size) {
            int j = 2 * index;
            if (j < size && heap[j + 1] < heap[j]) {
                j++;
            }
            if (heap[index] <= heap[j]) {
                break;
            }
            swap(index, j);
            index = j;
        }
    }

    private void swap(int i, int j) {
        long temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

class MaxHeap {
    private long[] heap;
    private int size;

    public MaxHeap(int capacity) {
        heap = new long[capacity + 1];
        size = 0;
    }

    public void insert(long x) {
        heap[++size] = x;
        siftUp(size);
    }

    public long peek() {
        return heap[1];
    }

    public long extract() {
        long max = heap[1];
        heap[1] = heap[size--];
        siftDown(1);
        return max;
    }

    public void remove(long x) {
        for (int i = (size + 1) / 2; i <= size; i++) {
            if (heap[i] == x) {
                heap[i] = heap[size--];
                if (i <= size) {
                    siftDown(i);
                    siftUp(i);
                }
                break;
            }
        }
    }

    public void clear() {
        size = 0;
    }

    private void siftUp(int index) {
        while (index > 1 && heap[index] > heap[index / 2]) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    private void siftDown(int index) {
        while (2 * index <= size) {
            int j = 2 * index;
            if (j < size && heap[j + 1] > heap[j]) {
                j++;
            }
            if (heap[index] >= heap[j]) {
                break;
            }
            swap(index, j);
            index = j;
        }
    }

    private void swap(int i, int j) {
        long temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
