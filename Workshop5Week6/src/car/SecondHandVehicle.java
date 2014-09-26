package car;

public class SecondHandVehicle extends Vehicle {
	private final int numberOfOwners;
	
	public int getNumberOfOwners() {
		return numberOfOwners;
	}

	public SecondHandVehicle(String registration, String make, int year,
			int value, int numberOfOwners) {
		super(registration, make, year, value);
		this.numberOfOwners = numberOfOwners;
	}
	
	public boolean moreThanOnePreviousOwner() {
		return numberOfOwners > 2;
	}
	public int quote() {
		return (int)(getValue()*1.1);
	}

}
