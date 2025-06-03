import java.util.Objects;
import java.util.Optional;
import java.util.Vector;

public class Test {
    private int id;
    private Vector<Intrebare> intrebari;
    private Curs curs;

    public Test() {
        intrebari = new Vector<>();
        this.curs = null;
    }

    public Test(Curs curs) {
        this.curs = curs;
        intrebari = new Vector<>();
    }

    public Test(int id, Curs curs) {
        this(curs);
        this.id = id;
    }

    public void adaugaIntrebare(Intrebare intrebare) {
        intrebari.add(intrebare);
    }

    public Vector<Intrebare> getIntrebari() {
        return intrebari;
    }

    public Curs getCurs() {
        return curs;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
