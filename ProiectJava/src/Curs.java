import java.util.Vector;

public class Curs {
    private int id;
    protected String titlu;
    protected String descriere;
    private String nota;
    private Profesor profesor;
    protected Vector<Test> teste;


    public Curs(String titlu, String descriere, String nota, Profesor profesor) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.nota = nota;
        this.profesor = profesor;
        this.teste = new Vector<>();
    }

    public Curs(int id, String titlu, String descriere, String nota, Profesor profesor) {
        this(titlu, descriere, nota, profesor);
        this.id = id;
    }

    public int getId() { return id; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public String getNota() { return nota; }
    public Profesor getProfesor() { return profesor; }

    public void setId(int anInt) {
        id = anInt;
    }
}
