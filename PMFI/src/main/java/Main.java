import pmfi.utils.MyDataset;
import pmfi.entities.*;
import pmfi.pmfit.ApproximatePMFIT;
import pmfi.entities.supports.SummedSupportProbabilisticVector;
import pmfi.pmfit.PMFIT;

import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static long MB = 1024 * 1024;
    private static long MILI_SECOND = 1000 * 1000;
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();

        String path = Paths.get("").toAbsolutePath().toString();
        path += "\\src\\main\\java\\pmfi\\datasets\\";

        //PMFITree: 13s; APMFITree: 6s (relative support 0.9, confidence: 0.1)
        //PMFITree: 14s; APMFITree: 6s (relative support 0.9, confidence: 0.01)
        //PMFITree: 12.7s; APMFITree: 6s (relative support 0.9, confidence: 0.01)
        String pathDataset = path + "T40I10D100K.txt";  //PMFITree: 124s; 503MB. APMFITree: 14s (support 0.1, confidence: 0.9)
        double mean = 0.79;                             //PMFITree: 8s; APMFITree: 6s (support 1, confidence: 0.9)
        double variance = 0.61;                         //PMFITree: 8s; APMFITree: 6s (support 0.5, confidence: 0.9)


        //PMFITree: 154ss; APMFITree: 15s (support 0.1, confidence: 0.9)
        //PMFITree: 154ss; APMFITree: 15s (support 0.5, confidence: 0.9)
        //PMFITree: 1.8; APMFITree: 1.2s (relative support 0.9, confidence: 0.1)
        //PMFITree: 1.7; APMFITree: 1.2s (relative support 0.9, confidence: 0.01)
//        String pathDataset = path + "connect4.txt";
//        double mean = 0.78;
//        double variance = 0.65;



//        String pathDataset = path + "accidents.txt";  //PMFITree: 653; APMFITree: 152s (support 0.1, confidence: 0.9)
//        double mean = 0.5;                            //PMFITree: 37; APMFITree: 30s (support 1, confidence: 0.9)
//        double variance = 0.58;



