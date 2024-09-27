package diskStructures;

import java.io.IOException;
import java.nio.ByteBuffer;

import myPackage.DataStruct;
import myPackage.FileController;
import myPackage.Point;
import myPackage.SearchResult;

// data structure of B2 question
public class DiskHash extends DataStruct {
	public final int size = 50; // size of DiskList array
	public DiskList[] array; // disklist array
	public DiskList list; // reference to current list
	public int position; // hash function returns int
	public int allPages;
	public byte[] xBytes;
	public byte[] yBytes;
	public byte[] pointBytes;
	public FileController controller;
	
	// constructor
	public DiskHash(FileController controller) {
		this.controller = controller;
		this.array = new DiskList[size];
		this.allPages = 0;
		this.pointBytes = new byte[8];
		for (int i = 0; i < size; i++) {
			array[i] = new DiskList();
		}
	}
	
	// insert Point function
	public void insert(Point p)  {
		xBytes = ByteBuffer.allocate(4).putInt(p.getX()).array();
		yBytes = ByteBuffer.allocate(4).putInt(p.getY()).array();
		for (int i = 0; i < 4; i++) {
			pointBytes[i] = xBytes[i];
			pointBytes[i+4] = yBytes[i];
		}
		position = findPosition(p);
		list = array[position];
		if (list.page.isFull()) {
			fullPageFunc(list);
		}
		list.addPoint(pointBytes);
	}
	
	// useful function
	// used for manually inserting last not full page in DiskList
	public void fullPageFunc(DiskList list) {
		try {
			controller.writePage(list.page.buffer);
		} catch (IOException e) {
			System.out.println("IOException problem ocurred.");
		}
		list.insert(list.page, allPages*256);
		this.allPages++;
	}
	
	// search function
	public SearchResult search(int x, int y) {
		position = findPosition(new Point(x, y));
		try {
			return array[position].search(x, y, controller);
		} catch (IOException e) {
			System.out.println("IOException problem ocurred.");
			return null;
		}
	}
	
	// hash function H(x, y) = (x*N + y)%size
	public int findPosition(Point p) {
	     return (int)(((long)p.getX()*(Math.pow(2, 18)) + p.getY())%size);
	}
	
	// useful print functions 
	// not used in this program
	public void printItemNums() {
		for (int i = 0; i < array.length; i++) {
			System.out.println("List number " + i + " has " + array[i].pageNum + " page(s).");
		}
	}
	
	public void printArray() {
		for (int i = 0; i < array.length; i++) {
			array[i].printList();
		}
	}
	
	public void printFile() throws IOException {
		byte[] temp = new byte[256];
		for (int i = 0; i < allPages; i++) {
			controller.readPage(temp, i*256);
			for (int j = 0; j < 256; j ++) {
				System.out.print(temp[j] + " ");
				if (j%8 == 0) {
					System.out.println("");
				}
			}
			System.out.println("\nPage " + i);
		}
	}
}