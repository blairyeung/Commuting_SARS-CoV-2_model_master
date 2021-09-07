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
     * Read all data from the 
     */ 

    final public static String[] Age_band_name = {"12-17yrs","18-29yrs","30-39yrs","40-49yrs","50-59yrs", "60-69yrs", "70-79yrs", "80+"};
    final public static String[] Age_band_name_output = Parameters.AgeBand;

    public static ArrayList[] Vaccinated_by_age_band = new ArrayList[Age_band_name.length];
    public static ArrayList[] Incidence_by_PHU = new ArrayList[Age_band_name.length];
    public static ArrayList<Date> Date_index = new ArrayList();
    public static ArrayList<Data_from_file> Ontario_data = new ArrayList();


    //Adjust data by age band
    public static double[] Adjustment_cases = new double[16];
    public static double[] Adjustment_deaths = new double[16];
    public static double[] Adjustment_resolved = new double[16];

    public static double[] Variant_ratio = new double[Parameters.Total_number_of_variants];

    public static ArrayList[] Population_ratio_by_PHU = null;

    public static CountyDataArray[] Ontario_past_data_array  = null;
    //public static ArrayList<CountyDataArray> Ontario_past_data_array  = new ArrayList<>();

    public static double[] Age_band_ratio_array = null;
    public static ArrayList<Double> Age_band_ratio = new ArrayList<>();
    public static double[] Age_vaccination_ratio_first = null;
    public static double[] Age_vaccination_ratio_second = null;

    public static void main(String[] args) {
        Ontario_Data_Input();
    }

    public static void Ontario_Data_Input(){
        /**
         * Load dependency
         */
        CountyDataIO.CountyData_IO_Input();
        PHU.PHU_IO_Input();

        Population_ratio_by_PHU = new ArrayList[PHU.Number_of_PHUs];

        Age_specific_data_IO();
        Vaccination_data_IO();
        Incidence_data_IO();
        Calculated_age_band_ratio();
        to_County();

    }

    public static void Vaccination_data_IO(){

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        for (int age_band = 0; age_band < Age_band_name.length; age_band++) {
            Vaccinated_by_age_band[age_band] = new ArrayList<Double[]>();
        }

        ArrayList<String> vaccination_list = new ArrayList<>();
        ArrayList<String> status_list = new ArrayList<>();

        String Path = Parameters.ReadPath + "vaccines_by_age.csv";
        System.out.println(Path);

        ArrayList<String> Lines = Function.Buffered_IO(Path);

        String str = Lines.get(0);
        int Length = Function.Column_count(str);

        for (int line = 1; line < Lines.size(); line++) {
             str = Lines.get(line);
            if(!str.contains("Undisclosed_or_missing")&&!str.contains("plus")){
                /**Excl the data that does not contain age information*/
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
            }
        }

        for (int day = 0; day < Ontario_data.size(); day++) {

            Age_vaccination_ratio_first = new double[Age_band_ratio.size()];
            Age_vaccination_ratio_second = new double[Age_band_ratio.size()];

            double[] first_dose = Ontario_data.get(day).getPercentage_vaccinated_one_dose();
            double[] second_dose = Ontario_data.get(day).getPercentage_vaccinated_two_dose();

            for (int i = 0; i < Age_band_name.length - 1; i++) {
                String Interval = Age_band_name[i];
                int Start_age = Integer.parseInt(Interval.substring(0,Interval.indexOf("-")));
                int End_age = Integer.parseInt(Interval.substring(Interval.indexOf("-")+1,Interval.indexOf("yrs")));
                System.out.println("Start_age = " + Start_age);
                System.out.println("End_age = " + End_age);

                for (int age = Start_age; age < End_age; age++) {
                    Age_vaccination_ratio_first[age] =  first_dose[i];
                    Age_vaccination_ratio_second[age] =  second_dose[i];
                }
            }

            double One_dose_array[] = new double[Age_band_name_output.length];
            double Two_dose_array[] = new double[Age_band_name_output.length];

            first_dose = new double[Age_band_name_output.length];
            second_dose = new double[Age_band_name_output.length];

            for (int i = 0; i < Age_band_name_output.length - 1; i++) {
                String Interval = Age_band_name_output[i];
                System.out.println(Interval);
                int Start_age = Integer.parseInt(Interval.substring(0,Interval.indexOf("to")-1));
                int End_age = Integer.parseInt(Interval.substring(Interval.indexOf("to")+3));
                System.out.println("Start_age = " + Start_age);
                System.out.println("End_age = " + End_age);
                double first_dose_vaccinated_subtotal = 0;
                double second_dose_vaccinated_subtotal = 0;
                double population_subtotal = Parameters.Ontario_population_by_age[i];/**Population import from statistics canada*/
                for (int age = Start_age; age < End_age; age++) {
                    first_dose_vaccinated_subtotal += (Age_vaccination_ratio_first[age]/population_subtotal);
                    second_dose_vaccinated_subtotal += (Age_vaccination_ratio_second[age]/population_subtotal);
                }
                One_dose_array[i] = first_dose_vaccinated_subtotal;
                Two_dose_array[i] = second_dose_vaccinated_subtotal;
            }

            int Start_age = 75;
            int End_age = Age_vaccination_ratio_first.length;

            double first_dose_vaccinated_subtotal = 0;
            double second_dose_vaccinated_subtotal = 0;
            double population_subtotal = Parameters.Ontario_population_by_age[Age_band_name_output.length-1];/**Population import from statistics canada*/

            for (int age = Start_age; age < End_age; age++) {
                first_dose_vaccinated_subtotal += (Age_vaccination_ratio_first[age]/population_subtotal);
                second_dose_vaccinated_subtotal += (Age_vaccination_ratio_second[age]/population_subtotal);
            }

            One_dose_array[Age_band_name_output.length-1] = first_dose_vaccinated_subtotal;
            Two_dose_array[Age_band_name_output.length-1] = second_dose_vaccinated_subtotal;

            Ontario_data.get(day).setPercentage_vaccinated_one_dose_by_age(One_dose_array);
            Ontario_data.get(day).setPercentage_vaccinated_two_dose_by_age(Two_dose_array);
        }
    }

    /**
     * Suppose
     * there are 100,000 people in york region
     * richmond hill pop: 30,000
     * markham pop: 50,000
     * north york: 20,000
     *
     * today, there are 200 new cases in york region
     *
     * then, we assume that there are:
     * 60 new cases in richmond hill
     * 100 new cases in markham
     * 40 news cases in north york
     *
     * and this pattern applies to resolved and deaths
     */

    public static void Incidence_data_IO(){
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        for (int age_band = 0; age_band < Age_band_name.length; age_band++) {
            Incidence_by_PHU[age_band] = new ArrayList<Double[]>();
        }

        ArrayList<String> vaccination_list = new ArrayList<>();
        ArrayList<String> status_list = new ArrayList<>();

        String Path = Parameters.ReadPath + "cases_by_status_and_phu.csv";

        ArrayList<String> Lines = Function.Buffered_IO(Path);

        String str = Lines.get(0);

        int Length = Function.Column_count(str);

        for (int line = 1; line < Lines.size(); line++) {
            str = Lines.get(line);
            if(!Ontario_data.isEmpty()){
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
                }

                String PHU_code = Stratified[1];

                if(PHU_code.isEmpty()){
                    continue;
                }

                String[] PHU_List = PHU.PHUs;

                int PHU_index = Function.index_of_object_in_array(PHU_code,PHU_List);

                int index = Date_index.indexOf(date);
                Ontario_data.get(index).setUnadjusted_cases_by_PHU(Integer.parseInt(Stratified[3]),PHU_index);
                Ontario_data.get(index).setUnadjusted_resolved_by_PHU(Integer.parseInt(Stratified[4]),PHU_index);
                Ontario_data.get(index).setUnadjusted_deaths_by_PHU(Integer.parseInt(Stratified[5]),PHU_index);
            }
        }

        for (int i = 0; i < Ontario_data.size(); i++) {
            Ontario_data.get(i).adjust_data();
        }

        Sort(Ontario_data);

        /*for(Data_from_file d: Ontario_data){
            System.out.println("Date: " + d.getDate());
            for (int i = 0; i < Age_band_name.length; i++) {
                System.out.print("Age_band: " + Age_band_name[i]);
                System.out.print("    One Dose: " + d.getPercentage_vaccinated_one_dose(i));
                System.out.println("    Two doses: " + d.getPercentage_vaccinated_two_dose(i));
            }

            for (int i = 0; i < PHU.PHUs_list.size(); i++) {
                System.out.print("PHU: " + PHU.PHUs_list.get(i));
                System.out.print("    Case: " + d.getAdjusted_cases_by_PHU(i));
                System.out.print("    Death: " + d.getAdjusted_deaths_by_PHU(i));
                System.out.println("    Resolved: " + d.getAdjusted_resolved_by_PHU(i));
            }
        }*/


    }

    public static void to_County(){
        Ontario_past_data_array = new CountyDataArray[CountyDataIO.Counties.length];
        for (int County_Code = 0; County_Code < CountyDataIO.Counties.length; County_Code++) {

            for (int date = 0; date < Date_index.size(); date++) {
                Data_from_file today_data = Ontario_data.get(date);
                CountyData data = CountyDataIO.Counties[County_Code];
                int ID = CountyDataIO.DistrictCode.indexOf(data.getDistrict());
                int PHU_index =  PHU.PHU_by_Division[ID];
                double incidence = today_data.getAdjusted_cases_by_PHU(PHU_index);
                double deaths = today_data.getAdjusted_deaths_by_PHU(PHU_index);
                double resolved = today_data.getAdjusted_resolved_by_PHU(PHU_index);

                Data today_county_data = new Data();
                for (int Age_band = 0; Age_band < 16; Age_band++) {
                    double Vaccinated_one_dose_age_band = today_data.getPercentage_vaccinated_one_dose()[Age_band];
                    double Vaccinated_two_dose_age_band = today_data.getPercentage_vaccinated_two_dose()[Age_band];
                    double age_band_adjusted_incidence = incidence * Adjustment_cases[Age_band];
                    double age_band_adjusted_deaths = deaths * Adjustment_deaths[Age_band];
                    double age_band_adjusted_resolved = resolved * Adjustment_resolved[Age_band];

                    Age_vaccination_ratio_first = new double[Age_band_ratio.size()];
                    Age_vaccination_ratio_second = new double[Age_band_ratio.size()];

                    /**
                     * 需要写到today data里 在该method运行前执行
                     */

                        /*for (int i = 0; i < Age_band.length - 1; i++) {
                            String Interval = Age_band[i];
                            int Start_age = Integer.parseInt(Interval.substring(0,Interval.indexOf("to")-1));
                            int End_age = Integer.parseInt(Interval.substring(Interval.indexOf("to")+3));
                            System.out.println("Start_age = " + Start_age);
                            System.out.println("End_age = " + End_age);
                            //Age_vaccination_ratio[Start_age];
                        }*/

                    for (int variant = 0; variant < Parameters.Total_number_of_variants; variant++) {
                        today_county_data.setValueDataPackByAge(variant,Age_band,17,((int) Math.round(age_band_adjusted_incidence)));//Set incidence
                        today_county_data.setValueDataPackByAge(variant,Age_band,18,((int) Math.round(age_band_adjusted_incidence)));//Set exposed
                        today_county_data.setValueDataPackByAge(variant,Age_band,19,((int) Math.round(age_band_adjusted_incidence)));//Set active cases
                        today_county_data.setValueDataPackByAge(variant,Age_band,21,((int) Math.round(age_band_adjusted_resolved)));//Set resolved
                        today_county_data.setValueDataPackByAge(variant,Age_band,22,((int) Math.round(age_band_adjusted_deaths)));//Set deaths
                        today_county_data.setValueDataPackByAge(variant,Age_band,23,((int) Math.round(Vaccinated_one_dose_age_band)));//Set vaccinated one dose
                        today_county_data.setValueDataPackByAge(variant,Age_band,24,((int) Math.round(Vaccinated_two_dose_age_band)));//Set vaccinated two dose
                    }
                }
            }
        }
    }

    public static ArrayList<Data_from_file> Sort(ArrayList<Data_from_file> dateList){
            dateList.sort((a1, a2) -> {
                return a1.getDate().compareTo(a2.getDate());
            });
            return dateList;
    }


    public static void Age_specific_data_IO(){
        String Path = Parameters.ReadPath + "Ontario_population_by_age.csv";
        ArrayList<String> Buffer = Function.Buffered_IO(Path,false);
        ArrayList<Double> Population_by_age_band = new ArrayList<>();

        for (int line = 0; line < Buffer.size(); line++) {
            //System.out.println(Buffer.get(line));
            Population_by_age_band.add((double) Math.round(Double.parseDouble(Buffer.get(line))));
        }

        Age_band_ratio = Function.Normalization(Population_by_age_band);

        for (int line = 0; line < Age_band_ratio.size(); line++) {
            System.out.println(Age_band_ratio.get(line));
        }
        Age_band_ratio_array = ArrayListToArray.toArray(Age_band_ratio, Age_band_ratio_array);
    }

    public static void Calculated_age_band_ratio(){
        String[] Age_band_name = {"12-17yrs","18-29yrs","30-39yrs","40-49yrs","50-59yrs", "60-69yrs", "70-79yrs", "80+"};
        String[] Age_band = {"0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+"};

        double Age_band_ratio_eight[] = new double[Age_band_name.length];
        double Age_band_ratio_sixteen[] = new double[Age_band.length];


    }


    public static int Relocate_age_band(int AgeBand){
       /**
        * dr zhu plz finish this
        */
       String[] Age_band_name = {"12-17yrs","18-29yrs","30-39yrs","40-49yrs","50-59yrs", "60-69yrs", "70-79yrs", "80+"};
       String[] Age_band = {"0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+"};

       switch (AgeBand){
           case 0, 1:
               /**
                *
                */
               return -1;
           case 2:
               return 0;
           case 3:

       }

       return 0;
    }

    public static double[] getAgebandBais(int AgeBand){
        switch (AgeBand){
            case 0, 1:
                return new double[]{-1,0};

        }
        return new double[2];
    }
}
