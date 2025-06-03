import java.sql.*;

public class UtilizatorDB {

    public static void insert(Utilizator u) {
        String sql = "INSERT INTO utilizator(name, email, password, tip) VALUES (?, ?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getTip());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getInt(1));
                }
            }

            System.out.println("✅ Utilizator adăugat cu succes.");
        } catch (SQLException e) {
            System.err.println("❌ Eroare la inserare utilizator: " + e.getMessage());
        }
    }

    public static void deleteById(int id) {
        String sql = "DELETE FROM utilizator WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea utilizatorului: " + e.getMessage());
        }
    }

    public static Utilizator login(String email, String password) {
        String sql = "SELECT * FROM utilizator WHERE email = ? AND password = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilizator(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("tip")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la login: " + e.getMessage());
        }
        return null;
    }
    public static void update(Utilizator u) {
        String sql = "UPDATE utilizator SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setInt(4, u.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Utilizator actualizat cu succes.");
            } else {
                System.out.println("Nicio actualizare efectuată.");
            }

        } catch (SQLException e) {
            System.err.println("Eroare la actualizare utilizator: " + e.getMessage());
        }
    }


}
