package charilaospackage;

import java.io.IOException;

import disk.bplus.bptree.BPlusConfiguration;
import disk.bplus.bptree.BPlusTree;
import disk.bplus.bptree.BPlusTreePerformanceCounter;
import disk.bplus.util.InvalidBTreeStateException;
import disk.bplus.util.TrialsClass;
import memory.avltree.AVLTree;
import memory.btree.BTree;

public class Test {
	final int testCases = 100;
	int[] constructionIns;
	int[] ins;
	int[] sear;
	int[] del;
	
	public Test(int n) {
		constructionIns = FileController.readInts("keys_1000000_BE.bin", n);
		ins = FileController.readInts("keys_insert_100_BE.bin", testCases);
		sear = FileController.readInts("keys_search_100_BE.bin", testCases);
		del = FileController.readInts("keys_delete_100_BE.bin", testCases);
	}
	
	public float[] bplustree(int pageSize) throws IOException, InvalidBTreeStateException {
		BPlusConfiguration btconf = new BPlusConfiguration(pageSize, (pageSize==256) ? 20 : 1);
		BPlusTreePerformanceCounter bPerf = new BPlusTreePerformanceCounter(true);
		TrialsClass t = new TrialsClass(ins, sear, del);
		BPlusTree bplustree = new BPlusTree(btconf, "rw+", bPerf);
		for (int i = 0; i < constructionIns.length; i++) {
			bPerf.insertIO(constructionIns[i], null, false);
		}
		float[] c = {0,0,0};
		c[0] += t.runInsertTrial(null, false, bPerf);
		c[1] += t.runSearchTrial(false, bPerf);
		c[2] += t.runDeletionTrials(false, bPerf);
		
		bplustree.commitTree();

		for (int i = 0; i < 3; i++) {
			c[i]/=100;
		}
		
		return c;
	}
	
	public float[] btree(int degree) {
		BTree<Integer, String> btree = new BTree<Integer, String>(degree);
		float[] c = {0,0,0};
		for (int i = 0; i < constructionIns.length; i++) {
			btree.put(constructionIns[i], "");
		}
		for (int i = 0; i < ins.length; i++) {
			c[0] += btree.put(ins[i], "");
		}
		for (int i = 0; i < sear.length; i++) {
			c[1] += btree.get(sear[i]);
		}
		for (int i = 0; i < del.length; i++) {
			c[2] += btree.put(del[i], null);
		}

		for (int i = 0; i < 3; i++) {
			c[i]/=100;
		}
		
		return c;
	}
	
	public float[] avltree() {
		AVLTree avltree = new AVLTree();
		float[] c = {0,0,0};
		for (int i = 0; i < constructionIns.length; i++) {
			avltree.insert(constructionIns[i]);
		}
		for (int i = 0; i < ins.length; i++) {
			c[0] += avltree.insert(ins[i]);
		}
		for (int i = 0; i < sear.length; i++) {
			c[1] += avltree.find(sear[i]);
		}
		for (int i = 0; i < del.length; i++) {
			c[2] += avltree.delete(del[i]);
		}
		
		for (int i = 0; i < 3; i++) {
			c[i]/=100;
		}
		
		return c;
	}
}