package package1;
//Volume.java: converts a command-line radius to a volume

public class Volume {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
				System.out.println("Usage: Volume [RADIUS]");
				return;
		}
		Sphere s = new Sphere(Double.parseDouble(args[0]));
		double v = s.calculateVolume();
		System.out.println("The volume of a sphere of radius " + s.radius + " is " + v);
		
	}
	
}

class Sphere {
	
	public double radius;
	
	Sphere(double r) {
		this.radius = r;
	}
	
	public double calculateVolume() {
		double v = 4/3 * Math.PI * Math.pow(this.radius,  3);
		return v;
	}
}
