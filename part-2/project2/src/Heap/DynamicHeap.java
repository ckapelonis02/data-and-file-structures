package Heap;

/**
* 
* Heap (as a complete binary tree) based on a tree,
* the elements of which also suggest a double-linked list.
*
* @author Charilaos Kapelonis
*
*/


public class DynamicHeap {
	public HeapNode root;	//always the root of the tree, the max element
	public HeapNode last;	//last inserted element, used for removals etc.
	public HeapNode parent;	//used for insertion, parent of the child that's going to be inserted
	private int tries;		//used as global variable to count comparisons 
	
	//	constructor
	public DynamicHeap() {
		this.root = null;
		this.last = null;
	}
	
	//	constructor of heap when array is given
	public DynamicHeap(int[] heap) {
		this.root = null;
		this.last = null;
		for (int i = 0; i < heap.length; i++) {
			insertKey(heap[i]);
		}
		heapifyRandom(parent);
	}
	
	//	inserts a key
	public int insert(int key) {
		tries = 0;
		insertKey(key);
		shiftUp(last);
		return tries;
	}
	
	//	removes max element
	public int removeMax() {
		tries = 0;
		tries++;
		if (root != null) {
			tries++;
			if (root.next == null) {
				this.root = null;
				this.last = null;
				System.err.println("Last element removed.");
				return tries;
			}
			swap(root, last);
			last.key = -1;
			heapify(root);
			last = last.prev;
			last.next = null;
			parent = last.parent;
		}
		else {
			System.err.println("Heap is empty.");
		}
		return tries;
	}
	
	//	heapify the tree  
	private void heapifyRandom(HeapNode parent) {
		while (true) {
			heapify(parent);
			if (parent == root) {
				break;
			}
			parent = parent.prev;
		}
	}
	
	//	private helping method to insert key
	private void insertKey(int key) {
		tries++;
		if (root == null) {
			root = new HeapNode(null, null, key);
			last = root;
			parent = root;
			return;
		}
		
		tries++;
		if (isFullParent(parent)) {
			parent = parent.next;
		}
		
		last.next = new HeapNode(parent, last, key);
		last = last.next;
		tries++;
		if (parent.left == null) {
			parent.left = last;
		}
		else {
			parent.right = last;
		}
	}
	
	//	shifts up element
	private void shiftUp(HeapNode node) {
		tries++;
		while (node != root && node.key > node.parent.key) {
			tries++;
			swap(node.parent, node);
			node = node.parent;
		}
	}
	
	//	swaps nodes
	private void swap(HeapNode p, HeapNode c) {
		int temp = p.key;
		p.key = c.key;
		c.key = temp;
	}
	
	//	returns true if node is leaf, otherwise false
	private boolean isLeaf(HeapNode node) {
		tries+=2;
		if (node != null) {
			return (node.left == null && node.right == null);
		}
		else {
			System.out.println("Node is null.");
		}
		return false;
	}
	
	//	returns true if node has exactly 2 children, otherwise false
	private boolean isFullParent(HeapNode node) {
		tries+=2;
		if (node != null) {
			return (node.left != null && node.right != null);
		}
		else {
			System.out.println("Node is null.");
		}
		return false;
	}
	
	//	heapify whole tree
	private void heapify(HeapNode r) {
		while (!isLeaf(r)) {
			tries++;
			HeapNode maxChild = r.left;
			tries+=2;
			if (r.right != null && maxChild.key < r.right.key) {
				maxChild = r.right;
			}
			tries++;
			if (r.key > maxChild.key) {
				return;
			}
		    swap(r, maxChild);
		    r = maxChild;
		  }
	}
}