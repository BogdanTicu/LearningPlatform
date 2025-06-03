import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class IntrebareDB {

    public static void insert(Intrebare i, Test t) {
        String sql = "INSERT INTO intrebare(cerinta, raspuns, test_id) VALUES (?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, i.getCerinta());
            stmt.setString(2, i.getRaspuns());
            stmt.setInt(3, t.getId());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    i.setId(keys.getInt(1));
                }
            }

            System.out.println("Întrebare adăugată cu succes.");
        } catch (Exception e) {
            System.err.println("Eroare la inserare întrebare: " + e.getMessage());
        }
    }

    public static Vector<Intrebare> getIntrebariByTestId(int testId) {
        Vector<Intrebare> intrebari = new Vector<>();
        String sql = "SELECT * FROM intrebare WHERE test_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, testId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Intrebare i = new Intrebare(
                        rs.getInt("id"),
                        rs.getString("cerinta"),
                        rs.getString("raspuns")
                );
                intrebari.add(i);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la încărcarea întrebărilor: " + e.getMessage());
        }

        return intrebari;
    }
}
