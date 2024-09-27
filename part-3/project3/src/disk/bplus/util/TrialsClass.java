package disk.bplus.util;

import java.io.IOException;

import disk.bplus.bptree.BPlusTreePerformanceCounter;

/**
 *
 * Wrapper class to run trials for a specific functionality
 *
 */
@SuppressWarnings("unused")
public class TrialsClass {
	public int[] insertions;
	public int[] searches;
	public int[] deletions;
	
	public TrialsClass(int[] i, int[] s, int[] d) {
		this.insertions = new int[i.length];
		for (int x = 0; x < i.length; x++) {
			insertions[x] = i[x];
		}
		this.searches = new int[s.length];
		for (int x = 0; x < s.length; x++) {
			searches[x] = s[x];
		}
		this.deletions = new int[d.length];
		for (int x = 0; x < d.length; x++) {
			deletions[x] = d[x];
		}
	}
	
    /**
     * Run a search trial
     *
     * @param trials the number of trials to run
     * @param rmin the min key value
     * @param rmax the max key value
     * @param unique want unique results?
     * @param bPerf performance class tied to a B+ Tree instance
     * @param verbose verbose results?
     * @throws IOException is thrown when an I/O operation fails
     * @throws InvalidBTreeStateException is thrown when there are inconsistencies in the blocks.
     */
    public int runSearchTrial(boolean unique,
                               BPlusTreePerformanceCounter bPerf)
            throws IOException, InvalidBTreeStateException {

        int pageReads = 0;
        int pageWrites = 0;
        int stats[];

        // trial loop
        for(int i = 0; i < searches.length; i++) {
        	stats = bPerf.searchIO(searches[i], unique);
            pageReads += stats[0];
            pageWrites += stats[1];
        }
        
        int diskAccesses = pageReads + pageWrites;
        
        return diskAccesses;
    }

    /**
     * Run a insertion trial
     *
     * @param trials the number of trials to run
     * @param rmin the min key value
     * @param rmax the max key value
     * @param value value to tie with the inserted key
     * @param unique allow duplicate insertions?
     * @param bPerf performance class tied to a B+ Tree instance
     * @param verbose verbose results?
     * @throws IOException is thrown when an I/O operation fails
     * @throws InvalidBTreeStateException is thrown when there are inconsistencies in the blocks.
     */
    public int runInsertTrial(String value, boolean unique,
                               BPlusTreePerformanceCounter bPerf)
            throws IOException, InvalidBTreeStateException {
        int pageReads = 0;
        int pageWrites = 0;
        Long key;
        int stats[];

        // trial loop
        for(int i = 0; i < insertions.length; i++) {
            key = (long) insertions[i];
            stats = bPerf.insertIO(key,
                    value == null ? key.toString() : value, unique);
            pageReads += stats[0];
            pageWrites += stats[1];
        }
        
        int diskAccesses = pageReads + pageWrites;
        
        return diskAccesses;
    }


    /**
     * Run a deletion trial
     *
     * @param trials number of trials to run
     * @param rmin the min key value
     * @param rmax the max key value
     * @param unique delete the *first* found or *all* found?
     * @param bPerf performance class tied to a B+ Tree instance
     * @param verbose verbose results?
     * @throws IOException is thrown when an I/O operation fails
     * @throws InvalidBTreeStateException is thrown when there are inconsistencies in the blocks.
     */
    public int runDeletionTrials(boolean unique,
                                  BPlusTreePerformanceCounter bPerf)
            throws IOException, InvalidBTreeStateException {
        int pageReads = 0;
        int pageWrites = 0;
        int stats[];

        // trial loop
        for(int i = 0; i < deletions.length; i++) {
            stats = bPerf.deleteIO(deletions[i], unique);
            pageReads += stats[0];
            pageWrites += stats[1];
        }
        
        int diskAccesses = pageReads + pageWrites;
        
        return diskAccesses;
    }
}
