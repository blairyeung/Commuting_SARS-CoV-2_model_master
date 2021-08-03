import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Ontario_past_data_IO {
    /**
     * @param args
     * Read all data from the
     */

    final public static String[] Age_band_name = {"12-17yrs","18-29yrs","30-39yrs","40-49yrs","50-59yrs", "60-69yrs", "70-79yrs", "80+"};
    public static ArrayList[] Vaccniated_by_age_band = new ArrayList[Age_band_name.length];

    public static void main(String[] args) {
        Ontario_Data_Input();
    }

    public static void Ontario_Data_Input(){
        Vaccination_data_IO();
        Incidence_data_IO();
        to_County();
    }

    public static void Vaccination_data_IO(){

        for (int age_band = 0; age_band < Age_band_name.length; age_band++) {
            Vaccniated_by_age_band[age_band] = new ArrayList<int[]>();
        }

        ArrayList<String> vaccination_list = new ArrayList<>();
        ArrayList<String> status_list = new ArrayList<>();

        String Path = Parameters.ReadPath + "vaccines_by_age.csv";
        System.out.println(Path);

        FileReader read = null;
        try {
            read = new FileReader(Path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader buffread = new BufferedReader(read);

        String str = null;

        try {
            str = buffread.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(str);

        int Length = Function.Comma_count(str);

        while (true) {
            try {
                if (!((str = buffread.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!str.contains("Undisclosed_or_missing")&&!str.contains("plus")){
                /**Excl the data that does not contain age information*/
                //System.out.println(str);
                String[] Stratified = Function.Stratification(str,Length);
                String date = Stratified[0];
                String age_group = Stratified[1];
                String one_dose = Stratified[2];
                String two_dose = Stratified[3];
                System.out.print("Date:  " + date + "  ");
                System.out.print("Age:  " + age_group + "  ");
                System.out.print("One dose:  " + one_dose + "  ");
                System.out.println("Two dose:  " + two_dose + "  ");
                //Vaccniated_by_age_band;
            }

        }


    }

    public static void Incidence_data_IO(){

    }

    public static void to_County(){


    }
}
