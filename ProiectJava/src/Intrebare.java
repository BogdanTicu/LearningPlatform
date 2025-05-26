public class Intrebare {
    protected String cerinta;
    protected String raspuns;

    Intrebare(String cerinta)
    {
        this.cerinta = cerinta;
        this.raspuns = null;
    }
    Intrebare(String cerinta, String raspuns) {
        this.cerinta = cerinta;
        this.raspuns = raspuns;
    }
    Intrebare(String cerinta, String raspuns,float punctaj) {
        this.cerinta = cerinta;
        this.raspuns = raspuns;
        //this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return "Cerinta: " + this.cerinta ;
    }
}
