package package1;

import java.text.*;

public class DisplayDecimal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double i = 0.5732;
		DecimalFormat formatter = new DecimalFormat("0.00%");
		
		System.out.println("The percentage is " + formatter.format(i));
	}

}
