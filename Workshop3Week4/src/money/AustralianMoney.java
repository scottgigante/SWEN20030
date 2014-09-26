package money;

/**
 * @author gigantes
 *
 */
public class AustralianMoney {
	private int dollars;
	private int cents;

	// constructor
	public AustralianMoney() {
		super();
		dollars = 0;
		cents = 0;
	}
	/**
	 * @param dollars
	 */
	public AustralianMoney(int dollars) {
		super();
		this.dollars = dollars;
		cents = 0;
	}
	public AustralianMoney(int dollars, int cents) {
		super();
		this.dollars = dollars;
		this.cents = cents;
		checkCents();
	}
	public AustralianMoney(double value) {
		super();
		this.setValue(value);
	}
	
	// get / set
	/**
	 * @return
	 */
	public int getDollars() {
		return dollars;
	}
	/**
	 * @param dollars
	 */
	public void setDollars(int dollars) {
		this.dollars = dollars;
	}
	public int getCents() {
		return cents;
	}
	public void setCents(int cents) {
		this.cents = cents;
		checkCents();
	}
	public double getValue() {
		return dollars + cents/100.0;
	}
	public void setValue(double value) {
		int intValue = (int)(value * 100);
		dollars = intValue / 100;
		cents = intValue % 100;
	}
	
	// facilitators
	public String toString() {
		return (getValue() >= 0 ? "" : "-") + "$" + Math.abs(dollars) + "." + Math.abs(cents);
	}
	public boolean equals(AustralianMoney m) {
		return (m.dollars == dollars) && (m.cents == cents);
	}
	
	// other methods
	private void checkCents() {
		while (cents > (getValue() >= 0 ? 99 : 0)) {
			cents -= 100;
			dollars += 1;
		}
		while (cents < (getValue() >= 0 ? 0 : -99)) {
			cents += 100;
			dollars += 1;
		}
	}
	public void add(AustralianMoney m) {
		dollars += m.dollars;
		cents += m.cents;
		checkCents();
	}
	
	// static methods
	public static AustralianMoney add(AustralianMoney m1, AustralianMoney m2) {
		return new AustralianMoney(m1.dollars + m2.dollars, m1.cents + m2.cents);
	}
	public static AustralianMoney minus(AustralianMoney m1, AustralianMoney m2) {
		return new AustralianMoney(m1.dollars - m2.dollars, m1.cents - m2.cents);
	}
}
