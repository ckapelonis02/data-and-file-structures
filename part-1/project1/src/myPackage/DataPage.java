package myPackage;

public class DataPage {
	public final int pageSize = 256;
	public final int pointSize = 8;
	public final int maxPoints = pageSize/pointSize;
	public byte[] buffer; // byte array for Points stored 
	public int numberOfPoints;
	
	// constructor
	public DataPage() {
		this.buffer = new byte[pageSize];
		this.numberOfPoints = 0;
	}
	
	// adding a single Point
	public void addPoint(byte[] point) {
		for(int i = 0; i < pointSize; i++) {
			this.buffer[numberOfPoints*pointSize + i] = point[i];
		}
		this.numberOfPoints++;
	}
		
	// useful function
	// checks if page is full or not
	public boolean isFull() {
		if (numberOfPoints == maxPoints) {
			return true;
		}
		else {
			return false;
		}
	}
	
}