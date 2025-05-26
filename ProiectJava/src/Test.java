import java.util.Objects;
import java.util.Optional;
import java.util.Vector;

public class Test{
    private Vector<Intrebare> intrebari;
    private Curs curs;
    Test(){
        intrebari = new Vector<>();
        this.curs = null;
    }
    Test (Curs c){
        intrebari = new Vector<>();
        this.curs = c;
    }



    public void addIntrebare(Intrebare i){
        intrebari.add(i);
    }

    public void afisareIntrebari()
    {
        for(Intrebare i : intrebari)
            System.out.println(i);
    }

    public Vector<Intrebare> getIntrebari() {
        return intrebari;
    }
    public void setIntrebari(Vector<Intrebare> intrebari) {
        this.intrebari = intrebari;
    }

    public Curs getCurs()
    {
        return curs;
    }
    public void setCurs(Curs c)
    {
        this.curs = c;
        c.teste.add(this);
    }

    public void removeIntrebari(Intrebare i){intrebari.remove(i);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test t = (Test) o;
        return this.curs.equals(t.curs) && this.intrebari.equals(t.intrebari);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curs, intrebari);
    }

    @Override
    public String toString(){
        return "Test la "+ curs.titlu +"\nCerinte:\n"+ intrebari;
    }
}
