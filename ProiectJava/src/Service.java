import java.util.*;
import java.io.*;

public class Service {
    static TreeSet<Profesor> profesorSet;
    static ArrayList<Student> studentVector;
    static Vector<Curs> cursVector;

    public Service() {
        profesorSet = ProfesorDB.getAllProfesors();
        studentVector = StudentDB.getAllStudents();
        cursVector = CursDB.getCursVector();

    }

    public String CitireString(Scanner sc) {
        StringBuilder descriere = new StringBuilder();
        while (sc.hasNext()) {
            String cuv = sc.next();
            if (cuv.equals("-1"))
                break;
            else
                descriere.append(cuv).append(" ");
        }
        return descriere.toString();
    }



    public void meniu() {
        Admin admin = AdminDB.getByEmailAndPassword("admin@gmail.com", "admin123");
        if (admin == null) {
            admin = new Admin("admin", "admin@gmail.com", "admin123");
            AdminDB.insert(admin);
        }

        System.out.println("1. Login ca admin");
        System.out.println("2. Login ca profesor");
        System.out.println("3. Login ca student");
        System.out.println("4. Deconectare aplicatie");

        Scanner sc = new Scanner(System.in);
        int optiuni = sc.nextInt();
        sc.nextLine();

        if (optiuni == 1) {
            System.out.println("Introdu mail:");
            String email = sc.nextLine();
            System.out.println("Introdu parola:");
            String password = sc.nextLine();
            Admin a = AdminDB.getByEmailAndPassword(email, password);
            if (a != null) {
                meniu_admin(a);
            } else {
                System.out.println("Mail sau parola incorecte!");
                meniu();
            }
        } else if (optiuni == 2) {
            System.out.println("Introdu mail:");
            String email = sc.nextLine();
            System.out.println("Introdu password:");
            String password = sc.nextLine();
            Profesor p = ProfesorDB.getByEmailAndPassword(email, password);
            if (p != null) {
                meniu_profesor(p);
            } else {
                System.out.println("Mail sau parola incorecte!");
                meniu();
            }
        } else if (optiuni == 3) {
            System.out.println("Introdu mail:");
            String email = sc.nextLine();
            System.out.println("Introdu password:");
            String password = sc.nextLine();
            Student s = StudentDB.getByEmailAndPassword(email, password);
            if (s != null) {
                meniu_student(s);
            } else {
                System.out.println("Mail sau parola incorecte!");
                meniu();
            }
        } else if (optiuni == 4) {
            System.out.println("La revedere!");
        } else {
            System.out.println("Optiune invalida");
            meniu();
        }
    }

