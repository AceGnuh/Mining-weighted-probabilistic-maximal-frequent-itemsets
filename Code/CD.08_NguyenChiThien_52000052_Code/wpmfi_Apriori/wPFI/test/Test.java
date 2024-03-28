package wPFI.test;

import wPFI.algorithms.AlgoW_PFI_Apriori;
import wPFI.entities.UncertainDatabase;
import wPFI.entities.UncertainTransaction;
import wPFI.entities.WeightedTable;
import wPFI.supports.ProbabilisticFrequentItemset;
import wPFI.supports.SummedSupportProbabilisticVector;

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
        map1.put("Milk", 0.4);
        map1.put("Fruit", 1.0);
        map1.put("Video", 0.3);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("Milk", 1.0);
        map2.put("Fruit", 0.8);

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
        weighted.put("Milk", 0.4);
        weighted.put("Fruit", 0.9);
        weighted.put("Video", 0.6);

        WeightedTable weightedTable = new WeightedTable(weighted);

        double minimumSupport = 2;
        double minProbabilisticConfidence = 0.2;
        double scaleFactor = 0;
        boolean isProbabilityModelBase = true;

//        Set inputItemset = new HashSet(List.of("Milk", "Fruit", "Video"));
//
//        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItemset);
//        System.out.println(Arrays.toString(summedSupportProbabilisticVector.getSummedSupportProbabilisticVector()));
//
//        ProbabilisticFrequentItemset probabilisticFrequentItemsetFrequentItemset
//                = new ProbabilisticFrequentItemset(uncertainDatabase, weightedTable, inputItemset );
//        double weightedProbabilisticItemset = probabilisticFrequentItemsetFrequentItemset
//                .calculateWeightedProbabilisticItemset(minimumSupport);
//
//        System.out.println(weightedProbabilisticItemset);

        AlgoW_PFI_Apriori algo = new AlgoW_PFI_Apriori(uncertainDatabase, weightedTable, minimumSupport, minProbabilisticConfidence);


        Set<Set> result = algo.runAlgorithm(scaleFactor, isProbabilityModelBase);
        System.out.println("Weighted Probabilistic Frequent Itemsets:");
        System.out.println(result);
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
        weighted.put("F", 0.9);

        WeightedTable weightedTable = new WeightedTable(weighted);
        System.out.println("Weighted table");
        System.out.println(weightedTable);

        double minimumSupport = 2;
        double minProbabilisticConfidence = 0.2;
        double scaleFactor = 0.9;
        boolean isProbabilityModelBase = false;

        AlgoW_PFI_Apriori algo = new AlgoW_PFI_Apriori(uncertainDatabase, weightedTable, minimumSupport, minProbabilisticConfidence);
        Set<Set> result = algo.runAlgorithm(scaleFactor, isProbabilityModelBase);

        System.out.println();
        System.out.println("Weighted Probabilistic Maximal Frequent Itemsets:");
        System.out.println(result);

//        Set inputItemset = new HashSet(List.of( "E", "D"));
//
//        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItemset);
//        System.out.println(Arrays.toString(summedSupportProbabilisticVector.getSummedSupportProbabilisticVector()));
//
//        ProbabilisticFrequentItemset probabilisticFrequentItemsetFrequentItemset
//                = new ProbabilisticFrequentItemset(uncertainDatabase, weightedTable, inputItemset );
//        double weightedProbabilisticItemset = probabilisticFrequentItemsetFrequentItemset
//                .calculateWeightedProbabilisticItemset(minimumSupport);
//
//        System.out.println(inputItemset + " : w PFI : " + weightedProbabilisticItemset);
    }


}
