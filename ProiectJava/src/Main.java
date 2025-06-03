import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = MyJDBC.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexiune reușită!");
            }
        } catch (Exception e) {
            System.out.println("❌ Eroare conexiune: " + e.getMessage());
        }
        Service s = new Service();
       // s.init_studenti();
       // s.init_profesori();
        s.meniu();

    }
}
