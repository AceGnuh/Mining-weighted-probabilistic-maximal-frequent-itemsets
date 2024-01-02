package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.MyDataset;

import java.nio.file.Paths;

/**
 * Test dataset T40I10D100K with min support and min confidence
 */
public class TestT40I10D100K {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\pmfi\\datasets\\";

    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Run test for dataset T40I10D100k
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void run(double minSupport, double minConfidence){

        //-----
        //PMFITree: 13s, 500MB; APMFITree: 12s, 476MB (relative support 0.9, confidence: 0.1)
        //PMFITree: 14s, 510MB; APMFITree: 11s, 502MB (relative support 0.9, confidence: 0.01)
        //PMFITree: 12s, 625MB; APMFITree: 8s, 581MB (relative support 0.9, confidence: 0.001)
        //-----
        //PMFITree: 170s; 1132MB. APMFITree: 58s, 1458MB (support 0.1, confidence: 0.9)
        //PMFITree: 26s, 1289; APMFITree: 25s, 283MB (support 0.5, confidence: 0.9)
        //PMFITree: 23s, 1365MB; APMFITree: 25s, 1290MB (support 1, confidence: 0.9)

        String pathDataset = path + "T40I10D100K.txt";
        double mean = 0.79;
        double variance = 0.61;

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
