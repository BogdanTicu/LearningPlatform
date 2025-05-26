import java.util.List;
import java.util.Vector;

public class RezolvareTest {
    private Student student;
    private Test testOriginal;
    private Vector<RezolvareIntrebare> raspunsuriStudent;
    private int scor;

    public RezolvareTest(Student student, Test test) {
        this.student = student;
        this.testOriginal = test;
        this.scor = 0; // default
    }
    public RezolvareTest(Student student, Test testOriginal, Vector<RezolvareIntrebare> raspunsuriStudent) {
        this.student = student;
        this.testOriginal = testOriginal;
        this.raspunsuriStudent = raspunsuriStudent;
        this.scor = -1;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public int getScor() {
        return scor;
    }

    public Student getStudent() {
        return student;
    }

    public Test getTestOriginal() {
        return testOriginal;
    }

    public Vector<RezolvareIntrebare> getRaspunsuriStudent() {
        return raspunsuriStudent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Test rezolvat de " + student.name + ":\n");
        for (RezolvareIntrebare r : raspunsuriStudent) {
            sb.append(r).append("\n");
        }
        sb.append("Scor: ").append(scor == -1 ? "NENOTAT" : scor).append("\n");
        return sb.toString();
    }
}

