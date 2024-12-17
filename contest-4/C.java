import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        long n = Integer.parseInt(reader.readLine());

        TreeC tree = new TreeC();

        for (long i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            String first = input[0];
            String second = input[1];

            tree.insert(first, second);
            tree.insert(second, first);
        }

        long q = Integer.parseInt(reader.readLine());

        for (long i = 0; i < q; i++) {
            System.out.println(tree.find(reader.readLine()));
        }
    }
}

class NodeB {
    String value;
    NodeB left;
    NodeB right;
    long height;
    String pair;

    public NodeB(String value, String pair) {
        this.value = value;
        this.height = 1;
        this.pair = pair;
    }

    public void updateHeight() {
        this.height = 1 + Math.max(getHeight(left), getHeight(right));
    }

    public long getHeight(NodeB node) {
        return (node == null) ? 0 : node.height;
    }
}

class TreeC {
    NodeB root = null;

    public void insert(String value, String pair) {
        root = insertRecursive(root, value, pair);
    }

    private NodeB insertRecursive(NodeB node, String value, String pair) {
        if (node == null) {
            return new NodeB(value, pair);
        }

        if (isSmaller(value, node.value)) {
            node.left = insertRecursive(node.left, value, pair);
        } else if (isSmaller(node.value, value)) {
            node.right = insertRecursive(node.right, value, pair);
        }

        node.updateHeight();

        return balance(node);
    }

    private NodeB rightRotate(NodeB node) {
        NodeB left = node.left;
        NodeB right = left.right;

        left.right = node;
        node.left = right;

        node.updateHeight();
        left.updateHeight();

        return left;
    }

    private NodeB leftRotate(NodeB node) {
        NodeB right = node.right;
        NodeB left = right.left;

        right.left = node;
        node.right = left;

        node.updateHeight();
        right.updateHeight();

        return right;
    }

    private NodeB balance(NodeB node) {
        long balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private long getBalanceFactor(NodeB node) {
        if (node == null) {
            return 0;
        }

        return node.getHeight(node.left) - node.getHeight(node.right);
    }

    public String find(String value) {
        return findRecursive(root, value);
    }

    private String findRecursive(NodeB node, String value) {
        if (node.value.equals(value)) {
            return node.pair;
        }

        if (isSmaller(node.value, value)) {
            return findRecursive(node.right, value);
        }

        return findRecursive(node.left, value);
    }

    private boolean isSmaller(String first, String second) {
        int i = 0;
        while (i != first.length() && i != second.length()) {
            if (first.charAt(i) < second.charAt(i)) {
                return true;
            } else if (first.charAt(i) > second.charAt(i)) {
                return false;
            }
            i++;
        }

        return first.length() < second.length();
    }
}
