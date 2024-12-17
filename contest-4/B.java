import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(reader.readLine());
        AvlTree tree = new AvlTree();
        long delta = 0;

        for (int i = 0; i < q; i++) {
            String[] input = reader.readLine().split(" ");

            if (input[0].equals("+")) {
                tree.insert((int) ((Integer.parseInt(input[1]) + delta) % 1_000_000_000));
                delta = 0;
            } else {
                int res = tree.find(Integer.parseInt(input[1]));
                System.out.println(res);
                delta = res;
            }
        }
    }
}

class NodeB {
    int value;
    NodeB left;
    NodeB right;
    int height;

    public NodeB(int value) {
        this.value = value;
        this.height = 1;
    }

    public void updateHeight() {
        this.height = 1 + Math.max(getHeight(left), getHeight(right));
    }

    public int getHeight(NodeB node) {
        return (node == null) ? 0 : node.height;
    }
}

class AvlTree {
    NodeB root = null;

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private NodeB insertRecursive(NodeB node, int value) {
        if (node == null) {
            return new NodeB(value);
        }

        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
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
        int balanceFactor = getBalanceFactor(node);

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

    private int getBalanceFactor(NodeB node) {
        if (node == null) {
            return 0;
        }

        return node.getHeight(node.left) - node.getHeight(node.right);
    }

    public int find(int value) {
        return findRecursive(root, value);
    }

    private int findRecursive(NodeB node, int value) {
        if (node == null) {
            return -1;
        }

        if (node.value == value) {
            return value;
        }

        if (node.value < value) {
            return findRecursive(node.right, value);
        }

        int result = findRecursive(node.left, value);
        return result == -1 ? node.value : result;
    }
}
