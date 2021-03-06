public class Main {
    public static int Day = 0;

    public static int Mode = 0;

    /**
     * Mode 0: Work, day time
     * Mode 1: Resident, night time
     */

    public static void main(String[] args) {

        /**
         * Read_file
         */
        CountyDataIO.CountyData_IO_Input();
        Commute_IO.Commute_IO_Input();
        IO.Combined_Input();

        Presets Preset = new Presets(0);
        Initial_Parameters Initial = Preset.getPreset(0);

        Trail Traildata = new Trail(0,1);

        Thread thread = new Thread();

        for (int i = 0; i < 10; i++) {
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

    }
}