//        String pathDataset = path + "GAZELLE.txt";  //PMFITree: 3; APMFITree: 1.5s  (support 0.1, confidence: 0.9)
//        double mean = 0.94;                         //PMFITree: ; APMFITree:  (support 0.01, confidence: 0.9)
//        double variance = 0.08;

        MyDataset myDataset = new MyDataset(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = myDataset.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //the number of transaction in database
        int lengthDb = myDataset.getUncertainDatabase().getUncertainTransactions().size();

        double minSupport = 0.9 * lengthDb;
        double minConfidence = 0.001;

        //run algorithms
        PMFIT pmfit = new PMFIT(uncertainDatabase, minSupport, minConfidence);

        //get time and memory at start algorithm
        long start = System.nanoTime();
        long memoryTotal = runtime.totalMemory();

        pmfit.findAllPMFI();


        //example1();
        //example2();

        //exampleApproximatePMFIT();

        System.out.println();

        // get time and memory at end algorithm
        long end = System.nanoTime();
        long memoryFree = runtime.freeMemory();

        // execution time in seconds
        long execution = (end - start);
        long memoryUsage = Math.abs(memoryTotal - memoryFree);

        System.out.println("Execution time of Program is");
        System.out.println(execution / MILI_SECOND + " milliseconds");
        System.out.println("Memory usage: ");
        System.out.println(memoryUsage / MB + " MB");
    }


    public static void example1(){

        Map<String, Double> map1 = new HashMap<>();
        map1.put("A", 0.6);
        map1.put("B", 0.7);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("A", 0.2);
        map2.put("C", 0.3);

//        Map<Integer, Double> map1 = new HashMap<>();
//        map1.put(1, 0.6);
//        map1.put(2, 0.7);
//
//        Map<Integer, Double> map2 = new HashMap<>();
//        map2.put(1, 0.2);
//        map2.put(3, 0.3);

        UncertainTransaction transaction1
                = new UncertainTransaction(1, map1);
        UncertainTransaction transaction2
                = new UncertainTransaction(2, map2);

        //create uncertain database
        UncertainDatabase uncertainDatabase
                = new UncertainDatabase(new ArrayList<>(
                List.of(
                        transaction1,
                        transaction2
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        int minimumSupport = 1;
        double minProbabilisticConfidence = 0.1;
        List inputItem = new ArrayList(Arrays.asList("A"));

        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItem);
        System.out.println(Arrays.toString(summedSupportProbabilisticVector.getSummedSupportProbabilisticVector()));

        PMFIT pmfit = new PMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree: " + pmfit.findAllPMFI());

    }


    public static void example2(){
        Map<String, Double> map1 = new HashMap<>();
        map1.put("A", 0.5);
        map1.put("B", 0.7);
        map1.put("D", 0.8);
        map1.put("E", 0.9);
        UncertainTransaction transaction1
                = new UncertainTransaction(1, map1);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("B", 0.6);
        map2.put("C", 0.8);
        map2.put("D", 0.6);
        map2.put("E", 0.8);
        UncertainTransaction transaction2
                = new UncertainTransaction(2, map2);

        Map<String, Double> map3 = new HashMap<>();
        map3.put("C", 0.6);
        map3.put("D", 0.9);
        map3.put("E", 0.5);
        UncertainTransaction transaction3
                = new UncertainTransaction(3, map3);

        Map<String, Double> map4 = new HashMap<>();
        map4.put("A", 0.6);
        map4.put("C", 0.7);
        map4.put("D", 0.8);
        map4.put("E", 0.8);
        UncertainTransaction transaction4
                = new UncertainTransaction(4, map4);

        Map<String, Double> map5 = new HashMap<>();
        map5.put("A", 0.8);
        map5.put("B", 0.9);
        map5.put("C", 0.5);
        map5.put("D", 0.6);
        map5.put("E", 0.7);

        UncertainTransaction transaction5
                = new UncertainTransaction(5, map5);

        Map<String, Double> map6 = new HashMap<>();
        map6.put("B", 0.6);
        map6.put("D", 0.9);
        map6.put("E", 0.8);
        UncertainTransaction transaction6
                = new UncertainTransaction(6, map6);

        UncertainDatabase uncertainDatabase
                = new UncertainDatabase(new ArrayList<>(
                Arrays.asList(
                        transaction1,
                        transaction2,
                        transaction3,
                        transaction4,
                        transaction5,
                        transaction6
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        int minimumSupport = 3;
        double minProbabilisticConfidence = 0.1;

        PMFIT pmfit = new PMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllPMFI());

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
        pmfit.preOrder();
    }

    public static void exampleApproximatePMFIT(){
        Map<String, Double> map1 = new HashMap<>();
        map1.put("A", 0.5);
        map1.put("B", 0.7);
        map1.put("D", 0.8);
        map1.put("E", 0.9);
        UncertainTransaction transaction1
                = new UncertainTransaction(1, map1);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("B", 0.6);
        map2.put("C", 0.8);
        map2.put("D", 0.6);
        map2.put("E", 0.8);
        UncertainTransaction transaction2
                = new UncertainTransaction(2, map2);

        Map<String, Double> map3 = new HashMap<>();
        map3.put("C", 0.6);
        map3.put("D", 0.9);
        map3.put("E", 0.5);
        UncertainTransaction transaction3
                = new UncertainTransaction(3, map3);

        Map<String, Double> map4 = new HashMap<>();
        map4.put("A", 0.6);
        map4.put("C", 0.7);
        map4.put("D", 0.8);
        map4.put("E", 0.8);
        UncertainTransaction transaction4
                = new UncertainTransaction(4, map4);

        Map<String, Double> map5 = new HashMap<>();
        map5.put("A", 0.8);
        map5.put("B", 0.9);
        map5.put("C", 0.5);
        map5.put("D", 0.6);
        map5.put("E", 0.7);

        UncertainTransaction transaction5
                = new UncertainTransaction(5, map5);

        Map<String, Double> map6 = new HashMap<>();
        map6.put("B", 0.6);
        map6.put("D", 0.9);
        map6.put("E", 0.8);
        UncertainTransaction transaction6
                = new UncertainTransaction(6, map6);

        UncertainDatabase uncertainDatabase
                = new UncertainDatabase(new ArrayList<>(
                Arrays.asList(
                        transaction1,
                        transaction2,
                        transaction3,
                        transaction4,
                        transaction5,
                        transaction6
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        int minimumSupport = 3;
        double minProbabilisticConfidence = 0.1;

        ApproximatePMFIT pmfit = new ApproximatePMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllPMFI());

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
        pmfit.preOrder();
    }


}