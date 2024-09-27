package BinarySearchTree;

import myPackage.SearchResult;

/**
 * 
 * Binary Search Tree (BST) based on 
 * dynamically allocating memory.
 * 
 * Code modified from Marcos Lopez Gonzalez (https://www.baeldung.com/java-binary-tree)
 * 
 */

public class DynamicBST {
    private TreeNode root;	//root reference
    private int tries;	//used as global variable to count comparisons

    //	creates and inserts node with given value
    public int insert(int value) {
    	tries = 0;
    	TreeNode n = insertRecursive(root, value);
    	tries++;
        if (n != null) {
        	root = n;
        }
        return tries;
    }
    
    private TreeNode insertRecursive(TreeNode current, int value) {
    	tries++;
        if (current == null) {
            return new TreeNode(value);
        }
        else {
        	tries++;
	        if (value < current.value) {
	            current.left = insertRecursive(current.left, value);
	        }
	        else if (value > current.value){
	            current.right = insertRecursive(current.right, value);
	        }
	        else {
	        	//System.err.println("Key already exists.");
	        	return null;
	        }
        }
        return current;
    }
    
    //	removes Node with given value
    public int remove(int value) {
    	tries = 0;
        root = deleteRecursive(root, value);
        return tries;
    }
    
    private TreeNode deleteRecursive(TreeNode current, int value) {
    	tries++;
        if (current == null) {
            return null;
        }
        else {
        	tries++;
	        if (value == current.value) {
	        	tries++;
	        	if (current.left == null && current.right == null) {
	        	    return null;
	        	}
	        	if (current.right == null) {
	        	    return current.left;
	        	}
	        	if (current.left == null) {
	        	    return current.right;
	        	}
	        	int smallestValue = findSmallestValue(current.right);
	        	current.value = smallestValue;
	        	current.right = deleteRecursive(current.right, smallestValue);
	        }
	        else if (value < current.value) {
	            current.left = deleteRecursive(current.left, value);
	        }
	        else {
		        current.right = deleteRecursive(current.right, value);
	        }
	        return current;
        }
    }
    
    									/* USEFUL FUNCTIONS */
    
    //	finds min within a tree
	private int findSmallestValue(TreeNode root) {
		tries++;
	    return root.left == null ? root.value : findSmallestValue(root.left);
	}
	
	//	searches for a Node
	//	is not used in this program
	public SearchResult search(int value) {
    	SearchResult result = new SearchResult();
        result.found = searchRecursive(root, value, result);
        return result;
    }
    
    private boolean searchRecursive(TreeNode current, int value, SearchResult result) {
        if (current == null) {
            return false;
        }
        result.tries++;
        if (value == current.value) {
            return true;
        }
        return value < current.value
          ? searchRecursive(current.left, value, result)
          : searchRecursive(current.right, value, result);
    }
}