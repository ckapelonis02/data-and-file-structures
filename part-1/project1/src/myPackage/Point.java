package myPackage;

public class Point extends Object {
	private int x;
	private int y;
	
	// constructor
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// setters and getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	// useful function for prints
	// not used in this program
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
