package predator;
import java.util.Scanner;

public class Predator {
	
	public static final int ANT_BREED_AGE = 3;
	public static final int BUG_BREED_AGE = 7;
	public static final int BUG_DEATH_AGE = 5;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulation sim = new Simulation();
		/*
		Scanner scan = new Scanner(System.in);
		System.out.println("Press enter to continue...");
		while (scan.nextLine().equals("")) {
			 if (sim.update() == false)
				 break;
			System.out.println("Press enter to continue...");
		} */
		while (sim.update()) {
			try {
			    Thread.sleep(100);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}

}
