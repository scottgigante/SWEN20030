package Student;
public class Student {
	
    // class attributes
    private static int numMarks = 3;
    private static int totalStudents;
    private static int totalMarks;
    
    // class methods
    public static int getTotalStudents() {
    	return totalStudents;
    }
    
    public static int getTotalMarks() {
    	return totalMarks;
    }
    
    public static double getAverageMark() {
    	return (double)totalMarks / (totalStudents * numMarks);
    }
    
    // object attributes
    private int id;
    private String name;
    private int[] marks = {0,0,0};
    
    // get/set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMark(int i) {
        return marks[i];
    }
    public void setMark(int i, int mark) {
    	totalMarks += mark - this.marks[i];
        this.marks[i] = mark;
    }
   
    // constructor
    public Student() {
    	this.id = 0;
    	this.name = "";
    	this.marks = new int[] {0,0,0};
    	totalStudents++;
    }
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    	this.marks = new int[] {0,0,0};
    	totalStudents++;
    }    
    public Student(int id, String name, int[] marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        for (int i=0;i<numMarks;i++) {
        	totalMarks += this.marks[i];
        }
    	totalStudents++;
    }    
    public Student(int id, String name, int mark1, int mark2, int mark3) {
        this.id = id;
        this.name = name;
        marks[0] = mark1;
        marks[1] = mark2;
        marks[2] = mark3;
        for (int i=0;i<numMarks;i++) {
        	totalMarks += this.marks[i];
        }
    	totalStudents++;
    }
   
    // other methods
    public double averageMark() {
        return (double)(marks[0] + marks[1] + marks[2])/numMarks;
    }
   
    // facilitators
    public String toString() {
        return "Name: "+name+", ID: "+id+" Marks: "+marks[0]+", "+marks[1]+", "+marks[2];
    }
    public boolean equals(Student s) {
        return (id==s.id) && name.equals(s.name) &&
                marks.equals(s.marks);
    }
    
}