package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.MyDataset;

import java.nio.file.Paths;

public class TestAccidents {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\pmfi\\datasets\\";

    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Run test for dataset Accidents
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void run(double minSupport, double minConfidence){

        //PMFITree: 17s, 1525MB; APMFITree: 17s, 1608MB (relative support 0.9, confidence: 0.00001)
        //PMFITree: 18s, 1835MB; APMFITree: 16s, 1348MB (relative support 0.9, confidence: 0.0001)
        //PMFITree: 17s, 1384MB; APMFITree: 16s, 1766MB (relative support 0.9, confidence: 0.001)
        //PMFITree: 20s, 1526MB; APMFITree: 16s, 1349MB (relative support 0.9, confidence: 0.01)
        //PMFITree: 17s, 1446MB; APMFITree: 19s, 1539MB (relative support 0.9, confidence: 0.1)
        //-----
        //PMFITree: 800s, 1900MB; APMFITree: 247s, 3110MB (support 0.08, confidence: 0.9)
        //PMFITree: 780s, 3352MB; APMFITree: 196s, 3282MB (support 0.1, confidence: 0.9)
        //PMFITree: 53s, 2419MB ; APMFITree: 49s, 2758MB (support 0.2, confidence: 0.9)
        //PMFITree: 49s, 2419MB ; APMFITree: 43s, 1630MB (support 0.5, confidence: 0.9)
        //PMFITree: 45s, 2080MB; APMFITree: 40s, 2599MB (support 0.9, confidence: 0.9)
        //PMFITree: 37s, 2751MB; APMFITree: 33s, 2079MB (support 1, confidence: 0.9)
        //-----

        String pathDataset = path + "accidents.txt";
        double mean = 0.5;
        double variance = 0.58;

        MyDataset myDataset = new MyDataset(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = myDataset.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = myDataset.getUncertainDatabase().getUncertainTransactions().size();

//        double minSupport = 0.7;
//        double minConfidence = 0.9;

        //run algorithms
        PMFIT pmfit = new PMFIT(uncertainDatabase, minSupport, minConfidence);

        //get time at start algorithm
        long start = System.nanoTime();

        pmfit.findAllPMFI();

        //memory when we run algorithm
        long memoryTotal = runtime.totalMemory();
        long memoryFree = runtime.freeMemory();

        System.out.println();

        // get time at end algorithm
        long end = System.nanoTime();

        // execution time in seconds
        long execution = end - start;

        //calculate memory usage
        long memoryUsage = memoryTotal - memoryFree;

        System.out.println("Execution time of Program is");
        System.out.println(execution / MILI_SECOND + " milliseconds");
        System.out.println("Memory usage: ");
        System.out.println(memoryUsage / MB + " MB");
    }
}