    public void meniu_admin(Admin admin) {
        Scanner sc = new Scanner(System.in);
        profesorSet = ProfesorDB.getAllProfesors();
        studentVector = StudentDB.getAllStudents();
        while (true) {
            System.out.println("1. Adaugă profesor");
            System.out.println("2. Șterge profesor");
            System.out.println("3. Adaugă student");
            System.out.println("4. Șterge student");
            System.out.println("5. Vezi lista profesori");
            System.out.println("6. Vezi lista studenți");
            System.out.println("7. Actualizare date cont profesor");
            System.out.println("8. Actualizare dat cont student");
            System.out.println("9. Afiseaza raport studenti");
            System.out.println("10. Logout");
            int opt = sc.nextInt();
            sc.nextLine();

            if (opt == 1) {
                System.out.print("Nume: ");
                String nume = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Parola: ");
                String parola = sc.nextLine();

                Profesor pr = new Profesor(nume, email, parola);
                ProfesorDB.insert(pr);
                AuditService.log("Profesor adaugat de admin");
            } else if (opt == 2) {
                System.out.print("Email profesor de șters: ");
                String email = sc.nextLine();

                Profesor p = Service.profesorSet.stream()
                        .filter(prof -> prof.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);

                if (p != null) {
                    ProfesorDB.deleteById(p.getId());
                    profesorSet.remove(p);
                    System.out.println("Profesor șters cu succes.");
                    AuditService.log("Profesor sters de admin");
                } else {
                    System.out.println("Profesorul nu a fost găsit.");
                }
            } else if (opt == 3) {
                System.out.print("Nume: ");
                String nume = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Parola: ");
                String parola = sc.nextLine();
                admin.adaugaStudent(new Student(nume, email, parola));
                AuditService.log("Student adaugat de admin");
            } else if (opt == 4) {
                System.out.print("Email student de șters: ");
                String email = sc.nextLine();

                Student s = studentVector.stream()
                        .filter(st -> st.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);

                if (s != null) {
                    StudentDB.deleteById(s.getId());
                    studentVector.remove(s);
                    System.out.println("Student șters cu succes.");
                    AuditService.log("Student sters de admin");
                } else {
                    System.out.println("Studentul nu a fost găsit.");
                }
            } else if (opt == 5) {
                admin.afiseazaProfesori();
            } else if (opt == 6) {
                admin.afiseazaStudenti();
            }else if(opt == 7) {
                System.out.println("Actualizare date cont profesor:");
                System.out.println("Pt ce prof vrei sa editezi? Introdu id: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Nume nou: ");
                String nume = sc.nextLine();
                System.out.print("Email nou: ");
                String email = sc.nextLine();
                System.out.print("Parolă nouă: ");
                String password  = sc.nextLine();
                Profesor p = ProfesorDB.getById(id);
                if(p!=null) {
                    p.setName(nume);
                    p.setEmail(email);
                    p.setPassword(password);
                    ProfesorDB.updateProfesor(p);
                }
                meniu_admin(admin);
            }
            else if(opt == 8) {
                System.out.println("Actualizare date cont student:");
                System.out.println("Pt ce profesori vrei sa editezi? Introdu id: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Nume nou: ");
                String name = sc.nextLine();
                System.out.print("Email nou: ");
                String email = sc.nextLine();
                System.out.print("Parolă nouă: ");
                String password  = sc.nextLine();

                Student s = StudentDB.getStudentById(id);
                if(s!=null) {
                    s.setName(name);
                    s.setEmail(email);
                    s.setPassword(password);
                    StudentDB.updateStudent(s);
                }
                meniu_admin(admin);
            }
            else if(opt == 9){
                Raport.genereazaRaportStudenti();
            }
            else if (opt == 10) {
                meniu();
                return;
            } else {
                System.out.println("Opțiune invalidă.");
            }
        }
    }

