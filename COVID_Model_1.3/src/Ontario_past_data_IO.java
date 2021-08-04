import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Ontario_past_data_IO {
    /**
     * @param args
     * Read all data from the
     */

    final public static String[] Age_band_name = {"12-17yrs","18-29yrs","30-39yrs","40-49yrs","50-59yrs", "60-69yrs", "70-79yrs", "80+"};
    public static ArrayList[] Vaccniated_by_age_band = new ArrayList[Age_band_name.length];
    public static ArrayList<Date> Date_index = new ArrayList();
    public static ArrayList<Data_from_file> Ontario_data = new ArrayList();

    public static void main(String[] args) {
        Ontario_Data_Input();
    }

    public static void Ontario_Data_Input(){
        Vaccination_data_IO();
        Incidence_data_IO();
        to_County();
    }

    public static void Vaccination_data_IO(){

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        for (int age_band = 0; age_band < Age_band_name.length; age_band++) {
            Vaccniated_by_age_band[age_band] = new ArrayList<Double[]>();
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
                Date date = null;

                try {
                    date = date_format.parse(Stratified[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(!Date_index.contains(date)){
                    Date_index.add(date);
                    Ontario_data.add(new Data_from_file(date));
                    //Data_from_file data = new Data_from_file(date);
                }

                String age_group = Stratified[1];
                int age_group_index = Function.index_of_object_in_array(Stratified[1],Age_band_name);
                double one_dose = Double.parseDouble(Stratified[2]);
                double two_dose = Double.parseDouble(Stratified[3]);
                System.out.print("Date:  " + date + "  ");
                System.out.print("Age:  " + age_group + "  ");
                System.out.print("One dose:  " + one_dose + "  ");
                System.out.println("Two dose:  " + two_dose + "  ");
                int index = Date_index.indexOf(date);
                Ontario_data.get(index).setPercentage_vaccinated_one_dose_by_age(one_dose,age_group_index);
                Ontario_data.get(index).setPercentage_vaccinated_two_dose_by_age(two_dose,age_group_index);
                //Vaccniated_by_age_band[Function.index_of_object_in_array(age_group,Age_band_name)].add(new double[]{one_dose,two_dose});
            }
        }
    }

    public static void Incidence_data_IO(){

    }

    public static void to_County(){


    }
}
