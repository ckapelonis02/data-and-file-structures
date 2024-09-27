package Heap;

public class HeapNode {
	public HeapNode parent, next, prev, left, right;
	public int key;
	
	public HeapNode(HeapNode parent, HeapNode prev, int key) {
		this.parent = parent;
		this.prev = prev;
		this.key = key;
		this.next = this.right = this.left = null;
	}
}
