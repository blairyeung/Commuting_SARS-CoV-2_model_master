import javax.swing.text.Segment;
import java.util.ArrayList;

public class Function {
    public static void main(String[] args) {
        for (int i = 0; i < 24; i++) {
            System.out.println(findGuassianBlur(Parameters.Travel_distance_distribution_full, i));
        }
    }
    public static double[] Normalization(double[] Array){
        double Sum = 0;
        for (int i = 0; i < Array.length; i++) {
            Sum += Array[i];
        }
        for (int i = 0; i < Array.length; i++) {
            if(Array[i]!=0){
                Array[i]/=Sum;
            }
        }
        return Array;
    }

    public static double[] addArrays(double Array1[], double Array2[]){
        if(Array1.length!=Array2.length){
            System.err.println("Error different array length");
            return Array1;
        }
        else {
            double Combined[] = new double[Array1.length];
            for (int i = 0; i < Array1.length; i++) {
                Combined[i] = Array1[i] + Array2[i];
            }
            return Combined;
        }
    }

    public static double findGuassianBlur(double[] Input, int Median){
        double[] GuassianArr = GuassianDist.GetGuassianDist(Median);
        double[] Output = new double[Input.length];
        double Sum = 0;
        for (int i = 0; i < Input.length; i++) {
            Output[i] = Input[i] * GuassianArr[i];
            Sum += Output[i];
        }
        return Sum;
    }

    public static int[] RandomAssign(int[] Probabilities, int Total){
        double Buffer[] = new double[Probabilities.length];
        for (int Index = 0; Index < Probabilities.length; Index++) {
            Buffer[Index] = Probabilities[Index];
        }
        return RandomAssignArr(Buffer, Total);
    }

    public static int RandomAssign(double[] Probabilities, int Total){
        int Return_index = 0;
        double Normalized_Probabilities[] = Normalization(Probabilities);
        double Generated_rad = Math.random();
        double Iterated = 0;
        for (int Index = 0; Index < Normalized_Probabilities.length; Index++) {
            if(Generated_rad>=Iterated){
                Return_index = Index;
                break;
            }
            Iterated += Normalized_Probabilities[Index];
        }



        return Return_index;
    }

    public static int[] RandomAssignArr(double[] Probabilities, int Total){

        /**
         * Efficiency optimization required
         */

        int[] Return_arr = new int[Probabilities.length];

        int Return_index = 0;
        double Normalized_Probabilities[] = Normalization(Probabilities);

        for (int i = 0; i < Total; i++) {
            double Generated_rad = Math.random();
            double Iterated = 0;

            /*for (int Index = 0; Index < Normalized_Probabilities.length; Index++) {
                //System.out.println(CountyDataIO.Counties[Index].getName() + Normalized_Probabilities[Index]);
            }*/

            for (int Index = 0; Index < Normalized_Probabilities.length; Index++) {
                //System.out.println(Normalized_Probabilities[Index]);
                Iterated += Normalized_Probabilities[Index];
                if(Generated_rad<=Iterated){
                    Return_index = Index;
                    break;
                }
                Iterated += Normalized_Probabilities[Index];
            }
            Return_arr[Return_index] += 1;
            //System.out.println(Return_index);
        }
        return Return_arr;
    }

    public static int Comma_count(String input){
        int count = 0;
        //String copy = input;
        while (input.contains(",")){
            input = input.substring(input.indexOf(",")+1);
            count++;
        }
        System.out.println(count);
        return count;
    }

    public static String[] Stratification(String input){
        /**
         * This will first detect the number of elements, takes longer but it is more flexible
         */
        return null;
    }


    public static String[] Stratification(String input, int length){
        /**
         * Overrides the previous one, no detection thus runs much faster
         */

        String[] returned = new String[length];

        for (int elm = 0; elm < length-1; elm++) {
            String Segment = input.substring(0,input.indexOf(","));
            input = input.substring(input.indexOf(",")+1);
            returned[elm] = Segment;
        }

        return returned;
    }
}
