public class Utilizator {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected String tip; // "STUDENT", "PROFESOR", "ADMIN"

    public Utilizator(int id, String name, String email, String password, String tip) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tip = tip;
    }

    public Utilizator(String name, String email, String password, String tip) {
        this(-1, name, email, password, tip);
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getTip() { return tip; }

    // Setteri
    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
