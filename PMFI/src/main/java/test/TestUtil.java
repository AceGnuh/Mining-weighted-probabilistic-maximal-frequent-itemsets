package test;

import pmfi.entities.UncertainDatabase;
import pmfi.pmfit.PMFIT;
import pmfi.utils.DatasetUtil;

import java.nio.file.Paths;

public class TestUtil {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\pmfi\\datasets\\";
    private static Runtime runtime = Runtime.getRuntime();
    public static void testMinSupport(String nameDataset, double mean, double variance, double minSupport, double minConfidence){
        String pathDataset = path + nameDataset;

        //read dataset
        DatasetUtil datasetUtil = new DatasetUtil(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = datasetUtil.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = datasetUtil.getUncertainDatabase().getUncertainDatabase().size();

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

    public static void testMinConfidence(String nameDataset, double mean, double variance, double minRelativeSupport, double minConfidence){
        String pathDataset = path + nameDataset;

        //read dataset
        DatasetUtil datasetUtil = new DatasetUtil(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = datasetUtil.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = datasetUtil.getUncertainDatabase().getUncertainDatabase().size();
        double minSupport = lengthDb * minRelativeSupport;

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
