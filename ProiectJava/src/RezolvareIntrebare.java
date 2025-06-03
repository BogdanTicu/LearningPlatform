public class RezolvareIntrebare {
    private int id;
    private Intrebare intrebareOriginala;
    private String raspunsStudent;

    public RezolvareIntrebare(Intrebare intrebareOriginala, String raspunsStudent) {
        this.intrebareOriginala = intrebareOriginala;
        this.raspunsStudent = raspunsStudent;
    }

    public Intrebare getIntrebare() {
        return intrebareOriginala;
    }

    public String getRaspunsStudent() {
        return raspunsStudent;
    }

    public int getId() {
        return id;
    }
}
