package charilaospackage;

import java.io.IOException;

import disk.bplus.util.InvalidBTreeStateException;

public class Main {

	public static void main(String[] args) {
		System.out.println("START");
		Test t;
		float[][] results = {{0,0,0}, {0,0,0}, {0,0,0}, {0,0,0}, {0,0,0}};
		int x = 100000;
		for (int i = 1; i <= 10; i++) {
			System.out.println("\n\n\n\n\t\tNUMBER OF INSERTIONS: " + x*i);
			System.out.println();
			t = new Test(x*i);
			try {
				results[0] = t.bplustree(128);
				results[1] = t.bplustree(256);
				results[2] = t.btree(3);
				results[3] = t.btree(64);
				results[4] = t.avltree();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidBTreeStateException e) {
				e.printStackTrace();
			}
			System.out.println("B+Tree (page size = 128 bytes): ");
			System.out.println("\tComparisons per insertion: " + results[0][0]);
			System.out.println("\tComparisons per search: " + results[0][1]);
			System.out.println("\tComparisons per deletion: " + results[0][2]);
			System.out.println();
			System.out.println("B+Tree (page size = 256 bytes): ");
			System.out.println("\tComparisons per insertion: " + results[1][0]);
			System.out.println("\tComparisons per search: " + results[1][1]);
			System.out.println("\tComparisons per deletion: " + results[1][2]);
			System.out.println();
			System.out.println("B Tree (degree = 3): ");
			System.out.println("\tComparisons per insertion: " + results[2][0]);
			System.out.println("\tComparisons per search: " + results[2][1]);
			System.out.println("\tComparisons per deletion: " + results[2][2]);
			System.out.println();
			System.out.println("B Tree (degree = 64): ");
			System.out.println("\tComparisons per insertion: " + results[3][0]);
			System.out.println("\tComparisons per search: " + results[3][1]);
			System.out.println("\tComparisons per deletion: " + results[3][2]);
			System.out.println();
			System.out.println("AVL Tree: ");
			System.out.println("\tComparisons per insertion: " + results[4][0]);
			System.out.println("\tComparisons per search: " + results[4][1]);
			System.out.println("\tComparisons per deletion: " + results[4][2]);
		}
		
		System.out.println("\n\n\nEND");
	}
}
