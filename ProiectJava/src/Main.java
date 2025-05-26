import java.util.Scanner;
import java.util.Vector;
//platforma de elearning.


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.init_profesori();
        service.init_studenti();
        service.meniu();
    }
}