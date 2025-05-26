import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Student extends Utilizator{
    private Vector<Curs>listaCursuriInscrise;
    private Vector<Test> testeRezolvate;

    Student(String name, String email, String password) {
        super(name, email, password);
        this.listaCursuriInscrise = new Vector<Curs>();
        this.testeRezolvate = new Vector<Test>();
    }

    public void afisare_cursuri_student() {
        for(Curs curs: listaCursuriInscrise){
            System.out.println(curs);
        }
    }

    public void afisare_teste_student() {
        for (Curs curs : listaCursuriInscrise) {
            for (Test t : curs.teste) {
                if (!testeRezolvate.contains(t)) {
                    System.out.println(t);
                }
            }
        }
    }

    public void Adauga_curs(Curs c)
    {
        this.listaCursuriInscrise.add(c);
    }

    public void rezolva_test(Test t) {
        if (testeRezolvate.contains(t)) {
            System.out.println("Ai rezolvat deja acest test.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Vector<RezolvareIntrebare> raspunsuri = new Vector<>();

        System.out.println("Rezolvă testul: " + t.getCurs().titlu);

        for (Intrebare intrebare : t.getIntrebari()) {
            System.out.println(intrebare);
            System.out.print("Scrie răspunsul tău: ");
            String raspuns = scanner.nextLine();
            raspunsuri.add(new RezolvareIntrebare(intrebare, raspuns));
        }
        RezolvareTest rezolvare = new RezolvareTest(this, t, raspunsuri);
        t.getCurs().getProfesor().adaugaRezolvareTest(rezolvare);
        testeRezolvate.add(t);

        System.out.println("Test trimis către profesor.");
    }


    @Override
    public String toString(){
        return super.toString() + listaCursuriInscrise;
    }
}
