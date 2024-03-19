import wPFI.test.Test;
import wPFI.test.TestRunTimeUtil;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String nameDataset = "us_10K.data";
        String nameWeighted = "weighted_connect4_10K.data";
        double[] minimumSupport = { 0.1};
        double[] minimumConfidence = {0.9};
        double scaleFactor = 0.6;
        boolean isProbabilityModel = true;

        int[][] runTimes = TestRunTimeUtil.getRunTimeList(nameDataset, nameWeighted, minimumSupport, minimumConfidence, scaleFactor, isProbabilityModel);

        System.out.println();
        System.out.println("Min Support: ");
        System.out.println(Arrays.toString(minimumSupport));
        System.out.println();
        System.out.println("Run Time: ");
        for(int i = 0; i < runTimes.length; i++){
            System.out.println(Arrays.toString(runTimes[i]));
        }

//        Test.example1();
//        Test.example2();

    }

}