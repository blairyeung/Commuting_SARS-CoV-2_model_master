import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

public class Auxiliary{
    public static void main(String[] args) throws Exception{

        System.out.println("./COVID_Model.iml");
        //Scanner scan = new Scanner(new File(".\\Model IO\\cases_by_status_and_phu.csv"));
        String path = "COVID_Model_1.3\\COVID_Model.iml";
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNext()) {
            System.out.println(scan.nextLine());
        }
    }

}

