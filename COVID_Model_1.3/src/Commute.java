import java.text.Normalizer;
import java.util.ArrayList;

public class Commute {

    public static final int UpperLimit = 120;/**Assumed limit for commute distance*/

    private static int Commute_Matrix[][] = null; /**Live_in_County_A_Work_in_County_B*/
    private static int Exportations[] = null; /**Live_in_County_A_Work_in_County_B*/


    private static int Generated_Matrix[][] = null; /**Live_in_County_A_Work_in_County_B*/
    private static int Generated_Reversed_Commute_Matrix[][] = null; /**Live_in_County_A_Work_in_County_B*/
    private static int Generated_Exportations[] = null; /**Live_in_County_A_Work_in_County_B*/
    private static int Generated_Importations[] = null; /**Live_in_County_A_Work_in_County_B*/

    private static int Reversed_Commute_Matrix[][] = null; /**Work_in_County_A_Resident_in_County_B*/
    private static int Importations[] = null; /**Work_in_County_A_Resident_in_County_B*/

    private static int Total_number_of_commuters[][] =  null;

    private static int Commute_distance_tier_by_county[][] = null;

    private static int Commute_distance_tier_by_county_rural_subarray[][] = null;
    private static int Commute_distance_tier_by_county_city_subarray[][] = null;

    private static ArrayList[] Rural_counties_within_commute_range_code= null;
    private static ArrayList[] City_counties_within_commute_range_code= null;

    private static double[] Rural_counties_within_commute_range_population= null;
    private static double[] City_counties_within_commute_range_population= null;

    private static ArrayList[] Rural_counties_within_commute_range_tier= null;
    private static ArrayList[] City_counties_within_commute_range_tier= null;

    private static boolean Within_same_district_by_county[][] = null;

    private static int[] Total_Population_of_potential_rural_within_range = null;
    private static int[] Total_Population_of_potential_city_within_range = null;

    private static int[][] Tiered_Total_Population_of_potential_rural_within_range = null;
    private static int[][] Tiered_Total_Population_of_potential_city_within_range = null;

