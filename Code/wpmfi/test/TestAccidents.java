package test;

public class TestAccidents {
    private static String nameDataset = "accidents_10K.data";
    private static String nameWeighted = "accidents.txt";
    private static double mean = 0.5;
    private static double variance = 0.58;

    //PMFITree:   3s, ; APMFITree:  (relative support 0.1, confidence: 0.9)
    //PMFITree:  12s, ; APMFITree:  (relative support 0.1, confidence: 0.7)
    //PMFITree:  36s, ; APMFITree:  (relative support 0.1, confidence: 0.5)
    //PMFITree:  95s, ; APMFITree:  (relative support 0.1, confidence: 0.3)
    //PMFITree: 105s, ; APMFITree:  (relative support 0.1, confidence: 0.1)
    //-----
    //PMFITree:  6 s; 249 MB. APMFITree: 1.2 s, 255 MB (support 0.9, confidence: 0.6)
    //PMFITree:  50 s; 335 MB. APMFITree: 17 s, 143 MB (support 0.7, confidence: 0.6)
    //PMFITree:  86 s, 195 MB; APMFITree: 41 s, 86 MB (support 0.5, confidence: 0.6)
    //PMFITree:  95 s, 272 MB; APMFITree: 61 s, 196 MB (support 0.3, confidence: 0.6)
    //PMFITree:  108 s, 46 MB; APMFITree: 56 s, 216 MB (support 0.1, confidence: 0.6)
    /**
     * Run Algorithm to find all PMFIT
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, nameWeighted, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run Algorithm to find all ApproximatePMFIT
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testApproximatePMFIT(double minRelativeSupport, double minConfidence){
        TestUtil.testApproximatePMFIT(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}
