package test;

import pmfi.entities.UncertainDatabase;
import pmfi.entities.WeightedTable;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.algorithms.AWPMFIT;

import pmfi.algorithms.WPMFIT;
import pmfi.utils.DatasetUtil;

import java.nio.file.Paths;
import java.util.*;

public class TestUtil {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\pmfi\\datasets\\";
    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Calculate run time of PMFI algorithm
     * @param nameDataset
     * @param mean
     * @param variance
     * @param minSupport
     * @param minConfidence
     */
    public static void testPMFI(String nameDataset, String nameWeighted, double mean, double variance, double minSupport, double minConfidence){
        String pathDataset = path + nameDataset + ".data";

        //read dataset
        DatasetUtil datasetUtil = new DatasetUtil(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = datasetUtil.getUncertainDatabase();
        //System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = datasetUtil.getUncertainDatabase().getUncertainDatabase().size();

        //set min support
        minSupport = minSupport * lengthDb;

        WeightedTable weightedTable = DatasetUtil.generateWeightedTable(uncertainDatabase);
        //System.out.println(weightedTable);

        //run algorithms
        ProbabilisticMaximalFrequentItemsetTree pmfit = new WPMFIT(uncertainDatabase, weightedTable, minSupport, minConfidence);

        //get time at start algorithm
        long start = System.nanoTime();

        Set<List> result =  pmfit.findAllWPMFI();
        System.out.println("Weighted probabilistic maximal frequent itemset : " + result);
//        List inputItemset = new ArrayList(List.of(76, 100, 85, 31, 49));
//        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItemset);
//
//        ProbabilisticFrequentItemset probabilisticFrequentItemset = new ProbabilisticFrequentItemset(uncertainDatabase, weightedTable, inputItemset);
//        System.out.println("Min confidence " + probabilisticFrequentItemset.calculateProbabilisticSupport(minConfidence));


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

    /**
     * Calculate run time of Approximate PMFI algorithm
     * @param nameDataset
     * @param mean
     * @param variance
     * @param minSupport
     * @param minConfidence
     */
    public static void testApproximatePMFIT(String nameDataset, double mean, double variance, double minSupport, double minConfidence){
        String pathDataset = path + nameDataset + ".data";

        //read dataset
        DatasetUtil datasetUtil = new DatasetUtil(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = datasetUtil.getUncertainDatabase();
        //System.out.println(uncertainDatabase);

        WeightedTable weightedTable = DatasetUtil.generateWeightedTable(uncertainDatabase);
        //System.out.println(weightedTable);

        //the number of transaction in database
        int lengthDb = datasetUtil.getUncertainDatabase().getUncertainDatabase().size();
        minSupport = lengthDb * minSupport;

        //run algorithms
        ProbabilisticMaximalFrequentItemsetTree pmfit = new AWPMFIT(uncertainDatabase, weightedTable, minSupport, minConfidence);

        //get time at start algorithm
        long start = System.nanoTime();

        System.out.println("Approximate weighted probabilistic maximal frequent itemset");
        System.out.println(pmfit.findAllWPMFI());

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
