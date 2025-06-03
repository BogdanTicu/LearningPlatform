import java.lang.reflect.Field;
import java.sql.*;
import java.util.Vector;

public class RezolvareTestDB {
    private static boolean exists(int studentId, int testId) {
        String sql = "SELECT 1 FROM rezolvare_test WHERE student_id = ? AND test_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, testId);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // există deja

        } catch (SQLException e) {
            System.err.println("Eroare la verificarea existenței testului: " + e.getMessage());
        }

        return false;
    }


    public static void insert(RezolvareTest rezolvare) {
        // Verificăm dacă testul a fost deja rezolvat
        if (exists(rezolvare.getStudent().getId(), rezolvare.getTestOriginal().getId())) {
            System.out.println("❗ Acest test a fost deja rezolvat de student.");
            return;
        }

        String sql = "INSERT INTO rezolvare_test(student_id, test_id, scor) VALUES (?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rezolvare.getStudent().getId());
            stmt.setInt(2, rezolvare.getTestOriginal().getId());
            stmt.setInt(3, rezolvare.getScor());

            stmt.executeUpdate();

            // Salvăm ID-ul generat
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    Field field = rezolvare.getClass().getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(rezolvare, keys.getInt(1));
                }
            }

            // Inserăm întrebările doar dacă nu există deja
            for (RezolvareIntrebare raspuns : rezolvare.getRaspunsuriStudent()) {
                RezolvareIntrebareDB.insert(raspuns, rezolvare.getId());
            }

            System.out.println("Rezolvare test salvată cu succes.");
        } catch (Exception e) {
            System.err.println("Eroare la salvare rezolvare test: " + e.getMessage());
        }
    }



    public static Vector<RezolvareTest> getTesteRezolvateDeStudenti(Profesor p) {
        Vector<RezolvareTest> lista = new Vector<>();

        String sql = "SELECT DISTINCT rt.id, rt.student_id, rt.test_id, rt.scor " +
                "FROM rezolvare_test rt " +
                "JOIN test t ON rt.test_id = t.id " +
                "JOIN curs c ON t.curs_id = c.id " +
                "WHERE c.profesor_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rezId = rs.getInt("id");
                int studentId = rs.getInt("student_id");
                int testId = rs.getInt("test_id");
                int scor = rs.getInt("scor");

                Student student = StudentDB.getStudentById(studentId);
                Test test = TestDB.getById(testId);  // încarcă testul și întrebările
                RezolvareTest rez = new RezolvareTest(student, test);
                rez.setId(rezId);
                rez.setScor(scor);

                // Adaugă întrebările rezolvate:
                Vector<RezolvareIntrebare> raspunsuri = RezolvareIntrebareDB.getByRezolvareTestId(rezId);
                for (RezolvareIntrebare ri : raspunsuri) {
                    rez.adaugaIntrebare(ri);
                }

                lista.add(rez);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la încărcare teste rezolvate: " + e.getMessage());
        }

        return lista;
    }

    public static Vector<RezolvareTest> getByStudentId(int studentId) {
        Vector<RezolvareTest> lista = new Vector<>();

        String sql = "SELECT id, test_id, scor FROM rezolvare_test WHERE student_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rezId = rs.getInt("id");
                int testId = rs.getInt("test_id");
                int scor = rs.getInt("scor");

                Test test = TestDB.getById(testId);
                Student student = StudentDB.getStudentById(studentId);

                RezolvareTest rez = new RezolvareTest(student, test);
                rez.setId(rezId);
                rez.setScor(scor);

                lista.add(rez);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la extragerea testelor studentului: " + e.getMessage());
        }

        return lista;
    }

    public static void updateScor(int rezolvareId, int scorNou) {
        String sql = "UPDATE rezolvare_test SET scor = ? WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, scorNou);
            stmt.setInt(2, rezolvareId);
            stmt.executeUpdate();

            System.out.println("Scorul a fost actualizat cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea scorului: " + e.getMessage());
        }
    }

}
