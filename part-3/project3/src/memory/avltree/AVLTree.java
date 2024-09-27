package memory.avltree;

public class AVLTree {
	private int comparisons;
	private void resetComparisons() {
		this.comparisons = 0;
	}
	
	public class Node {
		int key;
		int height;
		Node left;
		Node right;

		Node(int key) {
			this.key = key;
		}
	}

	private Node root;

	public int find(int key) {
		resetComparisons();
		Node current = root;
		comparisons++;
		while (current != null) {
			comparisons+=2;
			if (current.key == key) {
				break;
			}
			comparisons++;
			current = current.key < key ? current.right : current.left;
		}
		return comparisons;
	}

	public int insert(int key) {
		resetComparisons();
		root = insert(root, key);
		return comparisons;
	}

	public int delete(int key) {
		resetComparisons();
		root = delete(root, key);
		return comparisons;
	}

	public Node getRoot() {
		return root;
	}

	public int height() {
		comparisons++;
		return root == null ? -1 : root.height;
	}

	private Node insert(Node node, int key) {
		comparisons++;
		if (node == null) {
			return new Node(key);
		} else if (node.key > key) {
			node.left = insert(node.left, key);
		} else if (node.key < key) {
			node.right = insert(node.right, key);
		} else {
			throw new RuntimeException("duplicate Key!");
		}
		return rebalance(node);
	}

	private Node delete(Node node, int key) {
		comparisons++;
		if (node == null) {
			return node;
		} else if (node.key > key) {
			comparisons++;
			node.left = delete(node.left, key);
		} else if (node.key < key) {
			comparisons+=2;
			node.right = delete(node.right, key);
		} else {
			comparisons+=3;
			comparisons+=2;
			if (node.left == null || node.right == null) {
				comparisons++;
				node = (node.left == null) ? node.right : node.left;
			} else {
				Node mostLeftChild = mostLeftChild(node.right);
				node.key = mostLeftChild.key;
				node.right = delete(node.right, node.key);
			}
		}
		comparisons++;
		if (node != null) {
			node = rebalance(node);
		}
		return node;
	}

	private Node mostLeftChild(Node node) {
		Node current = node;
		/* loop down to find the leftmost leaf */
		comparisons++;
		while (current.left != null) {
			comparisons++;
			current = current.left;
		}
		return current;
	}

	private Node rebalance(Node z) {
		updateHeight(z);
		int balance = getBalance(z);
		comparisons++;
		if (balance > 1) {
			comparisons++;
			if (height(z.right.right) > height(z.right.left)) {
				z = rotateLeft(z);
			} else {
				z.right = rotateRight(z.right);
				z = rotateLeft(z);
			}
		} else if (balance < -1) {
			comparisons++;
			if (height(z.left.left) > height(z.left.right)) {
				z = rotateRight(z);
			} else {
				z.left = rotateLeft(z.left);
				z = rotateRight(z);
			}
		}
		return z;
	}

	private Node rotateRight(Node y) {
		Node x = y.left;
		Node z = x.right;
		x.right = y;
		y.left = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}

	private Node rotateLeft(Node y) {
		Node x = y.right;
		Node z = x.left;
		x.left = y;
		y.right = z;
		updateHeight(y);
		updateHeight(x);
		return x;
	}

	private void updateHeight(Node n) {
		n.height = 1 + Math.max(height(n.left), height(n.right));
	}

	private int height(Node n) {
		return n == null ? -1 : n.height;
	}

	public int getBalance(Node n) {
		return (n == null) ? 0 : height(n.right) - height(n.left);
	}
}
