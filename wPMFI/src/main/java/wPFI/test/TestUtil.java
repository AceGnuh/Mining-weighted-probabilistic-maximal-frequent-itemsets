package wPFI.test;

import wPFI.algorithm.AlgoW_PFI_Apriori;
import wPFI.entities.UncertainDatabase;
import wPFI.entities.WeightedTable;
import wPFI.utils.DatasetUtil;

import java.nio.file.Paths;
import java.util.Set;

public class TestUtil {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    private static String path = Paths.get("").toAbsolutePath() + "\\src\\main\\java\\wPFI\\datasets\\";
    private static Runtime runtime = Runtime.getRuntime();

    /**
     * Method run algo and return time run algorithm (ms)
     * @param nameDataset
     * @param mean
     * @param variance
     * @param minSupport
     * @param minConfidence
     * @param scaleFactor
     * @return time run algorithm (ms)
     */
    public static int testMinSupport(String nameDataset, double mean, double variance, double minSupport, double minConfidence, double scaleFactor, boolean isProbabilityModel){
        String pathDataset = path + nameDataset;

        //read dataset
        DatasetUtil datasetUtil = new DatasetUtil(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = datasetUtil.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        WeightedTable weightedTable = DatasetUtil.generateWeightedTable(uncertainDatabase);
        //System.out.println(weightedTable);

        //get length Db
        int lengthDb = datasetUtil.getUncertainDatabase().getUncertainDatabase().size();
        minSupport = lengthDb * minSupport;

        //run algorithms
        AlgoW_PFI_Apriori algoWPfiApriori = new AlgoW_PFI_Apriori(uncertainDatabase, weightedTable, minSupport, minConfidence);

        //get time at start algorithm
        long start = System.nanoTime();

        Set<Set<String>> result = algoWPfiApriori.runAlgorithm(scaleFactor, isProbabilityModel);
        System.out.println(result);

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

        return (int) (execution / MILI_SECOND);
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
        //ProbabilisticMaximalFrequentItemsetTree pmfit = new ApproximatePMFIT(uncertainDatabase, minSupport, minConfidence);

        //get time at start algorithm
        long start = System.nanoTime();

        //pmfit.findAllPMFI();

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
