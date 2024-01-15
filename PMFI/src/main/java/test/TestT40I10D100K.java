package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.DatasetUtil;

import java.nio.file.Paths;

/**
 * Test dataset T40I10D100K with min support and min confidence
 */
public class TestT40I10D100K {
    private static String nameDataset = "T40I10D100K.txt";
    private static double mean = 0.79;
    private static double variance = 0.61;

    //-----
    //PMFITree: 7s, 500MB; APMFITree: 7s, 476MB (relative support 0.9, confidence: 0.1)
    //PMFITree: 7s, 510MB; APMFITree: 7s, 502MB (relative support 0.9, confidence: 0.01)
    //PMFITree: 7s, 625MB; APMFITree: 7s, 581MB (relative support 0.9, confidence: 0.001)
    //-----
    //PMFITree: 393s; 1132MB. APMFITree: 359s, 1319MB (support 0.05, confidence: 0.9)
    //PMFITree: 67s; 1132MB. APMFITree: 60s, 1319MB (support 0.07, confidence: 0.9)
    //PMFITree: 17s; 1132MB. APMFITree: 14s, 1458MB (support 0.1, confidence: 0.9)
    //PMFITree: 8s, 803MB; APMFITree: 7s, 283MB (support 0.5, confidence: 0.9)
    //PMFITree: 7s, 728MB; APMFITree: 7s, 1290MB (support 1, confidence: 0.9)
    /**
     * Run test min support and min confidence for dataset T40I10D100K
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, mean, variance, minSupport, minConfidence);
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
