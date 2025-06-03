import java.sql.*;

public class AdminDB {

    public static void insert(Admin a) {
        UtilizatorDB.insert(a); // doar în `utilizator`, nu există tabel separat
        System.out.println("Admin adăugat cu succes.");
    }

    public static Admin getByEmailAndPassword(String email, String password) {

        String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Utilizator u = UtilizatorDB.login(email, password);
            if (u != null && "ADMIN".equals(u.getTip())) {
                return new Admin(u.getName(), u.getEmail(), u.getPassword());
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Eroare la ștergere admin: " + e.getMessage());
        }
        return null;
    }

    public static void deleteByEmail(String email) {
        String sql = "DELETE FROM utilizator WHERE email = ? AND tip = 'ADMIN'";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Admin șters cu succes.");
            } else {
                System.out.println("Niciun admin cu acest email.");
            }

        } catch (SQLException e) {
            System.err.println("Eroare la ștergere admin: " + e.getMessage());
        }
    }
}
