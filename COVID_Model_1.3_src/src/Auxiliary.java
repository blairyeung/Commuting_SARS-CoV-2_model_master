import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Auxiliary{
    public static void main(String[] args) throws Exception{
        System.out.println("./COVID_Model.iml");
        Scanner scan = new Scanner(new File(".\\Model IO\\cases_by_status_and_phu.csv"));
        while (scan.hasNext()) {
            System.out.println(scan.nextLine());
        }
    }

}
