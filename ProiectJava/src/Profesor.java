import java.util.Vector;

public class Profesor extends Utilizator {
    protected Vector<Curs> listaCursuriPredate;
    private Vector<RezolvareTest> testeRezolvateDeStudenti;

    public Profesor(String name, String email, String password) {
        super(name, email, password, "PROFESOR");
        this.listaCursuriPredate = new Vector<>();
        this.testeRezolvateDeStudenti = new Vector<>();
    }

    public Profesor(int id, String name, String email, String password) {
        super(id, name, email, password, "PROFESOR");
        this.listaCursuriPredate = new Vector<>();
        this.testeRezolvateDeStudenti = new Vector<>();
    }

    public void afisare_cursuri() {
        if (listaCursuriPredate.isEmpty()) {
            System.out.println("Nu ai niciun curs.");
        } else {
            for (Curs c : listaCursuriPredate) {
                System.out.println("Curs: " + c.getTitlu() + " | Descriere: " + c.getDescriere());
            }
        }
    }
    public void adaugare_curs(Curs c) {
        listaCursuriPredate.add(c);
        System.out.println("Curs adăugat cu succes.");
        //CursDB.insert(c);
    }
    public void sterge_curs(String titlu) {
        boolean removed = listaCursuriPredate.removeIf(c -> c.getTitlu().equals(titlu));
        if (removed) {
            System.out.println("Curs șters cu succes.");
        } else {
            System.out.println("Cursul nu a fost găsit.");
        }


    }
    public void afisareRezolvari() {
        testeRezolvateDeStudenti = RezolvareTestDB.getTesteRezolvateDeStudenti(this);
        if (testeRezolvateDeStudenti.isEmpty()) {
            System.out.println("Nu există teste rezolvate.");
            return;
        }

        for (RezolvareTest rez : testeRezolvateDeStudenti) {
            System.out.println("Student: " + rez.getStudent().getName());
            System.out.println("Curs: " + rez.getTestOriginal().getCurs().getTitlu());
            System.out.println("Scor: " + rez.getScor());
            System.out.println("-------------");
        }
    }
    public Vector<RezolvareTest> getTesteRezolvateDeStudenti() {

        return testeRezolvateDeStudenti;
    }
    @Override
    public String toString() {
        return "Profesor{" +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
