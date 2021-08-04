import java.util.Date;

public class Data_from_file {

    /**
     * Data of a specified date, including:
     * 1. Date
     * 2. No. cases by PHU, an int array
     * 3. No. deaths by PHU, an int array
     * 4. No. new cases by PHU, an int array
     * 5. No. vaccinated, one dose, by age, an double array
     * 6. No. vaccinated, two dose, by age, an double array
     */

    private Date date;

    /**
     * Raw_data
     */

    private int[] Unadjusted_cases_by_PHU;
    private int[] Unadjusted_deaths_by_PHU;
    private int[] Unadjusted_new_cases_by_PHU;
    private double[] Percentage_vaccinated_one_dose;
    private double[] Percentage_vaccinated_two_dose;

    public int[] Adjusted_cases_by_PHU;
    public int[] Adjusted_deaths_by_PHU;
    public int[] Adjusted_new_cases_by_PHU;

    public static void main(String[] args) {

    }

    public Data_from_file(){

    }

    public Data_from_file(int PHU_count, int Age_band_count){
        Unadjusted_cases_by_PHU = new int[PHU_count];
        Unadjusted_deaths_by_PHU = new int[PHU_count];
        Adjusted_cases_by_PHU = new int[PHU_count];
        Adjusted_deaths_by_PHU = new int[PHU_count];
        Percentage_vaccinated_one_dose = new double[Age_band_count];
        Percentage_vaccinated_two_dose = new double[Age_band_count];
    }

    public void setUnadjusted_cases_by_PHU(int unadjusted_cases_by_PHU,int PHU_index) {
        Unadjusted_cases_by_PHU[PHU_index] = unadjusted_cases_by_PHU;
    }

    public void setUnadjusted_deaths_by_PHU(int unadjusted_deaths_by_PHU,int PHU_index) {
        Unadjusted_deaths_by_PHU[PHU_index] = unadjusted_deaths_by_PHU;
    }

    private void setAdjusted_cases_by_PHU(int adjusted_cases_by_PHU,int PHU_index) {
        Adjusted_cases_by_PHU[PHU_index] = adjusted_cases_by_PHU;
    }

    private void setAdjusted_deaths_by_PHU(int adjusted_deaths_by_PHU,int PHU_index) {
        Adjusted_deaths_by_PHU[PHU_index] = adjusted_deaths_by_PHU;
    }

    public void Adjust_data_PHU(){
        /**
         * Assumed underacertainment ratio of number of cases/deaths
         */
        double Underacertainment_case = 0.3;
        double Underacertainment_death = 0.1;

        double Adjust_ratio_case = 1 + Underacertainment_case;
        double Adjust_ratio_death = 1 + Underacertainment_death;

    }

}
