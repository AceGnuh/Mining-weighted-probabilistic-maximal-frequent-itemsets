package test;

/**
 * Test dataset T40I10D100K with min support and min confidence
 */
public class TestT40I10D100K {
    private static String nameDataset = "T40I10D100K_10K.data";
    private static String nameWeighted = "accidents.txt";
    private static double mean = 0.79;
    private static double variance = 0.61;

    //-----
    //PMFITree: 2s, ; APMFITree: 1.7s,  (relative support 0.1, confidence: 0.9)
    //PMFITree: 2s, ; APMFITree: s,  (relative support 0.1, confidence: 0.7)
    //PMFITree: 3s, ; APMFITree: s,  (relative support 0.1, confidence: 0.5)
    //PMFITree: 4s, ; APMFITree: s,  (relative support 0.1, confidence: 0.3)
    //PMFITree: 5s, ; APMFITree: s,  (relative support 0.1, confidence: 0.1)
    //-----
    //PMFITree: 1s; MB. APMFITree: s, MB (support 0.35, confidence: 0.6)
    //PMFITree: 1s; MB. APMFITree: s, MB (support 0.30, confidence: 0.6)
    //PMFITree: 1s; MB. APMFITree: s, MB (support 0.25, confidence: 0.6)
    //PMFITree: 1s, MB; APMFITree: s, MB (support 0.20, confidence: 0.6)
    //PMFITree: 2s, MB; APMFITree: s, MB (support 0.15, confidence: 0.6)
    //PMFITree: 4s, MB; APMFITree: s, MB (support 0.10, confidence: 0.6)
    /**
     * Run test min support and min confidence for dataset T40I10D100K
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, nameWeighted, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run test min support and min confidence for dataset T40I10D100K
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testApproximatePMFIT(double minRelativeSupport, double minConfidence){
        TestUtil.testApproximatePMFIT(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}
