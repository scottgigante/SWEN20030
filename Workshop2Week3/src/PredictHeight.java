import java.util.Scanner;


public class PredictHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		Boolean quit=false;
		while (!quit) {
			double mother=0, father=0, child=0;
			Boolean init = false, male = false;
			String input;
			
			// input father's height
			while (!init && !quit) {
				System.out.println("Please enter the father's height in inches, or \"q\" at any time to exit:");
				if (s.hasNextDouble()) {
					father = s.nextDouble();
					if (father <= 0) {
						System.out.println("Please enter a positive number.");
					} else {
						init = true;
					}
				} else if ((input = s.next()).matches("q")) {
					quit = true;
				} else {
					System.out.println(input + " is not a valid input.");
				}
			}
			
			// input mother's height
			init = false;
			while (!init && !quit) {
				System.out.println("Please enter the mother's height in inches:");
				if (s.hasNextDouble()) {
					mother = s.nextDouble();
					if (mother <= 0) {
						System.out.println("Please enter a positive number.");
					} else {
						init = true;
					}
				} else if ((input = s.next()).matches("q")) {
					quit = true;
				} else {
					System.out.println(input + " is not a valid input.");
				}
			}
			
			// input gender
			init = false;
			while (!init && !quit) {
				System.out.println("Please enter the child's gender: (M or F)");
				input = s.next();
				input = input.toLowerCase();
				if (input.matches("m")) {
					male = true;
					init = true;
				} else if (input.matches("q")) {
						quit = true;
				} else if (input.matches("f")) {
					init = true;
				} else {
					System.out.println(input + " is not a valid input.");
				}
			}
			
			if (!quit) {
				// calculate height
				if (male) {
					child = ((mother*13/12)+father)/2;
				} else {
					child = ((father*12/13)+mother)/2;
				}
				
				// print result
				System.out.printf("The child is predicted to be %.2f inches tall.\n", child);
			}
		}
		s.close();
	}

}
