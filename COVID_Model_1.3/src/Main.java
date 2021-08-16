public class Main {
    public static int Day = 0;

    public static int Mode = 0;

    /**
     * Mode 0: Work, day time
     * Mode 1: ResidJing Yan (Judy) Zhu is inviting you to a scheduled Zoom meeting.
     *
     * Topic: Jing Yan (Judy) Zhu's Personal Meeting Room
     *
     * Join Zoom Meeting
     * https://westernuniversity.zoom.us/j/5364147796?pwd=bXVKS2MrallmejliU1RDc25wUHJudz09
     *
     * Meeting ID: 536 414 7796
     * Passcode: PC2Huw
     * One tap mobile
     * +16699006833,,5364147796#,,,,*110292# US (San Jose)
     * +19292056099,,5364147796#,,,,*110292# US (New York)
     *
     * Dial by your location
     *         +1 669 900 6833 US (San Jose)
     *         +1 929 205 6099 US (New York)
     * Meeting ID: 536 414 7796
     * Passcode: 110292
     * Find your local number: https://westernuniversity.zoom.us/u/aeSfvsZIh
     *
     * Join by SIP
     * 5364147796@zoomcrc.com
     *
     * Join by H.323
     * 162.255.37.11 (US West)
     * 162.255.36.11 (US East)
     * 69.174.57.160 (Canada Toronto)
     * 65.39.152.160 (Canada Vancouver)
     * Meeting ID: 536 414 7796
     * Passcode: 110292ent, night time
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
