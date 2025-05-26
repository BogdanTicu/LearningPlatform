public class RezolvareIntrebare {
    private Intrebare intrebareOriginala;
    private String raspunsStudent;
    //private int punctaj;
    public RezolvareIntrebare(Intrebare intrebareOriginala, String raspunsStudent) {
        this.intrebareOriginala = intrebareOriginala;
        this.raspunsStudent = raspunsStudent;
        //this.punctaj = 0;
    }

    public Intrebare getIntrebare() {
        return intrebareOriginala;
    }

    public String getRaspunsStudent() {
        return raspunsStudent;
    }


    @Override
    public String toString() {
        return intrebareOriginala.toString() + "\nRăspuns student: " + raspunsStudent;
    }
}
