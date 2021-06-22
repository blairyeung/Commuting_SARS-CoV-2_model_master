public class Main {
    public static int Day = 0;

    public static int Mode = 0;

    /**
     * Mode 0: Work, day time
     * Mode 1: Resident, night time
     */

    public static void main(String[] args) {

        CountyDataIO.CountyData_IO_Input();
        Commute_IO.Commute_IO_Input();
        IO dataio = new IO();

        Presets Preset = new Presets(0);
        Initial_Parameters Initial = Preset.getPreset(0);

        Trail Traildata = new Trail(0,1);

        //Province Ontario = new Province(Traildata);
        Thread thread = new Thread();


        //Province tries[] = new Province[10];
        //new Province(Traildata);
        //Ontario = new Province(Traildata);

        for (int i = 0; i < 1; i++) {
            Traildata = new Trail(0,i+1);
            Province Ontario = new Province(Traildata);
            for (int day = 0; day < 199; day++) {
                System.out.println("Date: " + day);
                Day = day;
                Ontario.ModelIterator();
            }
            Ontario.printToFile();
            //Ontario.printToConsole();
            Ontario.clear();
            System.gc();
        }

        /*for (int day = 0; day < 199; day++) {
            System.out.println("Date: " + day);
            Day = day;
            Ontario.ModelIterator();
        }
        Ontario.printToFile();*/

        /*for (int Trails = 0; Trails < 10; Trails++) {
            Traildata = new Trail(Trails,1);
            tries[Trails] = new Province(Traildata);
            //Ontario = new Province(Traildata);
            for (int day = 0; day < 199; day++) {
                System.out.println("Date: " + day);
                Day = day;
                Ontario.ModelIterator();
            }
            tries[Trails].printToFile();
            //Ontario.printToFile();
        }*/
    }
}
