package myPackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
//		try {
//			PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
//			System.setOut(out);
//		} catch(IOException e){ }
		
		System.out.println("\nprogram initiated\n".toUpperCase());
		
		Tester t = new Tester();
		try {
			t.test();
		} catch (IOException e) {
			System.out.println("IOException error occurred.");
		}
		
		System.out.println("\nprogram terminated\n".toUpperCase());
		System.err.println("TELOS");
	}
	
}