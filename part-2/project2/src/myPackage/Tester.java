package myPackage;

import BinarySearchTree.ArrayBST;
import BinarySearchTree.DynamicBST;
import Heap.ArrayHeap;
import Heap.DynamicHeap;

/**
 * 
 * Tester conducts tests for extracting results
 * of insertions, deletions etc of the data structures.
 * Deals with elapsed time and comparisons.
 * 
 * @author Charilaos Kapelonis
 *
 */

public class Tester {
	public String pinakas1 = "";
	public String pinakas2 = "";
	//arrays for storing results
	float[] comparisons = new float[8];
	double[] times = new double[10];
	long comp = 0;
	double time = 0;
	long nano = 1000000000; // nano*time(nanoseconds)=time(seconds)
	
	@SuppressWarnings("unused")
	public void Test(int[] ins, int[] del, int insNum, int delNum) {
		//defining the data structures
		DynamicBST tree1 = new DynamicBST();
		ArrayBST tree2 = new ArrayBST(insNum);
		ArrayHeap heap1 = new ArrayHeap(insNum);
		DynamicHeap heap2 = new DynamicHeap();
		ArrayHeap heap3;
		DynamicHeap heap4;
		
		//calculating elapsed time and comparisons per insertion
		time = System.nanoTime();
		for (int i = 0; i < insNum; i++) {
			comp += tree1.insert(ins[i]);
		}
		times[0] = (System.nanoTime() - time)/nano;
		comparisons[0] = comp/insNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < insNum; i++) {
			comp += tree2.insert(ins[i]);
		}
		times[1] = (System.nanoTime() - time)/nano;
		comparisons[1] = comp/insNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < insNum; i++) {
			comp += heap1.insert(ins[i]);
		}
		times[2] = (System.nanoTime() - time)/nano;
		comparisons[2] = comp/insNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < insNum; i++) {
			comp += heap2.insert(ins[i]);
		}
		times[3] = (System.nanoTime() - time)/nano;
		comparisons[3] = comp/insNum;
		comp = 0;
		
		
		
		time = System.nanoTime();
		heap3 = new ArrayHeap(ins, insNum);
		times[8] = (System.nanoTime() - time)/nano;
		
		time = System.nanoTime();
		heap4 = new DynamicHeap(ins);
		times[9] = (System.nanoTime() - time)/nano;
		
		
		
		
		//calculating elapsed time and comparisons per deletion
		time = System.nanoTime();
		for (int i = 0; i < delNum; i++) {
			comp += tree1.remove(del[i]);
		}
		times[4] = (System.nanoTime() - time)/nano;
		comparisons[4] = comp/delNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < delNum; i++) {
			comp += tree2.remove(del[i]);
		}
		times[5] = (System.nanoTime() - time)/nano;
		comparisons[5] = comp/delNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < delNum; i++) {
			comp += heap1.removeMax();
		}
		times[6] = (System.nanoTime() - time)/nano;
		comparisons[6] = comp/delNum;
		comp = 0;
		
		time = System.nanoTime();
		for (int i = 0; i < delNum; i++) {
			comp += heap2.removeMax();
		}
		times[7] = (System.nanoTime() - time)/nano;
		comparisons[7] = comp/delNum;
		comp = 0;
		
		
		pinakas1 +=
				"DynamicBST:\n" +
				"\nComparisons per insertion:\n" + comparisons[0] +
				"\nInsertion Time:\n" + times[0] + " seconds" +
				"\nComparisons per deletion:\n" + comparisons[4] +
				"\nDeletion Time:\n" + times[4] + " seconds\n\n" +
				
				"ArrayBST:\n" +
				"\nComparisons per insertion:\n" + comparisons[1] +
				"\nInsertion Time:\n" + times[1] + " seconds" +
				"\nComparisons per deletion:\n" + comparisons[5] +
				"\nDeletion Time:\n" + times[5] + " seconds\n\n"; 
	
	
		pinakas2 += 
				"ArrayHeap:\n" +
				"\nBuilding Time:\n" + times[8] + " seconds" +
				"\nInsertion Time:\n" + times[2] + " seconds" +
				"\nComparisons per insertion:\n" + comparisons[2] +
				"\nComparisons per deletion:\n" + comparisons[6] +
				"\nDeletion Time:\n" + times[6] + " seconds\n\n" +
				
				"DynamicHeap:\n" +
				"\nBuilding Time:\n" + times[9] + " seconds" +
				"\nInsertion Time:\n" + times[3] + " seconds" +
				"\nComparisons per insertion:\n" + comparisons[3] +
				"\nComparisons per deletion:\n" + comparisons[7] +
				"\nDeletion Time:\n" + times[7] + " seconds\n";
	}
}
