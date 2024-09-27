package myPackage;

public class SearchResult extends Object {
	public boolean found;
	public int tries;
	
	// constructor
	public SearchResult() {
		this.found = false;
		this.tries = 0;
	}

	// getters and setters
	public boolean getFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}
	
	// useful function for prints
	// not used in this program
	public String toString() {
		String s = "Point found: " + found + "\nTries: " + tries; 
		return s;
	}
	
	
}
