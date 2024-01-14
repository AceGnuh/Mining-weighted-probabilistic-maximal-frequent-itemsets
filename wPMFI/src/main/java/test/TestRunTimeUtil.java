package test;

public class TestRunTimeUtil {
    static double mean = 0.5;
    static double variance = 0.125;

    /**
     * Method run time of algorithm with min support and min confidence respectively
     * @param nameDataset
     * @param minSupportList
     * @param minConfidence
     * @param scaleFactor
     * @param isProbabilityModel
     * @return Run time of algorithm
     */
    public static int[][] getRunTimeList(String nameDataset, double[] minSupportList, double[] minConfidence, double scaleFactor, boolean isProbabilityModel){
        int[][] runTimeList = new int[minSupportList.length][minConfidence.length];

        for(int i = 0 ; i < minSupportList.length; i++){
            for(int j = 0; j < minConfidence.length; j++){
                runTimeList[i][j] = TestUtil.testMinSupport(nameDataset, mean, variance, minSupportList[i], minConfidence[j], scaleFactor, isProbabilityModel);

            }
        }

        return runTimeList;
    }
}
