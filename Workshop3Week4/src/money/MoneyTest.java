package money;

public class MoneyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AustralianMoney m1 = new AustralianMoney();
		AustralianMoney m2 = new AustralianMoney(10);
		AustralianMoney m3 = new AustralianMoney(1,80);
		AustralianMoney m4 = new AustralianMoney(13.01);
		
		System.out.println(m1 + " + " + m2 + " = " + AustralianMoney.add(m1, m2));
		System.out.println(m3 + " - " + m4 + " = " + AustralianMoney.minus(m3,m4));
		
		
		
	}

}
