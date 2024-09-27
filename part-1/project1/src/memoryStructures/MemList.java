package memoryStructures;

import myPackage.DataStruct;
import myPackage.Point;
import myPackage.SearchResult;

//data structure of A1 question
public class MemList extends DataStruct {
	private MemListItem head;
	private MemListItem tail;
	
	// constructor
	public MemList() {
		this.head = null;
		this.tail = null;
	}

	// insert Point function
	public void insert(Point point) {
		MemListItem item = new MemListItem(point);
		// for the first item inserted
		if (head == null) {
			head = item;
			tail = item;
		}
		else {
			this.tail.setNext(item);
			this.tail = item;
		}
	}
	
	// search function
	public SearchResult search(int x, int y) {
		SearchResult result = new SearchResult();
		MemListItem reference = head;
		while (reference != null) {
			result.tries++;
			if ((reference.getData().getX() == x) && (reference.getData().getY() == y)) {
				result.found = true;
				break;
			}
			if (reference.getNext() == null) {
				break;
			}
			reference = reference.getNext();
		}
		
		return result; 
	}
	
	// setters and getters
	public MemListItem getHead() {
		return this.head;
	}
	
	public MemListItem getTail() {
		return this.tail;
	}
}

