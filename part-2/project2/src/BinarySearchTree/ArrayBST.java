package BinarySearchTree;

import myPackage.SearchResult;

/**
 * 
 * Binary Search Tree (BST) based on a single 3xN array, in which:
 * 	1)	1st column represents data
 *	2)	2nd column contains address to left child's index
 *	3)	3rd column contains address to right child's index
 *
 * Note that, when a row is not used (available), the third position is
 * used as reference to the next available row, thus creating a stack
 * from where we pop and push nodes.
 *
 * In this data structure, as we are dealing with (positive) integers,
 * primitive data type, as a convention, null corresponds to -1.
 *
 * @author Charilaos Kapelonis
 *
 */

public class ArrayBST {
	private final int defaultSize = 1000000;
	private int size;		//array size, thus data structure size
	private int avail;		//current available row
	private int tree[][];	//3xN array
	private int root;		//root of BST
	private int numOfNodes;	//number of nodes
	private int tries;		//used as global variable to count comparisons 
	
	
	//	constructor with default size
	public ArrayBST() {
		this.size = defaultSize;
		initialize();
	}
	
	//	constructor with custom size
	public ArrayBST(int size) {
		this.size = size;
		initialize();
	}
	
	//	initializes some variables and
	//	"connects" available array positions 
	private void initialize() {
		tree = new int[3][size];
		for (int i = 0; i < size; i++) {
			tree[2][i] = i + 1;
		}
		avail = 0;
		root = 0;
		numOfNodes = 0;
	}
		
