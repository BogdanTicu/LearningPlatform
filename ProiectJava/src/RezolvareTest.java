import java.util.Vector;

public class RezolvareTest {
    private int id;
    private Student student;
    private Test testOriginal;
    private Vector<RezolvareIntrebare> raspunsuriStudent;
    private int scor;

    public RezolvareTest(Student student, Test test) {
        this.student = student;
        this.testOriginal = test;
        this.raspunsuriStudent = new Vector<>();
        this.scor = 0;
    }

    public RezolvareTest(Student student, Test testOriginal, Vector<RezolvareIntrebare> raspunsuriStudent) {
        this(student, testOriginal);
        this.raspunsuriStudent = raspunsuriStudent;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public int getScor() {
        return scor;
    }

    public Test getTestOriginal() {
        return testOriginal;
    }

    public Vector<RezolvareIntrebare> getRaspunsuriStudent() {
        return raspunsuriStudent;
    }

    public Student getStudent() {
        return student;
    }

    public int getId() {
        return id;
    }

    public void adaugaIntrebare(RezolvareIntrebare intrebare) {
        raspunsuriStudent.add(intrebare);
    }
    public void setId(int id) {
        this.id = id;
    }

}
