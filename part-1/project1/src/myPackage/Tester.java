package myPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import diskStructures.DiskHash;
import diskStructures.DiskPageStruct;
import memoryStructures.MemHash;
import memoryStructures.MemList;

public class Tester {
	int testCases[] = {1000, 10000, 30000, 50000, 70000, 100000};
	Point[] pointsRef;
	Point[] existingRef;
	Point[] nonExistingRef;
	final double upperLimit = Math.pow(2, 18);
	final String fileNamePage = "page";
	final String fileNameHash = "hash";
	final int searches = 100;
	
	// testing
	public void test() throws IOException {
		FileController[] controller = new FileController[2];
		
		MemList memList;
		MemHash memHash;
		DiskPageStruct pageStruct;
		DiskHash diskHash;
		for (int i = 0; i < testCases.length; i++) {
			
			randomPoints(testCases[i]);
			
			deleteFile(fileNamePage);
			deleteFile(fileNameHash);
						
			controller[0] = new FileController(fileNamePage);
			controller[1] = new FileController(fileNameHash);
			memList = new MemList();
			memHash = new MemHash();
			pageStruct = new DiskPageStruct(controller[0]);
			diskHash = new DiskHash(controller[1]);
			
			for (int k = 0; k < pointsRef.length; k++) {
				memList.insert(pointsRef[k]);
				memHash.insert(pointsRef[k]);
				pageStruct.insert(pointsRef[k]);
				diskHash.insert(pointsRef[k]);
			}
		
			pageStruct.fullPageFunc();
			for (int x = 0; x < diskHash.size; x++) {
				diskHash.fullPageFunc(diskHash.array[x]);
			}
			
			System.out.println("\n\n------ For " + testCases[i] + " test cases ------\n");
			System.out.println("\nMemList: \n");
			searching(memList);
			System.out.println("\nMemHash: \n");
			searching(memHash);
			System.out.println("\nDiskPageStruct: \n");
			searching(pageStruct);
			System.out.println("\nDiskHash: \n");
			searching(diskHash);

			controller[0].file.close();
			controller[1].file.close();
		}
	}
	
	// constructs our arrays
	public void randomPoints(int num) {
		pointsRef = randPointArray(num, 0, (int)upperLimit);
		existingRef = subArray(pointsRef, searches);
		nonExistingRef = nonExisting(searches, 0, (int)upperLimit);
	}
	
	// searching functions
	// searches for 100 existing
	// and 100 non existing Points in struct
	// counts mistakes (assert mistakes = 0)
	// and prints (comparisons)/100 or (disk accesses)/100
	public void searching(DataStruct struct) {
		int mistakes = 0;
		long successfulTries = 0;
		long unsuccessfulTries = 0;
		SearchResult res;
		for (int i = 0; i < existingRef.length; i++) {
			res = struct.search(existingRef[i].getX(), existingRef[i].getY());
			successfulTries += res.getTries();
			if (res.found == false) {
				System.out.println(existingRef[i].toString());
				mistakes++;
			}
		}
		
		for (int i = 0; i < nonExistingRef.length; i++) {
			res = struct.search(nonExistingRef[i].getX(), nonExistingRef[i].getY());
			unsuccessfulTries += res.getTries();
			if (res.found == true) {
				System.out.println(nonExistingRef[i].toString());
				mistakes++;
			}
		}
		if (mistakes!=0) {
			System.out.println("MISTAKES: " + mistakes);
		}
		System.out.println("Successful: " + successfulTries/searches);
		System.out.println("Unsuccessful: " + unsuccessfulTries/searches);
	}

	//delete file if it exists
	public void deleteFile(String fileName) {
		Path fileToDeletePath = Paths.get(fileName);
		try {
			Files.delete(fileToDeletePath);
		} 
		catch (Exception e) {
			System.out.println("file '" +  fileName + "' created.");
		}
	}

	// useful functions
	// used to construct random Points
	public Point[] randPointArray(int size, int min, int max) {
		Point[] array  = new Point[size];
		int x, y;
		for (int i = 0; i < size; i++) {
			array[i] = new Point(-1, -1);
		}
		for (int i = 0; i < size; i++) {
			while (true) {
				x = ThreadLocalRandom.current().nextInt(min, max);
				y = ThreadLocalRandom.current().nextInt(min, max);
				if (!exists(array, x, y)) {
					break;
				}
			}
			array[i] = new Point(x, y);
		}
		return array;
	}
	
	public boolean exists(Point[] array, int x, int y) {
		for (int i = 0; i < array.length; i++) {
			if ((array[i].getX() == x) && (array[i].getY() == y )) {
				return true;
			}
		}
		return false;
	}
	
	public boolean exists(int[] array, int x) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == x) {
				return true;
			}
		}
		return false;
	}
	
	public Point[] subArray(Point[] array, int size) {
		Point[] subArray = new Point[size];
		int[] randIndexes;
		randIndexes = randInts(size, 0, array.length);
		for (int i = 0; i < randIndexes.length; i++) {
			subArray[i] = array[randIndexes[i]];
		}
		return subArray;
	}
	
	public int[] randInts(int size, int min, int max) {
		int[] array = new int[size];
		int x;
		for (int i = 0; i < size; i++) {
			array[i] = -1;
		}
		for (int i = 0; i < size; i++) {
			while (true) {
				x = ThreadLocalRandom.current().nextInt(min, max);
				if (!exists(array, x)) {
					break;
				}
			}
			array[i] = x;
		}
		return array;
		
	}
	
	public Point[] nonExisting(int num, int min, int max) {
		Point[] non = new Point[num];
		Point ref;
		int i = 0;
		while (i < num) {
			ref = new Point(ThreadLocalRandom.current().nextInt(min, max), ThreadLocalRandom.current().nextInt(min, max));
			if (!exists(pointsRef, ref.getX(), ref.getY())) {
				non[i] = ref;
				i++;
			}
		}
		return non;
	}
}
