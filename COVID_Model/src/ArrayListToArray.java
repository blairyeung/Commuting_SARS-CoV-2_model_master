import java.util.ArrayList;

public class ArrayListToArray {
    public static CountyData[] toArray(ArrayList<CountyData> Input, CountyData[] Output){
        Output = new CountyData[Input.size()];
        for (int i = 0; i < Output.length; i++) {
            Output[i] = Input.get(i);
        }
        return Output;
    }
}
