import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StudentDB{

    public static void insert(Student s) {
        UtilizatorDB.insert(s); // inserează în `utilizator` și setează ID-ul
        String sql = "INSERT INTO student(id) VALUES (?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getId());
            stmt.executeUpdate();

            System.out.println("Student adăugat cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la inserare student: " + e.getMessage());
        }
    }

    public static Student getByEmailAndPassword(String email, String password) {
        Utilizator u = UtilizatorDB.login(email, password);
        if (u != null && "STUDENT".equals(u.getTip())) {
            return new Student(u.getId(), u.getName(), u.getEmail(), u.getPassword());
        }
        return null;
    }

    public static ArrayList<Student> getAllStudents() {
        List<Student> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.email, u.password FROM utilizator u INNER JOIN student p ON u.id = p.id";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
               Student s = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                       rs.getString("password"));
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cursurilor: " + e.getMessage());
        }

        return (ArrayList<Student>) lista;
    }
    public static void inscrieLaCurs(int studentId, int cursId) {
        String sql = "INSERT IGNORE INTO student_curs(student_id, curs_id) VALUES (?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, cursId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("❌ Eroare la înscriere curs: " + e.getMessage());
        }
    }
    public static Vector<Curs> getCursuriPentruStudent(int studentId) {
        Vector<Curs> lista = new Vector<>();
        String sql = "SELECT c.id, c.titlu, c.descriere, c.nota, c.profesor_id FROM curs c " +
                "JOIN student_curs sc ON c.id = sc.curs_id WHERE sc.student_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Profesor prof = ProfesorDB.getById(rs.getInt("profesor_id"));
                Curs c = new Curs(
                        rs.getInt("id"),
                        rs.getString("titlu"),
                        rs.getString("descriere"),
                        rs.getString("nota"),
                        prof
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la încărcare cursuri student: " + e.getMessage());
        }

        return lista;
    }
    public static Student getStudentById(int id) {
        String sql = "SELECT u.id, u.name, u.email, u.password FROM utilizator u INNER JOIN student s ON u.id = s.id WHERE u.id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Eroare la găsirea studentului: " + e.getMessage());
        }
        return null;
    }
    public static void deleteById(int id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            UtilizatorDB.deleteById(id); // șterge și din utilizator
            System.out.println("Student șters cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea studentului: " + e.getMessage());
        }
    }
    public static void updateStudent(Student s) {
        UtilizatorDB.update(s);
    }




}
