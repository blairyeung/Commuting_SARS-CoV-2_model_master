import java.sql.ClientInfoStatus;

public class Kernel {

    public static void main(String[] args) {
        double Ontario_Age_distribution[] = {2044603,2072100,2100865,2482802,2645240,2661723,2630680,2464247,2390116,2449915,2744896,2560241,2167275,1786622,1218303,811370,517710,248593,74476,11517};
        double Ontario_bias[] =    {0.93233476,0.9690367,0.909552598,0.83729059,0.944890746,1.004471513,1.040728868,1.070075112,1.007271233,0.934853606,0.942618458,1.151368848,1.271489248,1.386529457,1.629781421,1.689743487,1.889962568,2.51862702,3.335309895,2.855353005,0.2,0.2,0.2,0.2};

        double Calibrated[] = Gaussian_Kernel(Ontario_Age_distribution,5);
        double Smoothed[] = new double[Ontario_Age_distribution.length];

        double[] Smoothed_bias = Gaussian_Kernel(Ontario_bias,5);

        for (int i = 0; i < Calibrated.length; i++) {
            //System.out.println(Smoothed_bias[i]);
            System.out.println(Calibrated[i] * Smoothed_bias[i] * 5);
        }
        for (int i = 0; i < Smoothed.length; i++) {
            for (int i1 = 0; i1 < 5; i1++) {
                Smoothed[i]+=Calibrated[i1+5*i];
            }
            //System.out.println(Smoothed[i]);
        }
    }

    public static double[] Gaussian_Kernel(double[] Unsmoothed, int Smooth_level){
        double[] Output = new double[Unsmoothed.length*((int) ((double)Smooth_level*1.2))];
        for (int i = 0; i < Output.length; i++) {
            int center = i/Smooth_level;
            double calibrated_center = (((double) i)/((double) Smooth_level));
            double[] Calibration_array = GuassianDist.GetGaussianDist(calibrated_center,1, Unsmoothed.length);
            double Sum = 0;

            for (int i1 = 0; i1 < Calibration_array.length; i1++) {
                if(i1>Calibration_array.length-1){
                    Sum = Calibration_array[i1] * Unsmoothed[i1];
                }
                Sum += Calibration_array[i1] * Unsmoothed[i1];
            }

            Output[i] = Sum * 0.2;

            //System.out.println(calibrated_center);

        }
        return Output;
    }
}
