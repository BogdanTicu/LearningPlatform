import java.sql.*;
import java.util.Vector;

public class TestDB {

    public static void insert(Test t) {
        String sql = "INSERT INTO test(curs_id) VALUES (?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, t.getCurs().getId());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    t.setId(keys.getInt(1));
                }
            }

            System.out.println("Test adăugat cu succes.");
        } catch (Exception e) {
            System.err.println("Eroare la inserare test: " + e.getMessage());
        }
    }


    public static Test getById(int testId) {
        String sql = "SELECT * FROM test WHERE id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, testId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Test t = new Test(
                        rs.getInt("id"),
                        CursDB.getById(rs.getInt("curs_id"))
                );
                t.getIntrebari().addAll(IntrebareDB.getIntrebariByTestId(testId));
                return t;
            }
        } catch (SQLException e) {
            System.err.println("Eroare la găsirea testului: " + e.getMessage());
        }
        return null;
    }
    public static Vector<Test> getTesteByCursId(int cursId, int studentId) {
        Vector<Test> teste = new Vector<>();
        String sql = "SELECT * FROM test WHERE curs_id = ? AND id NOT IN " +
                "(SELECT test_id FROM rezolvare_test WHERE student_id = ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursId);
            stmt.setInt(2, studentId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int testId = rs.getInt("id");
                Test t = new Test(testId, CursDB.getById(cursId));
                t.getIntrebari().addAll(IntrebareDB.getIntrebariByTestId(testId));
                teste.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la încărcare teste: " + e.getMessage());
        }

        return teste;
    }


}
