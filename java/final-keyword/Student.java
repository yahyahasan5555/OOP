public class Student {
    private final int STUDENT_ID;
    private static final float DEGREE_OF_SUCCESS;
    String name;
    int yearOfBirth;

    // Blank final variable assigned ONLY in a static block.
    static {
        DEGREE_OF_SUCCESS = 253;
    }

    public Student(final int STUDENT_ID, String name, int yearOfBirth) {
        // STUDENT_ID = 1111; Error! can't update a FINAL VARIABLE, assign it as it.

        // can assign a blank final variable in either blocks or constructors only.
        this.STUDENT_ID = STUDENT_ID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    final public String toString() {
        return "Student{" +
                "STUDENT_ID=" + STUDENT_ID +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", DEGREE_OF_SUCCESS= " + DEGREE_OF_SUCCESS +
                '}';
    }
}

class GraduatedStudent extends Student{
//     Error: can't Override a final Method.
//     @Override
//     public String toString() {
//          return super.toString() + " Anything";
//     }
    public GraduatedStudent(final int STUDENT_ID, String name, int yearOfBirth) {
        super(STUDENT_ID, name, yearOfBirth);
    }
}
