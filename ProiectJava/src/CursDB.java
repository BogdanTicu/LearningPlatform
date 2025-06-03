import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CursDB {

    public static void insert(Curs c) {
        String sql = "INSERT INTO curs(titlu, descriere, nota, profesor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getTitlu());
            stmt.setString(2, c.getDescriere());
            stmt.setString(3, c.getNota());
            stmt.setInt(4, c.getProfesor().getId());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                   // c.id = keys.getInt(1);
                    c.setId(keys.getInt(1));
                }
            }

            System.out.println("Curs adăugat cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la inserare curs: " + e.getMessage());
        }
    }

    public static void inscrieStudent(Student s, Curs c) {
        String sql = "INSERT INTO student_curs(student_id, curs_id) VALUES (?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getId());
            stmt.setInt(2, c.getId());
            stmt.executeUpdate();

            System.out.println("Student înscris la curs cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la înscriere student: " + e.getMessage());
        }
    }
    public static void deleteByTitle(String title) {
        String sql = "DELETE FROM curs WHERE titlu = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cursul a fost șters cu succes.");
            } else {
                System.out.println("Cursul nu a fost găsit.");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea cursului: " + e.getMessage());
        }
    }


    public static Vector<Curs> getCursVector() {
        Vector<Curs> lista = new Vector<>();
        String sql = "SELECT * FROM curs";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            System.err.println("Eroare la citirea cursurilor: " + e.getMessage());
        }

        return lista;
    }

    public static Vector<Curs> getCursuriByProfesor(Profesor p) {
        Vector<Curs> lista = new Vector<>();
        String sql = "SELECT * FROM curs WHERE profesor_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curs c = new Curs(
                        rs.getInt("id"),
                        rs.getString("titlu"),
                        rs.getString("descriere"),
                        rs.getString("nota"),
                        p
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cursurilor: " + e.getMessage());
        }

        return lista;
    }
    public static Curs getById(int id) {
        String sql = "SELECT * FROM curs WHERE id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profesor profesor = ProfesorDB.getById(rs.getInt("profesor_id"));
                return new Curs(
                        rs.getString("id"),
                        rs.getString("titlu"),
                        rs.getString("descriere"),
                        profesor
                );
            }

        } catch (SQLException e) {
            System.err.println("Eroare la găsirea cursului: " + e.getMessage());
        }

        return null;
    }
}
