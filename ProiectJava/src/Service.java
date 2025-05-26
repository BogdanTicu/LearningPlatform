import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
public class Service {
    static TreeSet<Profesor> profesorSet;
    static ArrayList<Student> studentVector;
    static Vector<Curs> cursVector;
    Service() {
        profesorSet = new TreeSet<>(Comparator.comparing(Profesor::getEmail));
        studentVector = new ArrayList<>();
        cursVector = new Vector<>();
    }
    public String CitireString(Scanner sc){

        StringBuilder descriere= new StringBuilder();
        while(sc.hasNext()) {
            String cuv = sc.next();
            if(cuv.equals("-1"))
                break;
            else
                descriere.append(cuv).append(" ");
        }
        return descriere.toString();
    }
    public void init_studenti()
    {
        int n;
        Scanner sc = null;
        try {
            sc = new Scanner(new File("studenti.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        n = sc.nextInt();
        for(int i = 0; i < n; i++){
            String name, email, password;
            name = sc.next();
            email = sc.next();
            password = sc.next();
            name = name.trim();
            email = email.trim();
            password = password.trim();
            studentVector.add(new Student(name, email, password));
        }
    }

    public void init_profesori()
    {
        int n;
        Scanner sc = null;
        try {
            sc = new Scanner(new File("profesori.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        n = sc.nextInt();
        for(int i = 0; i < n; i++){
            String name, email, password;
            name = sc.next();
            email = sc.next();
            password = sc.next();
            name = name.trim();
            email = email.trim();
            password = password.trim();
            profesorSet.add(new Profesor(name, email, password));
        }
    }
    public void scrie_profesori()
    {
        FileWriter fw = null;
        try {
            fw = new FileWriter("profesori.txt");
            fw.write(profesorSet.size()+"\n");
            for(Profesor profesor : profesorSet){
                fw.write(profesor.name+"\n");
                fw.write(profesor.email+"\n");
                fw.write(profesor.password+"\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void scrie_studenti(){
        FileWriter fw = null;
        try {
           fw = new FileWriter("studenti.txt");
            fw.write(studentVector.size()+"\n");
            for(int i = 0; i < studentVector.size(); i++){
                fw.write(studentVector.get(i).name+"\n");
                fw.write(studentVector.get(i).email+"\n");
                fw.write(studentVector.get(i).password+"\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void meniu()
    {
        Admin admin = new Admin("admin", "admin@gmail.com", "admin123");
        System.out.println("1. Login ca admin");
        System.out.println("2. Login ca profesor");
        System.out.println("3. Login ca student");
        System.out.println("4. Deconectare aplicatie");
        Scanner sc = new Scanner(System.in);
        int optiuni = sc.nextInt();
        sc.nextLine();
        if(optiuni == 1)
        {
            String email, password;
            //2sc.nextLine();
            System.out.println("Introdu mail:");
            email = CitireString(sc);
            System.out.println("Introdu parola:");
            password = sc.nextLine();
            if(admin.login(email, password)) {
                meniu_admin(admin);
            } else {
                System.out.println("Mail sau parola incorecte!");
                meniu();
            }
        }
        else if(optiuni==2)
        {
            String email;
            String password;
            int ok=0;
            System.out.println("Introdu mail:");
            email = sc.nextLine();
            System.out.println("Introdu password:");
            password = sc.nextLine();
            for(Profesor p: profesorSet) {
                if (p.login(email, password) == true) {
                    ok=1;
                    meniu_profesor(p);
                    break;
                }
            }
            if(ok==0)
            {
                System.out.println("Mail sau parola incorecte!");
                meniu();
            }
        }
        else if(optiuni==3)
        {
            String email;
            String password;
            int ok=0;
            System.out.println("Introdu mail:");
            email = sc.nextLine();
            System.out.println("Introdu password:");
            password = sc.nextLine();
            for(int i = 0; i<studentVector.size(); i++) {
                if (studentVector.get(i).login(email, password) == true) {
                    ok=1;
                    meniu_student(studentVector.get(i));
                }
            }
            if(ok==0)
            {
                System.out.println("Mail sau parola incorecte!");
            }

        }
        else if(optiuni==4)
        {
            scrie_studenti();
            scrie_profesori();
        }
        else {
            System.out.println("Optiune invalida");
            meniu();
        }

    }

    public void meniu_admin(Admin admin) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Adaugă profesor");
            System.out.println("2. Șterge profesor");
            System.out.println("3. Adaugă student");
            System.out.println("4. Șterge student");
            System.out.println("5. Vezi lista profesori");
            System.out.println("6. Vezi lista studenți");
            System.out.println("7. Logout");
            int opt = sc.nextInt();
            sc.nextLine();

            if (opt == 1) {
                System.out.print("Nume: ");
                String nume = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Parola: ");
                String parola = sc.nextLine();
                admin.adaugaProfesor(new Profesor(nume, email, parola));
            } else if (opt == 2) {
                System.out.print("Email profesor de șters: ");
                admin.stergeProfesor(sc.nextLine());
            } else if (opt == 3) {
                System.out.print("Nume: ");
                String nume = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Parola: ");
                String parola = sc.nextLine();
                admin.adaugaStudent(new Student(nume, email, parola));
            } else if (opt == 4) {
                System.out.print("Email student de șters: ");
                admin.stergeStudent(sc.nextLine());
            } else if (opt == 5) {
                admin.afiseazaProfesori();
            } else if (opt == 6) {
                admin.afiseazaStudenti();
            } else if (opt == 7) {
                meniu();
                return;
            } else {
                System.out.println("Opțiune invalidă.");
            }

        }
    }

    public void meniu_profesor(Profesor p)
    {
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
        if(nr==1)
        {
            p.afisare_cursuri();
            meniu_profesor(p);
        }
        else if(nr==2)
        {
            String titlu;
            System.out.println("Introdu titlu:");
            titlu = sc.next();
            System.out.println("Introdu descriere:");
            String descriere = CitireString(sc);
            Curs c = new Curs(titlu, descriere,p);
            p.adaugare_curs(c);
            cursVector.add(c);
            meniu_profesor(p);
        }
        else if (nr==3)
        {
            String titlu;
            System.out.println("Introdu titlu:");
            titlu = sc.next();
            p.sterge_curs(titlu);
            meniu_profesor(p);
        }
        else if(nr==4){
            String curs;
            int n,cIndex=-1;
            System.out.println("Pt ce curs creezi testul: ");
            curs = sc.next();
            curs = curs.trim();
            for(int i=0;i<p.listaCursuriPredate.size();i++)
            {
                if(curs.equals(p.listaCursuriPredate.get(i).titlu))
                {
                    cIndex = i;
                    break;
                }
            }
            if(cIndex==-1)
            {
                System.out.println("Nu am gasit cursul");
                meniu_profesor(p);
            }
            System.out.println("Introdu numarul de intrebari: ");
            n = sc.nextInt();
            Test t = new Test(p.listaCursuriPredate.get(cIndex));
            for(int i=0;i<n;i++)
            {
                String question,answer;
                System.out.println("Introdu intrebare: ");
                question=CitireString(sc);
                System.out.println("Introdu raspuns: ");
                answer = CitireString(sc);
                Intrebare intrebare = new Intrebare(question,answer);
                t.addIntrebare(intrebare);
            }
            p.listaCursuriPredate.get(cIndex).teste.add(t);
            meniu_profesor(p);
        }
        else if(nr==5)
        {
            p.afiseazaRezolvari();
            meniu_profesor(p);
        }
        else if(nr==6)
        {
            Vector<RezolvareTest> t = p.getTesteRezolvateDeStudenti();
            for (int i = 0; i < t.size(); i++) {
                RezolvareTest rez = t.get(i);
                System.out.println(i + ". Test de la " + rez.getStudent().name+ " la " + rez.getTestOriginal().getCurs().titlu);
            }

            System.out.print("Alege testul pe care vrei să-l notezi (index): ");
            int idx = sc.nextInt();

            if (idx >= 0 && idx < t.size()) {
                System.out.println(t.get(idx));
            } else {
                System.out.println("Index invalid.");
            }
            RezolvareTest rez = t.get(idx);
            int notaFinala;
            try {
                System.out.print("Introdu punctajul: ");
                notaFinala = sc.nextInt();
                rez.setScor(notaFinala);
            }
            catch(ArithmeticException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Testul a fost corectat.");
            meniu_profesor(p);
        }
        else if(nr==7)
        {
            meniu();
        }
        else{
            System.out.println("Optiunea aleasa e invalida!");
            meniu_profesor(p);
        }
    }
    public void meniu_student(Student s)
    {
        System.out.println("Operatii student");
        System.out.println("1. Vizualizare cursuri");
        System.out.println("2. Inscrie-te la un curs");
        System.out.println("3. Vizualizare teste");
        System.out.println("4. Rezolva un test");
        System.out.println("5. Logout");
        Scanner sc = new Scanner(System.in);
        int nr = sc.nextInt();
        if(nr==1)
        {
            s.afisare_cursuri_student();
            meniu_student(s);
        }
        else if(nr==2)
        {
            int ok=0;
            System.out.println("Introdu titlul cursului:\n");
            String str = sc.next();
            for(Curs c: cursVector)
            {
                if(str.equals(c.titlu))
                {
                    ok=1;
                    s.Adauga_curs(c);
                    break;
                }
            }
            if(ok==1)
            {
                System.out.println("Curs adaugat cu succes");
            }
            else
                System.out.println("Nu exista acest curs");
            meniu_student(s);
        }
        else if(nr==3)
        {
            s.afisare_teste_student();
            meniu_student(s);
        }
        else if(nr==4)
        {
            boolean ok=false;
            s.afisare_teste_student();
            System.out.println("Pentru ce curs vrei sa rezolvi testul? \n");
            String str = sc.next();
            for(Curs c: cursVector)
            {
                if(str.equals(c.titlu))
                {
                    int cv = c.teste.size()-1;
                    System.out.println("Alege un test: 0 - " + cv);

                    int n = sc.nextInt();
                    s.rezolva_test(c.teste.get(n));
                    ok=true;
                    break;
                }
            }
            if(!ok)
            {
                System.out.println("Nu exista acest curs/test");
            }
            meniu_student(s);
        }
        else if(nr==5)
        {
            meniu();
        }
        else
        {
            System.out.println("Optiunea aleasa e invalida!");
            meniu_student(s);
        }
    }
}
