package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.DatasetUtil;

import java.nio.file.Paths;

/**
 * Run test dataset Gazelle
 */
public class TestGAZELLE {
    private static String nameDataset = "GAZELLE.txt";
    private static double mean = 0.94;
    private static double variance = 0.08;

    //PMFITree: 2s, 44MB; APMFITree: 2.2s, 44MB  (relative support 0.9, confidence: 0.1)
    //PMFITree: 1.5s, 53MB; APMFITree: 1.6s, 46MB  (relative support 0.9, confidence: 0.01)
    //PMFITree: 1.4s, 49MB; APMFITree: 1.4s, 46MB  (relative support 0.9, confidence: 0.001)
    //PMFITree: 1.6s, 46MB; APMFITree: 1.5s, 58MBs  (relative support 0.9, confidence: 0.0001)
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
    public static void testMinimumSupport(double minSupport, double minConfidence){
        TestUtil.testMinSupport(nameDataset, mean, variance, minSupport, minConfidence);
    }

    /**
     * Run test min support and min confidence for dataset GAZELLE
     * @param minRelativeSupport minimum relative support
     * @param minConfidence minimum confidence
     */
    public static void testMinimumConfidence(double minRelativeSupport, double minConfidence){
        TestUtil.testMinConfidence(nameDataset, mean, variance, minRelativeSupport, minConfidence);
    }
}
