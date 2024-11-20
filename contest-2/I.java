import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class I {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        Deque<Integer> left = new ArrayDeque<>();
        Deque<Integer> right = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            String command = input[0];

            switch (command) {
                case "+":
                    right.addLast(Integer.parseInt(input[1]));
                    break;
                case "*":
                    right.addFirst(Integer.parseInt(input[1]));
                    break;
                case "-":
                    System.out.println(left.pollFirst());
                    break;
            }
            if (left.size() > right.size() + 1) {
                right.addFirst(left.pollLast());
            } else if (right.size() > left.size()) {
                left.addLast(right.pollFirst());
            }
        }
    }
}
