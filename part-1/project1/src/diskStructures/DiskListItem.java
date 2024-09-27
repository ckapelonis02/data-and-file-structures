package diskStructures;

import myPackage.DataPage;

public class DiskListItem {
	public DataPage page;
	private long address; //address of page in file
	private DiskListItem next;

	//constructor
	public DiskListItem(DataPage page, long address) {
		this.page = page;
		this.address = address;
		this.next = null;
	}
	
	//setters and getters
	public DataPage getPage() {
		return page;
	}

	public void setPage(DataPage page) {
		this.page = page;
	}

	public long getAddress() {
		return address;
	}

	public void setAddress(long address) {
		this.address = address;
	}

	public DiskListItem getNext() {
		return next;
	}

	public void setNext(DiskListItem next) {
		this.next = next;
	}
}
