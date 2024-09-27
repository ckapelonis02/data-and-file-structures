package memoryStructures;

import myPackage.Point;

public class MemListItem {
	private Point data;
	private MemListItem next;
	
	// constructor
	public MemListItem(Point data) {
		this.data = data;
		this.next = null;
	}
	
	// getters and setters
	public void setData(Point point) {
		this.data= point;
	}
	
	public Point getData() {
		return this.data;
	}
	
	public void setNext(MemListItem item) {
		this.next = item;
	}
	
	public MemListItem getNext() {
		return this.next;
	}
	
}
