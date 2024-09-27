package Heap;

/**
 * 
 * Heap (as a complete binary tree) based on an one-dimensional array.
 *
 * Code modified from "Data and File Structures" lectures of ECE TUC  
 * 
 */

public class ArrayHeap { 
	public int[] heap;	//array where the keys are stored	
	public int size;	//array.length
	public int n;    	//current position
	private int tries;	//used as global variable to count comparisons 
	
	//	constructor of heap when array is given
	public ArrayHeap(int[] heap, int num) {
		this.heap = new int[heap.length];
		for (int i = 0; i < heap.length; i++) {
			this.heap[i] = heap[i];
		}
		this.n = num;
		this.size = heap.length;
		buildHeap();
	}
	
	//	constructor of heap so that elements are inserted one by one
	public ArrayHeap(int size) {
		this.n = 0;
		this.size = size;
		this.heap = new int[size];
	}
	
	//	inserts a key
	public int insert(int key) {
		int curr = n++;
		tries = 0;
		heap[curr] = key;
		while ((curr != 0) && (heap[curr] > heap[parent(curr)])) {
			tries+=2;
			swap(curr, parent(curr));
			curr = parent(curr);
		}
		return tries;
	}
	
	//	removes a key indexed by @param pos
	public int remove(int pos) {
		tries = 0;
		tries++;
		swap(pos, --n);
		if (n != 0) {
			shiftDown(pos);
		}
		return tries;
	}
	
	//	removes max key (root of binary tree)
	public int removeMax() {
		tries = 0;
		return remove(0);
	}
	
	//	returns index of left child
	private int leftChild(int pos) {
		if (pos <= (n-2)/2) {
			return (2*pos + 1); 
		}
		else {
			return -1;
		}
	}
	
	//	returns index of right child
	private int rightChild(int pos) {
		if (pos <= (n-3)/2) {
			return (2*pos + 2); 
		}
		else {
			return -1;
		}
	}
	
	//	returns index of parent
	private int parent(int pos) {
		tries++;
		if (pos > 0 && pos < n) {
			return (pos - 1)/2;
		}
		else {
			return -1;
		}
	}	
	
	//	returns true if a node is leaf, otherwise false
	private boolean isLeaf(int pos) {
		tries++;
		return (leftChild(pos) == -1 && rightChild(pos) == -1);
	}
	
	//	builds heap by shifting down every element that is not 
	//	leaf going from d or d-1 level upwards
	private void buildHeap() {
		for (int i = parent(n-1); i >= 0; i--) {
			shiftDown(i);
		}
	}

	//	shifts element down to its proper position
	private void shiftDown(int pos) {
		tries++;
		if (pos < 0 || pos >= n) {
			System.out.println("Illegal heap position.");
			return;
		}
		while (!isLeaf(pos)) {
			tries++;
		    int j = leftChild(pos);
		    if ((j<(n-1)) && (heap[j] < heap[j+1]))
		      j++; 	
		    if (heap[pos] > heap[j]) return; // Done
		    swap(pos, j);
		    pos = j;  	// Move down
		}
	}
	
	//	swaps index i with index j in the array (the data)
	private void swap(int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
}
