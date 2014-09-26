package Student;

public class StudentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // TODO Auto-generated method stub
        Student s1 = new Student(1,"A");
        Student s2 = new Student(2,"A",1,2,3);
        int[] a = {1,2,3};
        Student s3 = new Student(2,"B",a);
       
        System.out.println(s3 + " Average Mark: "+s3.averageMark());
        System.out.println(s1 + (s1.equals(s2) ? " equals " : " does not equal ") + s2);
        
        System.out.println("Average subject score: "+ Student.getAverageMark());
        System.out.println("Total marks: " + Student.getTotalMarks());
        System.out.println("Total students: " + Student.getTotalStudents());
    }

}
