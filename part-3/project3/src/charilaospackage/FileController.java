package charilaospackage;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * 
 * FileController deals with file reads.
 * 
 * @author Charilaos Kapelonis
 *
 */

public class FileController {
	//	static: reads from a file (named fileName), a number of 
	//	integers (num) and returns an integer array with them.
	public static int[] readInts(String fileName, int num) {
		int array[] = new int[num];
		try {
			int i = 0;
			DataInputStream in = new DataInputStream(new FileInputStream(fileName));
			while (i != num) { 
		        array[i] = in.readInt();
		        i++;
		    }
		    in.close();
		} catch(Exception e) {
			System.err.println("Problem with file occurred.");
			e.printStackTrace();
		}
		
		return array;
	}
}