    public void meniu_profesor(Profesor p) {
        int nr;
        Scanner sc = new Scanner(System.in);
        System.out.println("Operatii profesor");
        System.out.println("1. Vizualizare cursuri");
        System.out.println("2. Adaugare curs");
        System.out.println("3. Sterge curs");
        System.out.println("4. Creaza un test");
        System.out.println("5. Afisare teste rezolvate de studenti");
        System.out.println("6. Noteaza un test");
        System.out.println("7. Logout");
        nr = sc.nextInt();
        if (nr == 1) {
            p.listaCursuriPredate = CursDB.getCursuriByProfesor(p);
            p.afisare_cursuri();
            meniu_profesor(p);
        } else if (nr == 2) {
            System.out.println("Introdu titlu:");
            String titlu = sc.next();
            System.out.println("Introdu descriere:");
            String descriere = CitireString(sc);
            Curs c = new Curs(titlu, descriere, "", p);
            p.adaugare_curs(c);
            cursVector.add(c);
            CursDB.insert(c);
            AuditService.log("A fost adaugat un curs");
            meniu_profesor(p);
        } else if (nr == 3) {
            System.out.println("Introdu titlu:");
            String titlu = sc.next();
            p.sterge_curs(titlu);
            CursDB.deleteByTitle(titlu);
            AuditService.log("Curs sters ");
            meniu_profesor(p);
        } else if (nr == 4) {
            System.out.println("Pt ce curs creezi testul: ");
            String curs = sc.next().trim();
            int cIndex = -1;
            for (int i = 0; i < p.listaCursuriPredate.size(); i++) {
                if (curs.equals(p.listaCursuriPredate.get(i).titlu)) {
                    cIndex = i;
                    break;
                }
            }
            if (cIndex == -1) {
                System.out.println("Nu am gasit cursul");
                meniu_profesor(p);
            }
            System.out.println("Introdu numarul de intrebari: ");
            int n = sc.nextInt();
            Test t = new Test(p.listaCursuriPredate.get(cIndex));
            TestDB.insert(t);
            for (int i = 0; i < n; i++) {
                System.out.println("Introdu intrebare: ");
                String question = CitireString(sc);
                System.out.println("Introdu raspuns: ");
                String answer = CitireString(sc);
                Intrebare intrebare = new Intrebare(question, answer);
                IntrebareDB.insert(intrebare, t);
                t.adaugaIntrebare(intrebare);
            }
            p.listaCursuriPredate.get(cIndex).teste.add(t);
            meniu_profesor(p);
        } else if (nr == 5) {
            p.afisareRezolvari();
            meniu_profesor(p);
        } else if (nr == 6) {
            Vector<RezolvareTest> t = p.getTesteRezolvateDeStudenti();
            for (int i = 0; i < t.size(); i++) {
                RezolvareTest rez = t.get(i);
                System.out.println(i + ". Test de la " + rez.getStudent().name + " la " + rez.getTestOriginal().getCurs().titlu);
            }
            System.out.print("Alege testul pe care vrei să-l notezi (index): ");
            int idx = sc.nextInt();
            if (idx >= 0 && idx < t.size()) {
                RezolvareTest rez = t.get(idx);
                System.out.println(rez);
                System.out.print("Introdu punctajul: ");
                int notaFinala = sc.nextInt();
                rez.setScor(notaFinala);
                RezolvareTestDB.updateScor(rez.getId(), notaFinala);
                System.out.println("Testul a fost corectat.");
                AuditService.log("Un profesor a corectat un test.");

            } else {
                System.out.println("Index invalid.");
            }
            meniu_profesor(p);

        }
        else if (nr == 7) {
            meniu();
        } else {
            System.out.println("Optiunea aleasa e invalida!");
            meniu_profesor(p);
        }
    }

    public void meniu_student(Student s) {
        s.incarcaCursuriDinBD();
        System.out.println("Operatii student");
        System.out.println("1. Vizualizare cursuri");
        System.out.println("2. Inscrie-te la un curs");
        System.out.println("3. Vizualizare teste");
        System.out.println("4. Rezolva un test");
        System.out.println("5. Vezi teste rezolvate si notele obtinute");
        System.out.println("5. Logout");
        Scanner sc = new Scanner(System.in);
        int nr = sc.nextInt();
        if (nr == 1) {
            s.afisare_cursuri_student();
            meniu_student(s);
        } else if (nr == 2) {
            System.out.println("Introdu titlul cursului:");
            String str = sc.next();
            boolean ok = false;

            for (Curs c : cursVector) {
                System.out.println(c.titlu);
                if (str.equals(c.titlu)) {
                    s.Adauga_curs(c);
                    CursDB.inscrieStudent(s, c);
                    ok = true;
                    break;
                }
            }
            if (ok) System.out.println("Curs adaugat cu succes");
            else System.out.println("Nu exista acest curs");
            meniu_student(s);
        } else if (nr == 3) {
            s.afisare_teste_student();
            meniu_student(s);
        } else if (nr == 4) {
            s.afisare_teste_student();
            System.out.println("Pentru ce curs vrei sa rezolvi testul?");
            String str = sc.next();
            boolean ok = false;
            for (Curs c : cursVector) {
                if (str.equals(c.titlu)) {
                    c.teste = TestDB.getTesteByCursId(c.getId(),s.getId());
                    System.out.println("Alege un test: 0 - " + (c.teste.size() - 1));
                    int n = sc.nextInt();
                    s.rezolva_test(c.teste.get(n));
                    ok = true;
                    break;
                }
            }
            if (!ok) System.out.println("Nu exista acest curs/test");
            meniu_student(s);
        } else if(nr == 5) {
            s.afisareTesteRezolvate();
            meniu_student(s);
        }
        else if (nr == 6) {
            meniu();
        } else {
            System.out.println("Optiunea aleasa e invalida!");
            meniu_student(s);
        }
    }
}
