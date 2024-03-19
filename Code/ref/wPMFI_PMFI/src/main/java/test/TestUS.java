package test;

/**
 * Run test dataset Gazelle
 */
public class TestUS {
    private static String nameDataset = "us_10K.data";
    private static String nameWeighted = "accidents.txt";
    private static double mean = 0.94;
    private static double variance = 0.08;

    //PMFITree: 260 s, 99 MB; APMFITree: 217 s, 274 MB  (relative support 0.9, confidence: 0.1)
    //PMFITree: 245 s, 132 MB; APMFITree: 165 s, 235 MB  (relative support 0.9, confidence: 0.3)
    //PMFITree: 214 s, 264 MB; APMFITree: 132 s, 203 MB  (relative support 0.9, confidence: 0.5)
    //PMFITree: 138 s, 281 MB; APMFITree: 53 s, 283 MB  (relative support 0.9, confidence: 0.7)
    //PMFITree: 29 s, 318 MB; APMFITree: 9 s, 186 MB  (relative support 0.9, confidence: 0.9)
    //-----
    //PMFITree: 3.5s, 105MB; APMFITree: 3s, 245MB  (support 1, confidence: 0.9)
    //PMFITree: 4s, 76MB; APMFITree: 3.3s, 309MB  (support 0.1, confidence: 0.9)
    //PMFITree: 5s, 144MB; APMFITree: 4s, 294MB  (support 0.01, confidence: 0.9)
    //-----
    /**
     * Run test min support and min confidence for dataset GAZELLE
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, nameWeighted, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run test min support and min confidence for dataset GAZELLE
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testApproximatePMFIT(double minRelativeSupport, double minConfidence){
        TestUtil.testApproximatePMFIT(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}
