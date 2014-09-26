package Student;

public class StudentPair {
	// object attributes
	private Student s1;
	private Student s2;
	
	// constructor
	public StudentPair() {
		this.s1 = new Student();
		this.s2 = new Student();
	}
	public StudentPair(Student s1, Student s2) {
		this.s1 = s1;
		this.s2 = s2;
	}
	
	// get/set
	public String getName(int i) {
		if (i == 1) {
			return s1.getName();
		} else {
			return s2.getName();
		}
	}
	public void setName(int i, String name) {
		if (i == 1) {
			s1.setName(name);
		} else {
			s2.setName(name);
		}
	}
	
	public int getID(int i) {
		if (i == 1) {
			return s1.getId();
		} else {
			return s2.getId();
		}
	}
	public void setID(int i, int id) {
		if (i == 1) {
			s1.setId(id);
		} else {
			s2.setId(id);
		}
	}
	
	// TODO: can't be bothered
}
