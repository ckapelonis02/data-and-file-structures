package myPackage;

// Main: prints results of test
public class Main {
	public static void main(String[] args) {

		//reading the files
		String insInts = "keys_1000000_BE.bin";
		String delInts = "keys_del_100_BE.bin";
		int insNum = 1000000;
		int delNum = 100;
		int[] ins = FileController.readInts(insInts, insNum);
		int[] del = FileController.readInts(delInts, delNum);
		
		//conducting the test
		System.out.println("\t\t\t--- S T A R T ---\n\n");
		
		Tester t = new Tester();
		t.Test(ins, del, insNum, delNum);
		
		System.out.println(t.pinakas1);
		System.out.println("\n\n");
		System.out.println(t.pinakas2);
		
		System.out.println("\n\t\t\t--- E N D ---\n\n");
	}
}