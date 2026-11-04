public class Main {
	public static void main(String[] args) {
		Student s1 = new Student(1234, "Yahya", 2001);
		System.out.println(s1); // toString();
		
		// Final Reference: can't point to another object.
		// can i change its inner data (variables) normally.
		final Student s2 = new Student(2345, "Ali", 2004);
		// s2 = s1; // Error
		// s2 = new Student(.......) // Error.
		System.out.println(s2);


		// The sub-class still can inherit the final function/method
		// and use it.
		GraduatedStudent g1 = new GraduatedStudent(1234, "Mostafa", 1999);
		System.out.println(g1);
	}
}

/* **The output:

❯ javac Student.java Main.java
❯ java Main
Student{STUDENT_ID=1234, name='Yahya', yearOfBirth=2001, DEGREE_OF_SUCCESS= 253.0}
Student{STUDENT_ID=2345, name='Ali', yearOfBirth=2004, DEGREE_OF_SUCCESS= 253.0}
Student{STUDENT_ID=1234, name='Mostafa', yearOfBirth=1999, DEGREE_OF_SUCCESS= 253.0}
 */
