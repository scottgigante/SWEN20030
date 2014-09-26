package car;

public class Vehicle {
	private final String registration;
	private final String make;
	private final int year;
	private int value;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getRegistration() {
		return registration;
	}
	public String getMake() {
		return make;
	}
	public int getYear() {
		return year;
	}
	
	public Vehicle(String registration, String make, int year, int value) {
		this.registration = registration;
		this.make = make;
		this.year = year;
		this.value = value;
	}
	
	public int age(int year) {
		return year - this.year;
	}
	public int quote() {
		return (int)(value*1.2);
	}
}