    public static void getStaticCommuteMatrix(){

        /**
         * Stratify counties by distance to another city, for tiered
         * Will only be ran once to save computational power
         */

        int Number_of_Counties = CountyDataIO.Counties.length;

        Commute_distance_tier_by_county = new int[Number_of_Counties][Parameters.Travel_distance_distribution_full.length];
        Within_same_district_by_county = new boolean[Number_of_Counties][Parameters.Travel_distance_distribution_full.length];

        Total_Population_of_potential_rural_within_range = new int[Number_of_Counties];
        Total_Population_of_potential_city_within_range = new int[Number_of_Counties];

        Exportations = new int[Number_of_Counties];
        Importations = new int[Number_of_Counties];

        Tiered_Total_Population_of_potential_city_within_range = new int[Number_of_Counties][Parameters.Travel_distance_distribution_full.length];
        Tiered_Total_Population_of_potential_rural_within_range = new int[Number_of_Counties][Parameters.Travel_distance_distribution_full.length];

        Rural_counties_within_commute_range_code = new ArrayList[Number_of_Counties];
        City_counties_within_commute_range_code = new ArrayList[Number_of_Counties];

        Rural_counties_within_commute_range_population = new double[Number_of_Counties];
        City_counties_within_commute_range_population = new double[Number_of_Counties];

        Rural_counties_within_commute_range_tier = new ArrayList[Number_of_Counties];
        City_counties_within_commute_range_tier = new ArrayList[Number_of_Counties];

        Total_number_of_commuters = new int[Number_of_Counties][2];


        for (int Resident = 0; Resident < Number_of_Counties; Resident++) {

            Rural_counties_within_commute_range_code[Resident] = new ArrayList<Integer>();
            City_counties_within_commute_range_code[Resident] = new ArrayList<Integer>();

            Rural_counties_within_commute_range_tier[Resident] = new ArrayList<Integer>();
            City_counties_within_commute_range_tier[Resident] = new ArrayList<Integer>();

            for (int Work = 0; Work < Number_of_Counties; Work++) {

                if(Resident==Work){
                    continue;
                }

                double distance = CountyDataIO.DistanceBetweenCounties[Resident][Work];

                int Resident_population = CountyDataIO.Counties[Resident].getPopulation();

                if(distance<=UpperLimit){
                    int distance_tier = (int) distance/5;

                    int County_type = CountyDataIO.Counties[Work].getCounty_Type();
                    int Workplace_population = CountyDataIO.Counties[Work].getPopulation();

                    //double Distribution_Constant = Function.findGuassianBlur(Parameters.Travel_distance_distribution_full, distance_tier);

                    switch (County_type){
                        case 0:
                            /**
                             * If this county belongs to rural
                             */

                            Rural_counties_within_commute_range_code[Resident].add(Work);
                            Rural_counties_within_commute_range_tier[Resident].add(distance_tier);

                            Rural_counties_within_commute_range_population[Resident] += Workplace_population;

                            Total_Population_of_potential_rural_within_range[Resident] += Workplace_population;

                            Tiered_Total_Population_of_potential_rural_within_range[Resident][distance_tier] += Workplace_population;

                            break;
                       case 1:
                            /**
                             * If this county belongs to city
                             */

                            City_counties_within_commute_range_code[Resident].add(Work);
                            City_counties_within_commute_range_tier[Resident].add(distance_tier);

                            Total_Population_of_potential_city_within_range[Resident] += Workplace_population;

                            Tiered_Total_Population_of_potential_city_within_range[Resident][distance_tier] += Workplace_population;

                           break;
                        default:
                            /**throw new Exception(){

                            }*/

                            continue;
                    }

                }

            }


            /**
             * Categorize County into CMA, CA, MIZ
             */

            for (int County_Code = 0; County_Code < Number_of_Counties; County_Code++) {

                int Resident_population = CountyDataIO.Counties[County_Code].getPopulation();

                int Resident_county_type = CountyDataIO.Counties[County_Code].getCounty_Type();

                switch (Resident_county_type){
                    case 0:
                        double Urban_population_ratio;
                        Urban_population_ratio = City_counties_within_commute_range_population[County_Code]/(City_counties_within_commute_range_population[County_Code] + Rural_counties_within_commute_range_population[County_Code]);

                        if(Urban_population_ratio>0.625){/**Strong MIZ*/
                            CountyDataIO.Counties[Resident].setCounty_Category(3);
                        }
                        else if(Urban_population_ratio>0.167){/**Moderate MIZ*/
                            CountyDataIO.Counties[Resident].setCounty_Category(4);
                        }
                        else if(Urban_population_ratio>0.05){/**Weak MIZ*/
                            CountyDataIO.Counties[Resident].setCounty_Category(5);
                        }
                        else {/**No MIZ*/
                            CountyDataIO.Counties[Resident].setCounty_Category(6);
                        }

                        break;
                    case 1:
                        if(Resident_population>500000){
                            /**
                             * Larger CMAs
                             */
                            CountyDataIO.Counties[Resident].setCounty_Category(0);
                        }

                        else if(Resident_population>100000){
                            /**
                             * Smaller CMAs
                             */
                            CountyDataIO.Counties[Resident].setCounty_Category(1);
                        }
                        else {
                            /**
                             * All CAs
                             */
                            CountyDataIO.Counties[Resident].setCounty_Category(2);
                        }
                        break;

                }



            }

            /**
             * Find the number of commuters to rural/city by the population of the county and the type of the county
             */

            for (int County_Code = 0; County_Code < Number_of_Counties; County_Code++) {
                int County_type = CountyDataIO.Counties[County_Code].getCounty_Type();
                int County_Category = CountyDataIO.Counties[County_Code].getCounty_Category();
                int County_Population = CountyDataIO.Counties[County_Code].getPopulation();

                double Commuter_ratio = 0;
                double toUrban = 0;
                double toRural = 0;

                int toUrbanPopulation = 0;
                int toRuralPopulation = 0;

                Commuter_ratio = Parameters.Commuter_ratio_by_category[County_Category];

                toRural = Commuter_ratio * Parameters.Commuter_ratio_by_destination[County_Category][0];
                toUrban = Commuter_ratio * Parameters.Commuter_ratio_by_destination[County_Category][1];

                toRuralPopulation = (int) Math.round(toRural * ((double) County_Population));
                toUrbanPopulation = (int) Math.round(toUrban * ((double) County_Population));

                Total_number_of_commuters[County_Code][0] = toRuralPopulation;
                Total_number_of_commuters[County_Code][1] = toUrbanPopulation;

            }


            //System.out.println(CountyDataIO.Counties[Resident].getName());

            for (int i = 0; i < 24; i++) {
                //System.out.println("Tier= " + Parameters.Travel_distance_tiers_full[i]);
                //System.out.println("Rural_destination_sum_of_Population= " + Tiered_Total_Population_of_potential_rural_within_range[Resident][i]);
                //System.out.println("City_destination_sum_of_Population= " + Tiered_Total_Population_of_potential_city_within_range[Resident][i]);
            }

            //System.out.println("Total");

            //System.out.println("Rural_destination_sum_of_Population= " + Total_Population_of_potential_rural_within_range[Resident]);
            //System.out.println("City_destination_sum_of_Population= " + Total_Population_of_potential_city_within_range[Resident]);

        }

        Commute_Matrix = new int[Number_of_Counties][Number_of_Counties];
        Reversed_Commute_Matrix = new int[Number_of_Counties][Number_of_Counties];


        for (int Resident = 0; Resident < Number_of_Counties; Resident++) {

            double MetroInfluentialCoefficient = 1;
            /*if(City_counties_within_commute_range_code[Resident].contains(0)||Resident==0){
                MetroInfluentialCoefficient = 0.5;
            }*/

            for (int Work = 0; Work < Number_of_Counties; Work++) {

                int Tier = 0;

                if(Resident==Work){
                    continue;
                }

                double distance = CountyDataIO.DistanceBetweenCounties[Resident][Work];

                if(distance<=UpperLimit){

                    int Workplace_population = CountyDataIO.Counties[Work].getPopulation();

                    Tier = (int) (distance/5.0);
                    int County_Type = CountyDataIO.Counties[Work].getCounty_Type();
                    int Total_population_range = 0;

                    double Proportion = 0;
                    double Total_commuter = Total_number_of_commuters[Resident][County_Type];

                    int Expected_number_of_commuters = 0; /**Assumming baseline scenario*/



                    switch (County_Type){
                        case 0:
                            /**
                             * Rural
                             */

                            Total_population_range = Tiered_Total_Population_of_potential_rural_within_range[Resident][Tier];

                            Proportion = (((double)Workplace_population)/ ((double)Total_population_range));

                            //System.out.println("Proportion:  " + Proportion);

                            Expected_number_of_commuters = (int) Math.round(Parameters.Work_force_ratio * Proportion * Total_commuter * Parameters.Travel_distance_distribution_full[Tier] * MetroInfluentialCoefficient);

                            break;
                        case 1:

                            /**
                             * City
                             */

                            Total_population_range = Tiered_Total_Population_of_potential_city_within_range[Resident][Tier];

                            Proportion = (((double)Workplace_population)/ ((double)Total_population_range));

                            Expected_number_of_commuters = (int) Math.round(Parameters.Work_force_ratio * Proportion * Total_commuter * Parameters.Travel_distance_distribution_full[Tier] * MetroInfluentialCoefficient);

                            break;
                    }


                    if(Work==0){
                        //System.out.println(true);

                        //System.out.println(CountyDataIO.Counties[Resident].getName());
                        //System.out.println(Total_commuter);

                        /**
                         * If the county sits nears toronto
                         * Nearly 35% of the total population
                         *
                         * */

                        Expected_number_of_commuters =  (int) Math.round(Parameters.Work_force_ratio * 0.5 * Total_commuter * 0.4 * 10);
                        //System.out.println("Resident: " + Resident);
                        switch (CountyDataIO.Counties[Resident].getName()){
                            case "Hamilton":
                                Expected_number_of_commuters = 55365;
                                System.out.println(true);
                                break;
                            case "Oshawa":
                                Expected_number_of_commuters = 53320;
                                System.out.println(true);
                                break;
                            case "Barrie":
                                Expected_number_of_commuters = 22105;
                                System.out.println(true);
                                break;
                            default:
                                break;
                        }
                    }

                    //System.out.print(CountyDataIO.Counties[Resident].getName() + "," + Tier + ",");
                    //System.out.println(Total_commuter);


                    Commute_Matrix[Resident][Work] = Expected_number_of_commuters;
                    Exportations[Resident] += Expected_number_of_commuters;
                    Reversed_Commute_Matrix[Work][Resident] = Expected_number_of_commuters;
                    Importations[Work] += Expected_number_of_commuters;

                }
            }
        }
        Generated_Matrix = Commute_Matrix;
        Generated_Exportations = Exportations;
    }

