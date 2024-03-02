package test;

public class TestConnect4 {
    private static String nameDataset = "connect4_10K.data";
    private static String nameWeighted = "weighted_connect4_10K.data";
    private static double mean = 0.78;
    private static double variance = 0.65;

    //PMFITree: 1.2s, 447MB; APMFITree: 1s, 370MB (relative support 0.9, confidence: 0.0001)
    //PMFITree: 1.3s, 372MB; APMFITree: 1s, 371MB (relative support 0.9, confidence: 0.001)
    //PMFITree: 1s, 377MB; APMFITree: 1.2s, 371MB (relative support 0.9, confidence: 0.01)
    //PMFITree: 1s, 369MB ; APMFITree: 1s, 371MB (relative support 0.9, confidence: 0.1)
    //-----
    //PMFITree: 4s, 1050MB; APMFITree: 3.2s, 578MB (support 0.3, confidence: 0.6)
    //PMFITree: 3.3s, 981MB; APMFITree: 3.1s, 443MB (support 0.5, confidence: 0.6)
    //PMFITree: 3.4s, 1050MB; APMFITree: 2.9s, 773MB (support 0.7, confidence: 0.6)
    //PMFITree: 2.7s, 529MB; APMFITree: 2.7s, 684MB (support 0.9, confidence: 0.6)
    //-----
    /**
     * Run test min support and min confidence for dataset Connect4
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, nameWeighted, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run test min support and min confidence for dataset Connect4
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testApproximatePMFIT(double minRelativeSupport, double minConfidence){
        TestUtil.testApproximatePMFIT(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}