package diskStructures;

import java.io.IOException;
import java.nio.ByteBuffer;

import myPackage.DataPage;
import myPackage.DataStruct;
import myPackage.FileController;
import myPackage.Point;
import myPackage.SearchResult;

//data structure of B1 question
public class DiskPageStruct extends DataStruct {
	public int allPages; // sum of all pages
	public DataPage page; // current page reference
	public FileController controller; 
	public byte[] xBytes;
	public byte[] yBytes;
	public byte[] pointBytes;
	
	// constructor
	public DiskPageStruct(FileController controller) {
		this.controller = controller;
		this.allPages = 0;
		this.page = new DataPage();
		this.pointBytes = new byte[8];
	}
	
	// insert Point function
	public void insert(Point p) {
		xBytes = ByteBuffer.allocate(4).putInt(p.getX()).array();
		yBytes = ByteBuffer.allocate(4).putInt(p.getY()).array();
		for (int i = 0; i < 4; i++) {
			pointBytes[i] = xBytes[i];
			pointBytes[i+4] = yBytes[i];
		}
		if (page.isFull()) {
			fullPageFunc();
		}
		page.addPoint(pointBytes);
	}
	
	// useful function
	// manually inserts last not full page in structure
	public void fullPageFunc() {
		try {
			controller.writePage(page.buffer);
		} catch (IOException e) {
			System.out.println("IOException problem ocurred.");
		}
		page = new DataPage();
		this.allPages++;
	}
	
	// search function
	public SearchResult search(int x, int y) {
		SearchResult result = new SearchResult();
		boolean found = false;
		byte[] pageBuffer = new byte[256];
		for (int i = 0; i < allPages; i++) {
			result.tries++;
			try {
				controller.readPage(pageBuffer, i*256);
			} catch (IOException e) {
				System.out.println("IOException problem ocurred.");
			}
			
			found = search(pageBuffer, x, y);
			if (found) {
				result.found = true;
				break;
			}
		}
		return result;	
	}
	
	// searches x, y in searching bytes
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
}
