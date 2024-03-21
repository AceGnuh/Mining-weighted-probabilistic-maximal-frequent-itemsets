package test;

import pmfi.algorithms.AWPMFIT;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;
import pmfi.entities.WeightedTable;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.algorithms.WPMFIT;
import pmfi.supports.FrequentItemset;


import java.util.*;

/**
 * Run example in paper
 */
public class Test {

    /**
     * Run example 1 in paper with min support 1, min confidence 0.1
     */
    public static void example1(){

        Map<String, Double> map1 = new HashMap<>();
        map1.put("A", 0.6);
        map1.put("B", 0.8);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("A", 0.7);
        map2.put("C", 0.2);

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

        Map<String, Double> weighted = new HashMap<>();
        weighted.put("A", 0.8);
        weighted.put("B", 0.5);
        weighted.put("C", 0.3);


        WeightedTable weightedTable = new WeightedTable(weighted);

        double minimumSupport = 1;
        double minProbabilisticConfidence = 0.1;
//        List inputItem = new ArrayList(List.of("A"));
//
//        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItem);
//        System.out.println(Arrays.toString(summedSupportProbabilisticVector.getSummedSupportProbabilisticVector()));

        ProbabilisticMaximalFrequentItemsetTree pmfit = new WPMFIT(uncertainDatabase, weightedTable, minimumSupport, minProbabilisticConfidence);
//
//        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree: " + pmfit.findAllWPMFI());
//
//        ////Probabilistic maximal frequent itemset tree
//        System.out.println();
//        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
//        pmfit.preOrder();
    }

    /**
     * Run example 2 in paper with min support 3, min confidence 0.1
     */
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

        Map<String, Double> weighted = new HashMap<>();
        weighted.put("A", 0.3);
        weighted.put("B", 0.9);
        weighted.put("C", 0.5);
        weighted.put("D", 0.6);
        weighted.put("E", 0.9);


        WeightedTable weightedTable = new WeightedTable(weighted);
        System.out.println("Weighted table");
        System.out.println(weightedTable);

        double minimumSupport = 2;
        double minProbabilisticConfidence = 0.2;

        ProbabilisticMaximalFrequentItemsetTree pmfit = new WPMFIT(uncertainDatabase, weightedTable, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Weighted Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllWPMFI());

//        System.out.println();
//        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
//        pmfit.preOrder();
    }

    public static void example3(){
        Map<String, Double> map1 = new HashMap<>();
        map1.put("A", 0.6);
        map1.put("B", 0.8);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("A", 0.7);
        map2.put("C", 0.2);


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

        System.out.println(uncertainDatabase);

        Map<String, Double> weighted = new HashMap<>();
        weighted.put("A", 0.8);
        weighted.put("B", 0.5);
        weighted.put("C", 0.1);

        WeightedTable weightedTable = new WeightedTable(weighted);

        double minimumSupport = 1;
        double minProbabilisticConfidence = 0.1;

        ProbabilisticMaximalFrequentItemsetTree tree = new AWPMFIT(uncertainDatabase, weightedTable, minimumSupport, minProbabilisticConfidence);

        System.out.println(tree.findAllWPMFI());

        FrequentItemset frequentItemset = new FrequentItemset(uncertainDatabase, weightedTable, List.of("A", "B"));
        double exSupport = frequentItemset.calculateExpectedSupport();
        System.out.println(frequentItemset.calculateLowerBound(exSupport, minProbabilisticConfidence));
    }


}