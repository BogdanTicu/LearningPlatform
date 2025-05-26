public class Admin extends Utilizator {
    public Admin(String name, String email, String password) {
        super(name, email, password);
    }

    public void adaugaProfesor(Profesor p) {
        Service.profesorSet.add(p);
    }

    public void stergeProfesor(String email) {
        Service.profesorSet.removeIf(p -> p.getEmail().equals(email));
    }

    public void adaugaStudent(Student s) {
        Service.studentVector.add(s);
    }

    public void stergeStudent(String email) {
        Service.studentVector.removeIf(s -> s.getEmail().equals(email));
    }

    public void afiseazaProfesori() {
        for (Profesor p : Service.profesorSet) {
            System.out.println(p);
        }
    }

    public void afiseazaStudenti() {
        for (Student s : Service.studentVector) {
            System.out.println(s);
        }
    }
}
