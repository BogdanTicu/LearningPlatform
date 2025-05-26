import java.util.Scanner;

public class Utilizator {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    static int counter = 0;

    Utilizator(String name, String email, String password) {
        this.id = counter;
        counter++;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean login(String email, String password)
    {

        if(this.email.equals(email) && this.password.equals(password))
            return true;
        return false;
    }

    public String getEmail()
    {
        return email;
    }

}
