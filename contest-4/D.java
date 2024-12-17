import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class D {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SplayTree tree = new SplayTree();
        String input;

        while ((input = reader.readLine()) != null) {
            String[] query = input.split(" ");

            switch (query[0]) {
                case "insert" -> tree.insert(Integer.parseInt(query[1]));
                case "delete" -> tree.delete(Integer.parseInt(query[1]));
                case "exists" -> System.out.println(tree.exists(Integer.parseInt(query[1])));
                case "kth" -> System.out.println(tree.kth(Integer.parseInt(query[1])));
                case "next" -> System.out.println(tree.next(Integer.parseInt(query[1])));
                case "prev" -> System.out.println(tree.prev(Integer.parseInt(query[1])));
                default -> {
                    return;
                }
            }
        }
    }
}

class SplayNode {
    int value;
    int subtreeSize;
    SplayNode parent;
    SplayNode left;
    SplayNode right;

    SplayNode(int value) {
        this.value = value;
        this.subtreeSize = 1;
    }

    void updateSize() {
        this.subtreeSize =
            1 + (left != null ? left.subtreeSize : 0) + (right != null ? right.subtreeSize : 0);
    }
}

class SplayTree {
    SplayNode root;

    void rotateLeft(SplayNode node) {
        if (node == null || node.right == null) return;

        SplayNode right = node.right;
        node.right = right.left;

        if (right.left != null) right.left.parent = node;
        right.parent = node.parent;

        if (node.parent == null) root = right;
        else if (node == node.parent.left) node.parent.left = right;
        else node.parent.right = right;

        right.left = node;
        node.parent = right;

        node.updateSize();
        right.updateSize();
    }

    void rotateRight(SplayNode node) {
        if (node == null || node.left == null) return;

        SplayNode left = node.left;
        node.left = left.right;

        if (left.right != null) left.right.parent = node;
        left.parent = node.parent;

        if (node.parent == null) root = left;
        else if (node == node.parent.right) node.parent.right = left;
        else node.parent.left = left;

        left.right = node;
        node.parent = left;

        node.updateSize();
        left.updateSize();
    }

    void splay(SplayNode node) {
        while (node != null && node.parent != null) {
            if (node.parent.parent == null) {
                if (node.parent.left == node) rotateRight(node.parent);
                else rotateLeft(node.parent);
            } else if (node.parent.left == node && node.parent.parent.left == node.parent) {
                rotateRight(node.parent.parent);
                rotateRight(node.parent);
            } else if (node.parent.right == node && node.parent.parent.right == node.parent) {
                rotateLeft(node.parent.parent);
                rotateLeft(node.parent);
            } else if (node.parent.left == node && node.parent.parent.right == node.parent) {
                rotateRight(node.parent);
                rotateLeft(node.parent);
            } else {
                rotateLeft(node.parent);
                rotateRight(node.parent);
            }
        }
        root = node;
        if (root != null) {
            root.updateSize();
        }
    }

    public SplayNode find(int value, SplayNode node) {
        while (node != null) {
            if (value == node.value) return node;
            node = (value < node.value) ? node.left : node.right;
        }
        return null;
    }

    public boolean exists(int value) {
        return find(value, root) != null;
    }

    public void insert(int value) {
        if (exists(value)) return;

        SplayNode curr = root, parent = null;

        while (curr != null) {
            parent = curr;
            curr = (value < curr.value) ? curr.left : curr.right;
        }

        SplayNode newNode = new SplayNode(value);
        newNode.parent = parent;

        if (parent == null) root = newNode;
        else if (value < parent.value) parent.left = newNode;
        else parent.right = newNode;

        splay(newNode);
    }

    public void delete(int value) {
        SplayNode node = find(value, root);
        if (node == null) return;

        splay(node);

        if (node.left != null) {
            SplayNode maxNode = max(node.left);
            splay(maxNode);
            maxNode.right = node.right;
            if (node.right != null) node.right.parent = maxNode;
            root = maxNode;
        } else {
            root = node.right;
            if (root != null) root.parent = null;
        }

        if (root != null) {
            root.updateSize();
        }
    }

    public SplayNode max(SplayNode node) {
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node;
    }

    public String kth(int k) {
        if (root == null || k < 0 || k >= root.subtreeSize) return "none";

        SplayNode node = root;
        while (node != null) {
            int leftSize = (node.left != null ? node.left.subtreeSize : 0);
            if (k < leftSize) {
                node = node.left;
            } else if (k == leftSize) {
                splay(node);
                return String.valueOf(node.value);
            } else {
                k -= leftSize + 1;
                node = node.right;
            }
        }
        return "none";
    }

    public String next(int value) {
        SplayNode curr = root, successor = null;

        while (curr != null) {
            if (value < curr.value) {
                successor = curr;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        return (successor != null) ? String.valueOf(successor.value) : "none";
    }

    public String prev(int value) {
        SplayNode curr = root, predecessor = null;

        while (curr != null) {
            if (value > curr.value) {
                predecessor = curr;
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        return (predecessor != null) ? String.valueOf(predecessor.value) : "none";
    }
}
