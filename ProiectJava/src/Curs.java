import java.util.Vector;

public class Curs {
    private int id;
    protected String titlu;
    protected String descriere;
    private String nota;
    private Profesor profesor;
    protected Vector<Test>teste;
    static int counterCurs = 0;

    Curs(String titlu) {
        this.titlu = titlu;
        this.descriere = "";
        this.nota = "";
        this.teste=new Vector<>();
        this.profesor=null;
        counterCurs++;
    }
    Curs(String titlu, String descriere,Profesor profesor) {
        this.id = counterCurs;
        counterCurs++;
        this.titlu = titlu;
        this.descriere = descriere;
        this.nota = "";
        this.teste=new Vector<>();
        this.profesor=profesor;
        //this.profesor = profesor;
    }
    @Override
    public String toString() {
        return "Curs{" +
                "id=" + id +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                '}';
    }

    public Profesor getProfesor() {
        return profesor;
    }
    public int getId() {
        return id;
    }
    public void setNota(String nota) {
        this.nota = nota;
    }
    public String getNota() {
        return nota;
    }
}
