package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileController {
	public File myFile; // useful wrapper so that we can delete the file
	public RandomAccessFile file; // wrapped by File
	
	// constructor
	public FileController(String fileName) throws FileNotFoundException {
		this.myFile = new File(fileName);
		this.file = new RandomAccessFile(myFile, "rw");
	}
	
	// writes buffer of bytes in file
	public void writePage(byte[] buffer) throws IOException {
		file.seek(file.getFilePointer());
		file.write(buffer);
	}
	
	// reads buffer of bytes from file
	public void readPage(byte[] temp, long address) throws IOException {
		file.seek(address);
		file.read(temp);
	}
	
}
