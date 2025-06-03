import java.sql.*;
import java.util.Vector;

public class RezolvareIntrebareDB {

    public static void insert(RezolvareIntrebare ri, int rezolvareTestId) {
        String sql = "INSERT INTO rezolvare_intrebare(intrebare_id, rezolvare_test_id, raspuns_student) VALUES (?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ri.getIntrebare().getId());
            stmt.setInt(2, rezolvareTestId);
            stmt.setString(3, ri.getRaspunsStudent());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea răspunsului: " + e.getMessage());
        }
    }
    public static Vector<RezolvareIntrebare> getByRezolvareTestId(int rezolvareTestId) {
        Vector<RezolvareIntrebare> lista = new Vector<>();

        String sql = "SELECT ri.raspuns_student, i.id AS intrebare_id, i.cerinta, i.raspuns_corect " +
                "FROM rezolvare_intrebare ri " +
                "JOIN intrebare i ON ri.intrebare_id = i.id " +
                "WHERE ri.rezolvare_test_id = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rezolvareTestId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String enunt = rs.getString("cerinta");
                String raspunsCorect = rs.getString("raspuns_corect");
                String raspunsStudent = rs.getString("raspuns_student");

                Intrebare intrebare = new Intrebare(enunt, raspunsCorect);
                RezolvareIntrebare rezIntrebare = new RezolvareIntrebare(intrebare, raspunsStudent);
                lista.add(rezIntrebare);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la încărcarea răspunsurilor: " + e.getMessage());
        }

        return lista;
    }
}
