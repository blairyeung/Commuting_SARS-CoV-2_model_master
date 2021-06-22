public class CountyData {
    private int ID;
    private String Name;
    private int District;
    private double[] Coordinate;
    private int Population;
    private int County_Type;
    private int County_Category;/**Larger CMA, CA, or MIZ*/

    /**
     * @param Name
     * @param District
     * @param Coordinate
     * @param Population
     * @param County_Type
     */


    /**Store the demographic information of each county*/

    public CountyData(String Name, int District, double[] Coordinate, int Population, int County_Type){
        this.Name = Name;/**Name_of_the_county*/
        this.District = District;/**Code_of_the_distrizct*/
        this.Coordinate = Coordinate;/**Geographical_coordinate*/
        this.Population = Population;/**Total_population*/
        this.County_Type = County_Type;/**County_of_the_county: urban/rural*/
    }

    public CountyData(int County_ID,String Name, int District, double[] Coordinate, int Population, int County_Type){
        this.ID = County_ID;
        this.Name = Name;/**Name_of_the_county*/
        this.District = District;/**Code_of_the_distrizct*/
        this.Coordinate = Coordinate;/**Geographical_coordinate*/
        this.Population = Population;/**Total_population*/
        this.County_Type = County_Type;/**County_of_the_county: urban/rural*/
    }

    public CountyData(int County_ID, String Name, int District, double[] Coordinate, int Population, int County_Type, int County_Cateogyry){
        this.Name = Name;/**Name_of_the_county*/
        this.District = District;/**Code_of_the_district*/
        this.Coordinate = Coordinate;/**Geographical_coordinate*/
        this.Population = Population;/**Total_population*/
        this.County_Type = County_Type;/**County_of_the_county: urban/rural*/
        this.County_Category = County_Cateogyry;/**Larger CMA, CA, or MIZ*/


    }

    public void setCounty_Category(int county_Category) {
        County_Category = county_Category;
    }

    public int getID() {
        return ID;
    }

    public String getName(){return Name;}
    public int getDistrict(){return District;}
    public double[] getCoordinate(){return Coordinate;}
    public int getPopulation(){return Population;}
    public int getCounty_Type(){return County_Type;}
    public int getCounty_Category() {
        return County_Category;
    }
}
