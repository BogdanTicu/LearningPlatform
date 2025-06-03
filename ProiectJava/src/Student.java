import java.util.Scanner;
import java.util.Vector;

public class Student extends Utilizator {
    private Vector<Curs> listaCursuriInscrise;
    private Vector<Test> testeRezolvate;

    public Student(int id, String name, String email, String password) {
        super(id, name, email, password, "STUDENT");
        this.listaCursuriInscrise = new Vector<>();
        this.testeRezolvate = new Vector<>();
    }

    public Student(String name, String email, String password) {
        super(name, email, password, "STUDENT");
        this.listaCursuriInscrise = new Vector<>();
        this.testeRezolvate = new Vector<>();
    }


    public Vector<Curs> getListaCursuriInscrise() {
        return listaCursuriInscrise;
    }
    public void afisare_cursuri_student() {
        if (listaCursuriInscrise.isEmpty()) {
            System.out.println("Nu ești înscris la niciun curs.");
        } else {
            for (Curs c : listaCursuriInscrise) {
                System.out.println("Curs: " + c.getTitlu() + " | Descriere: " + c.getDescriere() + " |Id: " + c.getId());
            }
        }
    }
    public void Adauga_curs(Curs c) {
        if (!listaCursuriInscrise.contains(c)) {
            listaCursuriInscrise.add(c);
            StudentDB.inscrieLaCurs(this.id,c.getId());
            System.out.println("Curs adăugat.");
        } else {
            System.out.println("Ești deja înscris la acest curs.");
        }
    }
    public void afisare_teste_student() {
        listaCursuriInscrise = StudentDB.getCursuriPentruStudent(this.id);

        if (listaCursuriInscrise.isEmpty()) {
            System.out.println("Nu esti inscris la niciun curs.");
            return;
        }

        for (Curs c : listaCursuriInscrise) {
            System.out.println("Curs: " + c.getTitlu());
            c.teste = TestDB.getTesteByCursId(c.getId(),this.getId());
            if(c.teste.isEmpty()) {
                System.out.println("Nu exista niciun test la acest curs");
                continue;
            }
            for (int i = 0; i < c.teste.size(); i++) {
                System.out.println(" - Test " + i + " cu " + c.teste.get(i).getIntrebari().size() + " intrebari");
            }
        }
    }

    public void rezolva_test(Test test) {
        Scanner sc = new Scanner(System.in);
        RezolvareTest rezolvare = new RezolvareTest(this, test);

        for (Intrebare intrebare : test.getIntrebari()) {
            System.out.println("Intrebare: " + intrebare.getText());
            System.out.print("Răspunsul tau: ");
            String raspuns = sc.nextLine();
            RezolvareIntrebare rezIntrebare = new RezolvareIntrebare(intrebare, raspuns);
            rezolvare.adaugaIntrebare(rezIntrebare);
            RezolvareIntrebareDB.insert(rezIntrebare,test.getId());
        }

        RezolvareTestDB.insert(rezolvare);
        System.out.println("Testul a fost trimis profesorului pentru notare.");
    }

    public void incarcaCursuriDinBD() {
        this.listaCursuriInscrise = new Vector<>(StudentDB.getCursuriPentruStudent(this.id));
    }
    public void incarcaTestePentruCursuri() {
        for (Curs c : listaCursuriInscrise) {
            c.teste = TestDB.getTesteByCursId(c.getId(),this.getId());
        }
    }

    public void afisareTesteRezolvate() {
        Vector<RezolvareTest> testeRezolvate = RezolvareTestDB.getByStudentId(this.getId());

        if (testeRezolvate.isEmpty()) {
            System.out.println("Nu ai niciun test rezolvat.");
            return;
        }

        System.out.println("Teste rezolvate:");
        for (RezolvareTest r : testeRezolvate) {
            System.out.println("Test la cursul: " + r.getTestOriginal().getCurs().getTitlu());
            System.out.println("Scor obtinut: " + r.getScor());
        }
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", listaCursuriInscrise=" + listaCursuriInscrise +
                '}';
    }
}
