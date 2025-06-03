public class Admin extends Utilizator {

    public Admin(String name, String email, String password) {
        super(name, email, password, "ADMIN"); // adăugăm tipul pentru DB
    }

    public void adaugaProfesor(Profesor p) {
        ProfesorDB.insert(p); // salvează în baza de date
        Service.profesorSet.add(p); // salvează în memorie
    }

    public void stergeProfesor(String email) {
        Service.profesorSet.removeIf(p -> p.getEmail().equals(email));
        // optional: delete din DB (dacă vrei complet)
        // ProfesorDAO.deleteByEmail(email);
        ProfesorDB.deleteByEmail(email);
    }

    public void adaugaStudent(Student s) {
        StudentDB.insert(s); // DB
        Service.studentVector.add(s); // memorie
    }

    public void stergeStudent(String email) {
        Service.studentVector.removeIf(s -> s.getEmail().equals(email));
        // optional: StudentDAO.deleteByEmail(email);
    }

    public void afiseazaProfesori() {
        Service.profesorSet = ProfesorDB.getAllProfesors();
        for (Profesor p : Service.profesorSet) {
            System.out.println(p);
        }
    }

    public void afiseazaStudenti() {
        Service.studentVector = StudentDB.getAllStudents();
        for (Student s : Service.studentVector) {
            System.out.println(s);
        }
    }
}
