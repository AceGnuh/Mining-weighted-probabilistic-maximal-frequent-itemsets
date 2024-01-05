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
    //PMFITree: 13s, 500MB; APMFITree: 12s, 476MB (relative support 0.9, confidence: 0.1)
    //PMFITree: 14s, 510MB; APMFITree: 11s, 502MB (relative support 0.9, confidence: 0.01)
    //PMFITree: 12s, 625MB; APMFITree: 8s, 581MB (relative support 0.9, confidence: 0.001)
    //-----
    //PMFITree: 170s; 1132MB. APMFITree: 58s, 1458MB (support 0.1, confidence: 0.9)
    //PMFITree: 26s, 1289; APMFITree: 25s, 283MB (support 0.5, confidence: 0.9)
    //PMFITree: 23s, 1365MB; APMFITree: 25s, 1290MB (support 1, confidence: 0.9)
    /**
     * Run test min support and min confidence for dataset T40I10D100K
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testMinimumSupport(double minSupport, double minConfidence){
        TestUtil.testMinSupport(nameDataset, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run test min support and min confidence for dataset T40I10D100K
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testMinimumConfidence(double minRelativeSupport, double minConfidence){
        TestUtil.testMinConfidence(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}
