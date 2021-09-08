public class CountyDataArray {

    /**
     * The place where all the data were stored
     */

    private Data TimeSeries[];
    private String CountyName;

    public CountyDataArray(String Name){
        this.CountyName = Name;
        TimeSeries = new Data[Parameters.Observation_Range];
    }

    public CountyDataArray() {
        TimeSeries = new Data[Parameters.Observation_Range];
        for (int Date = 0; Date < Parameters.Observation_Range; Date++) {
            TimeSeries[Date] = new Data();
        }
    }

    public void bindTimeSeries(Data CountyData){
        //System.out.println("Going to bind");
        //ConsolePrint.PrintCountyInformation(CountyData);
        TimeSeries[Main.Day] = CountyData;
    }

    public Data[] getTimeSeries(){
        return TimeSeries;
    }
    public void setTimeSeries(Data[] Series){
        this.TimeSeries = Series;
    }
    public void setDataWithinSeries(int day,Data Data){
        this.TimeSeries[day] = Data;
    }
}
