package car;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecondHandVehicleTest {

	@Test
	public void testGetNumberOfOwners() {
		int i = 3;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", 1999, 500, i);
		if (v.getNumberOfOwners()!= i)
			fail("False");
	}

	@Test
	public void testMoreThanOnePreviousOwner() {
		int i = 3;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", 1999, 500, i);
		if (!v.moreThanOnePreviousOwner())
			fail("False");
		i = 2;
		v = new SecondHandVehicle("abc123", "toyota", 1999, 500, i);
		if (v.moreThanOnePreviousOwner())
			fail("False2");
	}

	@Test
	public void testGetValue() {
		int i = 500;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", 1999, i, 3);
		if (v.getValue()!= i)
			fail("False");
	}

	@Test
	public void testSetValue() {
		int i = 500, j = 300;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", 1999, i, 3);
		v.setValue(j);
		if (v.getValue()!= j)
			fail("False");
	}

	@Test
	public void testGetRegistration() {
		String i = "abc123";
		SecondHandVehicle v = new SecondHandVehicle(i, "toyota", 1999, 500, 3);
		if (v.getRegistration()!= i)
			fail("False");
	}

	@Test
	public void testGetMake() {
		String i = "toyota";
		SecondHandVehicle v = new SecondHandVehicle("abc123", i, 1999, 500, 3);
		if (v.getMake()!= i)
			fail("False");
	}

	@Test
	public void testGetYear() {
		int i = 1999;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", i, 500, 3);
		if (v.getYear()!= i)
			fail("False");
	}

	@Test
	public void testAge() {
		int i = 1999;
		SecondHandVehicle v = new SecondHandVehicle("abc123", "toyota", i, 500, 3);
		if (v.age(2014)!= 15)
			fail("False");
	}
	
	@Test
	public void testQuote() {
		int i = 500;
		SecondHandVehicle v1 = new SecondHandVehicle("abc123", "toyota", 1999, i, 3);
		Vehicle v2 = new SecondHandVehicle("abc123", "toyota", 1999, i, 3);
		Vehicle v3 = new Vehicle("abc123", "toyota", 1999, i);
		if (v1.quote()!= (int)(i*1.1))
			fail("False1");
		if (v2.quote()!= (int)(i*1.1))
			fail("False2");
		if (v3.quote()!= (int)(i*1.2))
			fail("False3");
	}

}
