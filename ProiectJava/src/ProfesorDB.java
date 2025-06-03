import java.sql.*;
import java.util.TreeSet;

public class ProfesorDB {

    public static void insert(Profesor p) {
        UtilizatorDB.insert(p); // inserează în `utilizator`
        String sql = "INSERT INTO profesor(id) VALUES (?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId());
            stmt.executeUpdate();

            System.out.println("Profesor adăugat cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la inserare profesor: " + e.getMessage());
        }
    }

    public static Profesor getByEmailAndPassword(String email, String password) {
        Utilizator u = UtilizatorDB.login(email, password);
        if (u != null && "PROFESOR".equals(u.getTip())) {
            return new Profesor(u.getId(), u.getName(), u.getEmail(), u.getPassword());
        }
        return null;
    }

    public static void deleteById(int id) {
        String sql = "DELETE FROM profesor WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            UtilizatorDB.deleteById(id); // șterge și din utilizator
            System.out.println("Profesor șters cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea profesorului: " + e.getMessage());
        }
    }

    public static void deleteByEmail(String email) {
        String sql = "DELETE FROM profesor WHERE id = (SELECT id FROM utilizator WHERE email = ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Profesor șters cu succes.");
            } else {
                System.out.println("Nu s-a găsit profesorul cu emailul specificat.");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea profesorului: " + e.getMessage());
        }
    }

    public static TreeSet<Profesor> getAllProfesors() {
        TreeSet<Profesor> profesors = new TreeSet<>((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getEmail()));
        String sql = "SELECT u.id, u.name, u.email, u.password FROM utilizator u INNER JOIN profesor p ON u.id = p.id";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                profesors.add(new Profesor(id, name, email, password));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la preluarea profesorilor: " + e.getMessage());
        }

        return profesors;
    }
    public static Profesor getById(int id) {
        String sql = "SELECT * FROM utilizator WHERE id = ? AND upper(tip) = 'PROFESOR'";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Profesor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Eroare la găsirea profesorului: " + e.getMessage());
        }

        return null;
    }
    public static void updateProfesor(Profesor p) {
        UtilizatorDB.update(p);
    }

}