    public static void main(String[] args) {
        CountyDataIO c = new CountyDataIO();
        getStaticCommuteMatrix();
    }

    public double[][] Commute(double[] Tier, double[][] StratificationByCouty){
        int CountyCount = CountyDataIO.Counties.length;
        double[][] CommuteFromTo = new double[CountyCount][CountyCount];
        for (int i = 0; i < CountyCount; i++) {
            for (int i1 = 0; i1 < CountyCount; i1++) {
                double Distance = CountyDataIO.DistanceBetweenCounties[i][i1];
                int CaseFlux;

            }
        }
        return new double[1][1];
    }

    public double[] FindCoutiesWithinRange(double[] StartCoord, int InitialCounty){
        double[] Distances = CountyDataIO.DistanceBetweenCounties[InitialCounty];
        double[] Exported = new double[CountyDataIO.Counties.length];
        ArrayList<Integer> CountyWithinRange = new ArrayList<>();

        for (int i = 0; i < CountyDataIO.Counties.length; i++) {
            if(Distances[i]<150){
                CountyWithinRange.add(i);
            }
        }

        double[] Relative_commuters = new double[CountyWithinRange.size()];

        for (int DestinationCounty = 0; DestinationCounty < CountyWithinRange.size(); DestinationCounty++) {
            double Population = CountyDataIO.Counties[DestinationCounty].getPopulation();
            double Distance = CountyDataIO.DistanceBetweenCounties[InitialCounty][CountyWithinRange.get(DestinationCounty)];
            double Weight = getWeight(InitialCounty, DestinationCounty, Distance);
            Relative_commuters[DestinationCounty] = Weight;
        }

        Relative_commuters = Function.Normalization(Relative_commuters);

        return Exported;
    }

