import java.util.*;
import java.text.*;

public class ComputeSquareRoot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// input n
		Scanner s = new Scanner(System.in);
		double n=0, r=0, guess=0, prev=0;
		Boolean negative = false, initialised = false;
		while (!initialised) {
			System.out.println("Please enter an number:");
			if (s.hasNextDouble()) {
				initialised = true;
				n = s.nextDouble();
				guess = n/2;
				prev = -guess;
			} else {
				System.out.println(s.next() + " is not a valid input.");
			}
		}
		
		// check for negatives
		if (n < 0) {
			n = -n;
			guess = -guess;
			prev = -prev;
			negative = true;
		}
		
		// find the square root
		while (guess/prev < 0.99 || guess/prev > 1.01) {
			r = n/guess;
			prev = guess;
			guess = (prev+r)/2;
		}
		
		// print result
		DecimalFormat f = new DecimalFormat("0.00");
		System.out.println("The square root of " + (negative ? "-" : "") + n + " is " + f.format(guess) + (negative ? "i" : "") + "!");
		s.close();
	}

}
