package memoryStructures;

import myPackage.DataStruct;
import myPackage.Point;
import myPackage.SearchResult;

//data structure of A2 question
public class MemHash extends DataStruct {
	public final int size = 50;
	public MemList[] array;
	
	// constructor
	public MemHash() {
		this.array = new MemList[size];
		for (int i = 0; i < size; i++) {
			array[i] = new MemList();
		}
	}
	
	// insert Point function
	public void insert(Point p) {
		int pos = findPosition(p);
		array[pos].insert(p);
	}
	
	// search function
	public SearchResult search(int x, int y) {
		return array[findPosition(new Point(x, y))].search(x, y);
	}
	
	// hash function H(x, y) = (x*N + y)%size
	public int findPosition(Point p) {
	     return (int)(((long)p.getX()*(Math.pow(2, 18)) + p.getY())%size);
	}
}