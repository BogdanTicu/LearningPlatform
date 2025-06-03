public class Intrebare {
    private int id;
    protected String cerinta;
    protected String raspuns;

    public Intrebare(String cerinta) {
        this.cerinta = cerinta;
        this.raspuns = null;
    }

    public Intrebare(String cerinta, String raspuns) {
        this.cerinta = cerinta;
        this.raspuns = raspuns;
    }

    public Intrebare(int id, String cerinta, String raspuns) {
        this(cerinta, raspuns);
        this.id = id;
    }

    public String getCerinta() {
        return cerinta;
    }

    public String getRaspuns() {
        return raspuns;
    }

    public int getId() {
        return id;
    }
    public void setCerinta(String cerinta) {
        this.cerinta = cerinta;
    }
    public void setRaspuns(String raspuns) {
        this.raspuns = raspuns;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return cerinta;
    }
}
