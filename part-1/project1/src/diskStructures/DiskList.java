package diskStructures;

import java.io.IOException;
import java.nio.ByteBuffer;

import myPackage.DataPage;
import myPackage.FileController;
import myPackage.SearchResult;

public class DiskList {
	private DiskListItem head;
	private DiskListItem tail;
	public DataPage page;
	public int pageNum;
	
	// constructor
	public DiskList() {
		this.head = null;
		this.tail = null;
		this.page = new DataPage();
		this.pageNum = 0;
	}
	
	// insert DiskListItem function
	public void insert(DataPage fullPage, long address) {
		DiskListItem item = new DiskListItem(fullPage, address);
		if (head == null) {
			head = item;
			tail = item;
		}
		else {
			this.tail.setNext(item);
			this.tail = item;
		}
		this.page = new DataPage();
		this.pageNum++;
	}
	
	// adds point in current not full page
	public void addPoint(byte[] buffer) {
		page.addPoint(buffer);
	}
	
	// search function
	public SearchResult search(int x, int y, FileController controller) throws IOException {
		SearchResult result = new SearchResult();
		DiskListItem reference = head;
		boolean found = false;
		byte[] pageBuffer = new byte[256];
		for (int i = 0; i < pageNum; i++) {
			result.tries++;
			controller.readPage(pageBuffer, reference.getAddress());
			found = search(pageBuffer, x, y);
			if (found) {
				result.found = true;
				break;
			}
			reference = reference.getNext();
		}
		return result;
	}
	
	// searches for x,y in searching bytes
	public boolean search(byte[] searching, int x, int y) {
		byte[] xBytes = ByteBuffer.allocate(4).putInt(x).array();
		byte[] yBytes = ByteBuffer.allocate(4).putInt(y).array();
		
		for (int j = 0; j < 32; j++) {
			if ((searching[j*8] == xBytes[0]) && (searching[j*8 + 4] == yBytes[0]) &&
				(searching[j*8 + 1] == xBytes[1]) && (searching[j*8 + 4 + 1] == yBytes[1]) &&
				(searching[j*8 + 2] == xBytes[2]) && (searching[j*8 + 4 + 2] == yBytes[2]) &&
				(searching[j*8 + 3] == xBytes[3]) && (searching[j*8 + 4 + 3] == yBytes[3])) {
					return true;
			}
		}
	
		return false;
	}

	// setters and getters
	public DiskListItem getHead() {
		return this.head;
	}
	
	public DiskListItem getTail() {
		return this.tail;
	}
	
	// useful print function
	// not used in this program
	public void printList() {
		DiskListItem ref = head;
		System.out.println("List has " + pageNum + " pages:");
		for (int i = 0; i < pageNum; i++) {
			System.out.println("Page number " + i + ":");
			for (int j = 0; j < ref.page.buffer.length; j++) {
				if ((j + 1)%8 == 0) {
					System.out.println(" " + ref.page.buffer[j]);
				}
			}
			ref = ref.getNext();
		}
	}
}
