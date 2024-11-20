import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class QueueWithMin {
    private long[] queue;
    private int front;
    private int rear;
    private int size;
    private long min;

    public QueueWithMin(int capacity) {
        queue = new long[capacity];
        front = 0;
        rear = 0;
        size = 0;
        min = Long.MAX_VALUE;
    }

    public void enqueue(long x) {
        queue[rear] = x;
        rear = rear + 1;
        size++;

        if (x < min) {
            min = x;
        }

        System.out.println("ok");
    }

    public void dequeue() {
        if (size == 0) {
            System.out.println("error");
            return;
        }

        long x = queue[front];
        front = front + 1;
        size--;

        if (x == min) {
            min = Long.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                if (queue[front + i] < min) {
                    min = queue[front + i];
                }
            }
        }

        System.out.println(x);
    }

    public void front() {
        if (size == 0) {
            System.out.println("error");
            return;
        }

        System.out.println(queue[front]);
    }

    public void size() {
        System.out.println(size);
    }

    public void clear() {
        front = 0;
        rear = 0;
        size = 0;
        min = Long.MAX_VALUE;

        System.out.println("ok");
    }

    public void min() {
        if (size == 0) {
            System.out.println("error");
            return;
        }

        System.out.println(min);
    }
}

public class J {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        QueueWithMin queue = new QueueWithMin(1000000);

        for (int i = 0; i < n; i++) {
            String[] request = reader.readLine().split(" ");
            String command = request[0];

            if (command.equals("enqueue")) {
                queue.enqueue(Integer.parseInt(request[1]));
            } else if (command.equals("dequeue")) {
                queue.dequeue();
            } else if (command.equals("front")) {
                queue.front();
            } else if (command.equals("size")) {
                queue.size();
            } else if (command.equals("clear")) {
                queue.clear();
            } else if (command.equals("min")) {
                queue.min();
            }
        }
    }
}
