import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Profesor extends Utilizator{
    protected Vector<Curs> listaCursuriPredate;
    private Vector<RezolvareTest> testeRezolvateDeStudenti;

    Profesor(String name, String email, String password) {
        super(name, email, password);
        this.listaCursuriPredate = new Vector<Curs>();
        this.testeRezolvateDeStudenti = new Vector<RezolvareTest>();
    }

    public void afisare_cursuri() {
        for(Curs curs: listaCursuriPredate){
            System.out.println(curs);
            for(Test t :curs.teste)
                System.out.println(t);
        }
    }
    public void adaugare_curs(Curs c) {
        this.listaCursuriPredate.add(c);
    }


    public void sterge_curs(String titlu){
        // int id = c.getId();
        for(int i=0; i<listaCursuriPredate.size(); i++){
            if(Objects.equals(listaCursuriPredate.get(i).titlu, titlu)){
                listaCursuriPredate.remove(i);
            }
        }
    }

    public Vector<RezolvareTest>getTesteRezolvateDeStudenti()
    {
        return testeRezolvateDeStudenti;
    }
    public void adaugaRezolvareTest(RezolvareTest rezolvare) {
        this.testeRezolvateDeStudenti.add(rezolvare);
    }

    public void afiseazaRezolvari() {
        System.out.println("Rezolvări primite:");
        for (RezolvareTest rez : testeRezolvateDeStudenti) {
            System.out.println(rez);
        }
    }


    public void adauga_test(Curs c, Test t)
    {
        t.setCurs(c);

    }
    public void NotareTest(RezolvareTest rezolvare)
    {
        Scanner scanner = new Scanner(System.in);
        int totalPuncte = 0;

        System.out.println("Notare test de la student: " + rezolvare.getStudent().name);

        for (RezolvareIntrebare r : rezolvare.getRaspunsuriStudent()) {
            System.out.println(r.getIntrebare());
            System.out.println("Răspuns student: " + r.getRaspunsStudent());
            System.out.print("Acordă punctaj (int): ");
            int punctaj = Integer.parseInt(scanner.nextLine());
            totalPuncte += punctaj;
        }

        rezolvare.setScor(totalPuncte);
        System.out.println("Scor total acordat: " + totalPuncte);
    }
}
