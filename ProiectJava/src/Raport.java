import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Raport {

   // private static final int PRAG_TRECERE = 5;

    public static void genereazaRaportStudenti() {
        ArrayList<Student> studenti = StudentDB.getAllStudents();

        for (Student student : studenti) {
            Vector<RezolvareTest> rezolvari = RezolvareTestDB.getByStudentId(student.getId());

            int total = rezolvari.size();
            int trecute = 0;

            for (RezolvareTest r : rezolvari) {
                if (r.getScor() >= 50) {
                    trecute++;
                }
            }

            System.out.println("Student: " + student.getName() +
                    " | Teste trecute: " + trecute +
                    " / " + total);
        }
    }
}
