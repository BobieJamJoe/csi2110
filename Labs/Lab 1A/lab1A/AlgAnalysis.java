
// CSI2110 Fall 2015 Laboratory 1A: Algorithm Runtimes
// ==========================================================================
// (C)opyright:
//
//   Lachlan Plant
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, ON, K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: lplant (Lachlan Plant)
// Email:   lplan053@uottawa.ca
// ==========================================================================
// $Log: Lab00.java,v $
// Revision 1.0  2015/09/14 01:20:40  lplant
// Revision 1.1  2015/09/19 11:21:00  Lucia Moura
// ==========================================================================
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class AlgAnalysis {

	/**
	 * Tests runtimes of Arrays.sort()
	 * Runs experiments to find the average time taken to sort arrays of n elements
	 * prints results directly
	 *
	 * @param  maxSize size of largest array to be tested
	 * @param  count number of arrays tested.
	 */
	public static void arraySortRuntime(int count, int maxSize){
		long maxTime = 0;
		int[] arr;
		int interval = maxSize / count;
		if (interval == 0) interval = 1;
		for (int i = 0; i < count; i++) {
			
			int n = interval*(i + 1);
			
			for (int j = 0; j < 10; j++) {
				arr = randomizeArray(genArray(n));
				long start = System.nanoTime();
				Arrays.sort(arr);
				long tn = System.nanoTime() - start;
				if (tn > maxTime) maxTime = tn;
			}

			System.out.println("\n");
			System.out.println("---------- Test " + (i + 1) + " ----------");
			System.out.println("n  = " + n);
			System.out.println("T(n) = " + maxTime);
			System.out.println("T(n) / (" + n + "^2) = " + maxTime / Math.pow(n, 2));
			System.out.println("T(n) / (" + n + "*log("+ n +")) = " + maxTime / (n*Math.log(n)));
			maxTime = 0;
		}
	}
	
	/**
	 * creates an array of size n, then tests the runtime of findDups1 using that array
	 *
	 * @param  n size of array
	 * @return time taken in nano seconds
	 */
	public static long unique1Runtime(int n){
		int[] arr = genArray(n);
		long start = System.nanoTime();
		Unique1.unique1(arr);
		return System.nanoTime() - start;
	}
	
	/**
	 * creates an array of size n, then tests the runtime of findDups2 using that array
	 *
	 * @param  n size of array
	 * @return time taken in nano seconds
	 */
	public static long unique2Runtime(int n){
		int[] arr = genArray(n);
		long start = System.nanoTime();
		Unique1.unique1(arr);
		return System.nanoTime() - start;
	}

	/**
	 *
	 * Tests unique1() and unique2() with an array whose size increases exponentially.
	 * Outputs times for each run of each algorithm to a text file until each
	 * algorithm exceeds 60 seconds.
	 *
	 * @param filename	name of file to output results
	 * @param base		factor that is size of the array is multiplied by
	 */
	public static void uniqueTester(String filename, int base) {
		try {
			File file = new File(filename);
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("Warning: file name already exists. Results will be outputted to existing file");
		}
		
		try {
			FileWriter writer = new FileWriter(filename);
			int i = 1;
			writer.write("unique1:\n");
			while ((Math.pow(base, i) < Math.pow(2, 30)) && (nanoToSeconds(unique1Runtime((int) Math.pow(base, i))) < 60)) {
				writer.write("\tn = " + Math.pow(base, i) + "\n");
				i++;
			}
			writer.write("\t Largest size within 1 minute: n = " + Math.pow(base, i - 2));
			i = 1;
			writer.write("unique2:\n");
			while ((Math.pow(base, i) < Math.pow(2, 30)) && (nanoToSeconds(unique2Runtime((int) Math.pow(base, i))) < 60)) {
				writer.write("\tn = " + Math.pow(base, i) + "\n");
				i++;
			}
			writer.write("\t Largest size within 1 minute: n = " + Math.pow(base, i - 2));
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates an array of ints of size n
	 * Array contains values array[i]=i
	 *
	 * @param  n size of array
	 * @return ordered array
	 */
	private static int[] genArray(int n){
		int[] ret = new int[n];
		for(int i=0; i < ret.length; i++){
			ret[i] = i;
		}
		return ret;
	}
	
	/**
	 * Randomly shuffles an array
	 *
	 * @param  array array of ints to be shuffled
	 * @return      randomized array
	 */
	private static int[] randomizeArray(int[] array){
		Random rng = new Random();  		
 
		for (int i=array.length -1; i > 0; i--) {
			//rng.nextInt(N) returns random number between 0 and N-1 inclusive
			int randomPosition = rng.nextInt(i);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}	
	
	/**
	 * Convert time in nanoseconds to seconds
	 *
	 * @param  time time in nanoseconds
	 * @return      time in seconds as a double
	 */
	private static double nanoToSeconds(long time){
		return (double)time / 1000000000.0;
	}

	/**
	 * Main method
	 * Provides prompts for all experiments
	 */
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("Enter 0 to test Arrays.sort(), 1 for unique, any other number to exit");
			int opt1 = scanner.nextInt();
			if(opt1 == 0){
				System.out.println("Enter number of arrays to test");
				int count = scanner.nextInt();
				System.out.println("Enter largest array size");
				int n = scanner.nextInt();
				AlgAnalysis.arraySortRuntime(count, n);			
				System.out.println();
			}
			else if(opt1 == 1){
				while(true){
					System.out.println("Enter 1 to test unique1, 2 for unique2, 3 to test both, and any other number to exit");
					int opt2 = scanner.nextInt();
					if(opt2 == 1){
						System.out.println("Enter n value");
						int n = scanner.nextInt();
						System.out.println("Time Elapsed: "+AlgAnalysis.nanoToSeconds(AlgAnalysis.unique1Runtime(n))+" secs");
						System.out.println();
					}
					else if(opt2 == 2){
						System.out.println("Enter n value");
						int n = scanner.nextInt();
						System.out.println("Time Elapsed: "+AlgAnalysis.nanoToSeconds(AlgAnalysis.unique2Runtime(n))+" secs");
						System.out.println();
					}
					else if(opt2 == 3){
						System.out.println("Enter a filename (with extension) to store the results");
						String filename = scanner.next();
						System.out.println("Enter a base size to test exponentially");
						int base = scanner.nextInt();
						System.out.println("Beginning test...");
						uniqueTester(filename, base);
						System.out.println("Test complete. Examine results in " + filename);
					}
					else{
						System.out.println("End of Program!");
						return;
					}
				}
			}
			else {
				System.out.println("End of Program!");
				return;
			}
			
		}
	}

}