package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.MyDataset;

import java.nio.file.Paths;

/**
 * Run test dataset Gazelle
 */
public class TestGAZELLE {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\pmfi\\datasets\\";

    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Run test for dataset Gazelle
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void run(double minSupport, double minConfidence){

        //PMFITree: 2s, 44MB; APMFITree: 2.2s, 44MB  (relative support 0.9, confidence: 0.1)
        //PMFITree: 1.5s, 53MB; APMFITree: 1.6s, 46MB  (relative support 0.9, confidence: 0.01)
        //PMFITree: 1.4s, 49MB; APMFITree: 1.4s, 46MB  (relative support 0.9, confidence: 0.001)
        //PMFITree: 1.6s, 46MB; APMFITree: 1.5s, 58MBs  (relative support 0.9, confidence: 0.0001)
        //-----
        //PMFITree: 3.5s, 105MB; APMFITree: 3s, 245MB  (support 1, confidence: 0.9)
        //PMFITree: 4s, 76MB; APMFITree: 3.3s, 309MB  (support 0.1, confidence: 0.9)
        //PMFITree: 5s, 144MB; APMFITree: 4s, 294MB  (support 0.01, confidence: 0.9)
        //-----

        String pathDataset = path + "GAZELLE.txt";
        double mean = 0.94;
        double variance = 0.08;

        MyDataset myDataset = new MyDataset(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = myDataset.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = myDataset.getUncertainDatabase().getUncertainTransactions().size();

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
