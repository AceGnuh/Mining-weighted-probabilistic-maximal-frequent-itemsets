package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.DatasetUtil;

import java.nio.file.Paths;

public class TestAccidents {
    private static String nameDataset = "accidents.txt";
    private static double mean = 0.5;
    private static double variance = 0.58;

    //PMFITree: 17s, 1525MB; APMFITree: 17s, 1608MB (relative support 0.9, confidence: 0.00001)
    //PMFITree: 18s, 1835MB; APMFITree: 16s, 1348MB (relative support 0.9, confidence: 0.0001)
    //PMFITree: 17s, 1384MB; APMFITree: 16s, 1766MB (relative support 0.9, confidence: 0.001)
    //PMFITree: 20s, 1526MB; APMFITree: 16s, 1349MB (relative support 0.9, confidence: 0.01)
    //PMFITree: 17s, 1446MB; APMFITree: 19s, 1539MB (relative support 0.9, confidence: 0.1)
    //-----
    //PMFITree: 800s, 1900MB; APMFITree: 247s, 3110MB (support 0.08, confidence: 0.9)
    //PMFITree: 754s, 3352MB; APMFITree: 196s, 3282MB (support 0.1, confidence: 0.9)
    //PMFITree: 53s, 2419MB ; APMFITree: 49s, 2758MB (support 0.2, confidence: 0.9)
    //PMFITree: 49s, 2419MB ; APMFITree: 43s, 1630MB (support 0.5, confidence: 0.9)
    //PMFITree: 45s, 2080MB; APMFITree: 40s, 2599MB (support 0.9, confidence: 0.9)
    //PMFITree: 37s, 2751MB; APMFITree: 33s, 2079MB (support 1, confidence: 0.9)
    //-----
    /**
     * Run Algorithm to find all PMFIT
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void testPMFI(double minSupport, double minConfidence){
        TestUtil.testPMFI(nameDataset, mean, variance, minSupport, minConfidence);
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