    public double getWeight(int InitialCounty, int DestinationCounty, double Distance){

        double Weight = 0;

        /**
         * Calculate the relative percentage of commuters
         */

        double Dist = 0;

        for (int i = 0; i < 20; i++) {
            Dist += GammaDist.getGammaFunction(Parameters.MedianDistance_of_Travel_for_Commute)[(int) Distance - 10 + i];
        }

        double DestinationPopulation = CountyDataIO.Counties[DestinationCounty].getPopulation();

        Weight = Dist * DestinationPopulation;

        return Weight;
    }


    public static void GenerateWeeklyCommuteMatrixfromMutiplicatoin(int Tiers[]){
        /**
         * Stratify counties by distance to another city, for tiered
         * Will only be ran once to save computational power
         */

        int Number_of_Counties = CountyDataIO.Counties.length;

        Generated_Matrix = new int[Number_of_Counties][Number_of_Counties];
        Generated_Reversed_Commute_Matrix = new int[Number_of_Counties][Number_of_Counties];

        Generated_Exportations = new int[Number_of_Counties];
        Generated_Importations = new int[Number_of_Counties];

        for (int Resident = 0; Resident < Number_of_Counties; Resident++) {
            for (int Work = 0; Work < Number_of_Counties; Work++) {
                int Maximum_level = Math.max(Tiers[Work],Tiers[Resident]);
                double Workplace_lockdown_coefficient = Parameters.WorkPlaceReductionbyLevel[Maximum_level];
                int Number = (int) Math.round(Workplace_lockdown_coefficient * Commute_Matrix[Resident][Work]);
                Generated_Matrix[Resident][Work] = Number;
                Generated_Reversed_Commute_Matrix[Work][Resident] = Number;
                Generated_Exportations[Resident] = Number;
                Generated_Importations[Work] = Number;
            }
        }
    }


    public static ArrayList[] getCity_counties_within_commute_range_code() {
        return City_counties_within_commute_range_code;
    }

    public static ArrayList[] getCity_counties_within_commute_range_tier() {
        return City_counties_within_commute_range_tier;
    }

    public static ArrayList[] getRural_counties_within_commute_range_code() {
        return Rural_counties_within_commute_range_code;
    }

    public static ArrayList[] getRural_counties_within_commute_range_tier() {
        return Rural_counties_within_commute_range_tier;
    }

    public static boolean[][] getWithin_same_district_by_county() {
        return Within_same_district_by_county;
    }

    public static int[] getTotal_Population_of_potential_city_within_range() {
        return Total_Population_of_potential_city_within_range;
    }

    public static int[] getTotal_Population_of_potential_rural_within_range() {
        return Total_Population_of_potential_rural_within_range;
    }

    public static int[][] getCommute_distance_tier_by_county() {
        return Commute_distance_tier_by_county;
    }

    public static int[][] getCommute_distance_tier_by_county_city_subarray() {
        return Commute_distance_tier_by_county_city_subarray;
    }

    public static int[][] getCommute_distance_tier_by_county_rural_subarray() {
        return Commute_distance_tier_by_county_rural_subarray;
    }

    public static int[][] getTiered_Total_Population_of_potential_city_within_range() {
        return Tiered_Total_Population_of_potential_city_within_range;
    }

    public static int[][] getTiered_Total_Population_of_potential_rural_within_range() {
        return Tiered_Total_Population_of_potential_rural_within_range;
    }

    public static int[][] getCommute_Matrix() {
        return Commute_Matrix;
    }

    public static int[][] getReversed_Commute_Matrix() {
        return Reversed_Commute_Matrix;
    }

    public static int[][] getTotal_number_of_commuters() {
        return Total_number_of_commuters;
    }

    public static int[] getExportations() {
        return Exportations;
    }

    public static int[] getImportations() {
        return Importations;
    }

    public static int[][] getGenerated_Matrix() {
        return Generated_Matrix;
    }

    public static int[] getGenerated_Exportations() {
        return Generated_Exportations;
    }
}