	//	inserting a key
	public int insert(int key) {
		int tries = 0;
		tries++;
		//is it the first element? (root)
		if (numOfNodes == 0) {
			int index = getNode();
			tree[0][index] = key;
			tree[1][index] = -1;
			tree[2][index] = -1;
			numOfNodes++;
		}
		else {
			int curr = root;
			while (true) {
				//searching proper position
				tries++;
				if (key < info(curr)) {
					tries++;
					if (left(curr) == -1) {
						int index = getNode();
						tree[1][curr] = index;
						tree[0][index] = key;
						tree[1][index] = -1;
						tree[2][index] = -1;
						numOfNodes++;
						break;
					}
					else {
						curr = left(curr);
					}
				}
				else if (key > info(curr)) {
					tries++;
					if (right(curr) == -1) {
						int index = getNode();
						tree[2][curr] = index;
						tree[0][index] = key;
						tree[1][index] = -1;
						tree[2][index] = -1;
						numOfNodes++;
						break;
					}
					else {
						curr = right(curr);
					}
				}
				else if (key == info(curr)) {
					//System.err.println("Key already exists.");
					break;
				}
			}
		}
		return tries;
	}
	
	
	//	removes key
	public int remove(int key) {
		tries = 0;
		
		tries++;
		// case that the key is the root of the tree
		if (info(root) == key) {
			tries++;
			// case 1: child is LEAF
			if (left(root) == -1 && right(root) == -1) {
				tree[1][root] = 0;
				freeNode(root);
			}
			// case 2: child has ONLY right subtree
			else if (left(root) == -1 && right(root) != -1) {
				tree[1][root] = 0;
				int temp = root;
				root = right(root);
				freeNode(temp);
			}
			// case 3: child has ONLY left subtree
			else if (right(root) == -1 && left(root) != -1) {
				int temp = root;
				root = left(root);
				freeNode(temp);
			}
			// case 4: child has TWO subtrees
			else {
				tries++;
				int max = 0;
				if (right(left(root)) == -1) { // case where parent of max = root
					max = left(root);
					tree[2][max] = right(root);
				}
				else {
					int parentMax = findParentOfMax(left(root));
					max = right(parentMax);
					tree[2][parentMax] = left(max);
					tree[1][max] = left(root);
					tree[2][max] = right(root);
				}
				freeNode(root);
				root = max;
			}
			numOfNodes--;
			return tries;
		}
		
		// find parent of child to be removed
		int parent = root;
		int child = root;
		while (true) {
			tries++;
			if (left(child) != -1 && key < info(child)) {
				parent = child;
				child = left(child);
				continue;
			}
			else if (right(child) != -1 && key > info(child)) {
				parent = child;
				child = right(child);
				continue;
			}
			else if (key == info(child)) {
				break;
			}
			else {
				//System.err.println("Key does not exist.");
				return tries;
			}
		}
		
		//	identifying which child to be removed (left or right)
		int childPos;
		tries++;
		if (left(parent) != -1 && key == info(left(parent))) {
			childPos = 1; // child is parent's left
		}
		else {
			childPos = 2; // child is parent's right
		}
		
		//	remove key (the child)
		tries++;
		// case 1: child is LEAF
		if (left(child) == -1 && right(child) == -1) {
			tree[childPos][parent] = -1;
		}
		// case 2: child has ONLY right subtree
		else if (left(child) == -1) {
			tree[childPos][parent] = tree[2][child];
		}
		// case 3: child has ONLY left subtree
		else if (right(child) == -1) {
			tree[childPos][parent] = tree[1][child];
		}
		// case 4: child has TWO subtrees
		else {
			//1) find child's left subtree's max and max's parent
			//	 will replace child in tree
			tries++;
			int max;
			if (right(left(child)) == -1) { // case where parent of max = child
				max = left(child);
				tree[1][parent] = max;
				tree[2][max] = right(child);
			}
			else {
				int parentMax = findParentOfMax(left(child));
				max = right(parentMax);
				//2) parent of child to be removed points at max as left child
				tree[childPos][parent] = max;
				//3) max's parent points at max's left child
				tree[2][parentMax] = left(max);
				//4) max points at both left and right child of the node to be removed
				tree[1][max] = left(child);
				tree[2][max] = right(child);
			}
		}
		
		freeNode(child);
		numOfNodes--;
		return tries;
	}
	
	
	//	searches for a key and returns SearchResult, that is:
	//		1) int: number of comparisons
	//		2) boolean: found or not
	//	not used in this program
	public SearchResult search(int key) {
		int treeRoot = root;
		SearchResult result = new SearchResult();
		while (true) {
			result.tries++;
			if (key == info(treeRoot)) {
				result.found = true;
				return result;
			}
			else if (key < info(treeRoot)) {
				if (left(treeRoot) == -1) {
					result.found = false;
					return result;
				}
				else {
					treeRoot = left(treeRoot);
				}
			}
			else {
				if (right(treeRoot) == -1) {
					result.found = false;
					return result;
				}
				else {
					treeRoot = right(treeRoot);
				}
			}
		}
		
	}
	
	
										/* USEFUL FUNCTIONS */
	
	//	returns data of node
	private int info(int pointer) {
		return tree[0][pointer];
	}
	
	//	returns array index/address where left child is stored
	private int left(int pointer) {
		return tree[1][pointer];
	}
	
	//	returns array index/address where right child is stored
	private int right(int pointer) {
		return tree[2][pointer];
	}

	//	pops from avail positions stack
	private int getNode() {
	    if (avail == -1) {
	    	return avail;
	    }
	    else {
	    	int pos = avail;
	        avail = right(avail);
	        return pos;
	    }
	}
	
	//	pushes to avail positions stack
	private void freeNode(int p) {
		tree[2][p] = avail;
	    avail = p;
	}
	
	
	//	finds and returns parent of max within a tree
	//	parent sees child, while child does not
	//	so essentially we need parent
	private int findParentOfMax(int r) {
		int max = r;
		while (true) {
			tries++;
			if (right(right(max)) == -1) {
				return max;
			}
			max = right(max);
		}
	}
	
	
	//	useful recursive print function
	//	won't be used in this program
	public void print(int index) {
		if (numOfNodes != 0) {
			int x = index;
			System.out.println(x + ": " + info(x) + " " + left(x) + " " + right(x));
			if (left(x) != -1) {
				print(left(x));
			}
			if (right(x) != -1) {
				print(right(x));
			}
		}
		else {
			System.err.println("Tree is empty.");
		}
	}
	
}