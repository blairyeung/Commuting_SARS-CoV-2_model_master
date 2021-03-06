import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CoordIO {

    public static CountyData[] Counties;
    public static ArrayList<CountyData>[] Counties_By_District;
    public static double[][] DistanceBetweenCounties;

    public static int[] DistrictCodes;
    public static ArrayList<Integer> DistrictCode = new ArrayList<>();

    public static void main(String[] args) {
        CoordIO C = new CoordIO();
    }
    public CoordIO(){
        String CoordPath = Parameters.ReadPath + "GeoCode.csv";
        File CoordFile = new File(CoordPath);
        Scanner CoordScan = null;

        try {
            CoordScan = new Scanner(CoordFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> CoordList = new ArrayList<>();
        ArrayList<String> Locations = new ArrayList<>();
        ArrayList<Double> Longitudes = new ArrayList<>();
        ArrayList<Double> Latitudes = new ArrayList<>();
        ArrayList<Integer> Populations = new ArrayList<>();
        ArrayList<Integer> Districts = new ArrayList<>();
        ArrayList<String> OrgCounty = new ArrayList<>();
        ArrayList<CountyData> CountyDataList = new ArrayList<>();


        CoordScan.nextLine();
        while (CoordScan.hasNext()) {
            String str = CoordScan.nextLine();
            System.out.println(str);
            CoordList.add(str);
        }

        for (int i = 0; i < CoordList.size(); i++) {
            String str = CoordList.get(i);
            String Location = str.substring(0,str.indexOf(","));
            str = str.substring(str.indexOf(",")+1);
            String Longti = str.substring(0,str.indexOf(","));
            double Longitude = Double.parseDouble(Longti);
            str = str.substring(str.indexOf(",")+1);
            String Lati = str.substring(0,str.indexOf(","));
            double Latitude = Double.parseDouble(Lati);
            str = str.substring(str.indexOf(",")+1);
            //System.out.println(str);
            int Population = Integer.parseInt(str.substring(0,str.indexOf(",")));
            str = str.substring(str.indexOf(",")+1);
            String District_Code = str.substring(0,str.indexOf(","));
            int District = Integer.parseInt(District_Code);
            String County_Type = str.substring(str.indexOf(",")+1);

            /**
             * Remove if population is 0
             */

            if (Population==0) {
                break;
            }

            int CountyType = 0;

            System.out.println("County_Type: " + County_Type);

            if(County_Type.equals("Town")||County_Type.equals("City")||County_Type.equals("Municipality")){
                CountyType = 1;
                System.out.println(false);
            }

            System.out.println("Location: " + Location + "  Longtitude: " + Longitude + "  Latitude: " + Latitude + "  Population: " + Population + "  District: " + District + "  County_Type: " + CountyType);

            Locations.add(Location);
            Longitudes.add(Longitude);
            Latitudes.add(Longitude);
            Populations.add(Population);
            Districts.add(District);
            OrgCounty.add(County_Type);

            double Coordinates[] = {Longitude,Latitude};

            CountyData c = new CountyData(Location,District, Coordinates, Population, CountyType);
            CountyDataList.add(c);

            /**Add Code*/

            if(!DistrictCode.contains(District)){
                DistrictCode.add(District);
            }

        }

        DistrictCodes = new int[DistrictCode.size()];
        Counties_By_District = new ArrayList[DistrictCode.size()];

        for (int i = 0; i < DistrictCode.size(); i++) {
            DistrictCodes[i] = DistrictCode.get(i);
            Counties_By_District[i] = new ArrayList<CountyData>();
        }

        Counties = ArrayListToArray.toArray(CountyDataList,Counties);

        for (int i1 = 0; i1 < Counties.length; i1++) {
            /**
             * Categorize counties by district
             */
            int ind = DistrictCode.indexOf(Counties[i1].getDistrict());
            Counties_By_District[ind].add(Counties[i1]);
        }

        /**
         * The List "Locations" will act as an menu/index
         */

        DistanceBetweenCounties = new double[Populations.size()][Populations.size()];

        for (int i = 0; i < Populations.size(); i++) {
            for (int i1 = 0; i1 < Populations.size(); i1++) {
                double distance;
                double Coord1[] = Counties[i].getCoordinate();
                double Coord2[] = Counties[i1].getCoordinate();

                //System.out.print("Depart: " + Counties[i].getName());

                //System.out.print("    Arrival: " + Counties[i1].getName());

                distance = CartesianDistance(Coord1,Coord2) * Parameters.Kilometers_per_degree;
                /**
                 * Kilometers_per_degree is a constant
                 */
                DistanceBetweenCounties[i][i1] = distance;

                //System.out.println("    Distnace: "+ distance);
            }
        }
        Commute.getStaticCommuteMatrix();
    }

    public double CartesianDistance(double[] Coord1, double[] Coord2){
        double a = (Coord1[0] - Coord2[0]);
        double b = (Coord1[1] - Coord2[1]);
        double distance =Math.pow((a*a+b*b),0.5);
        return distance;
    }
}
