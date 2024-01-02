package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.MyDataset;

import java.nio.file.Paths;

public class TestConnect4 {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\pmfi\\datasets\\";

    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Run test for dataset Connect4
     * @param minSupport minimum support
     * @param minConfidence minimum confidence
     */
    public static void run(double minSupport, double minConfidence){

        //PMFITree: 1.2s, 447MB; APMFITree: 1s, 370MB (relative support 0.9, confidence: 0.0001)
        //PMFITree: 1.3s, 372MB; APMFITree: 1s, 371MB (relative support 0.9, confidence: 0.001)
        //PMFITree: 1s, 377MB; APMFITree: 1.2s, 371MB (relative support 0.9, confidence: 0.01)
        //PMFITree: 1s, 369MB ; APMFITree: 1s, 371MB (relative support 0.9, confidence: 0.1)
        //-----
        //PMFITree: 4s, 1050MB; APMFITree: 3.2s, 578MB (support 0.3, confidence: 0.9)
        //PMFITree: 3.3s, 981MB; APMFITree: 3.1s, 443MB (support 0.5, confidence: 0.9)
        //PMFITree: 3.4s, 1050MB; APMFITree: 2.9s, 773MB (support 0.7, confidence: 0.9)
        //PMFITree: 2.7s, 529MB; APMFITree: 2.7s, 684MB (support 0.9, confidence: 0.9)
        //-----

        String pathDataset = path + "connect4.txt";
        double mean = 0.78;
        double variance = 0.65;

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
