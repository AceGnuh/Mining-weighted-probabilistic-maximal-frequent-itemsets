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
        map1.put("1", 0.6);
        map1.put("2", 0.8);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("1", 0.7);
        map2.put("3", 0.2);

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
        weighted.put("1", 0.8);
        weighted.put("2", 0.5);
        weighted.put("3", 0.3);
        WeightedTable weightedTable = new WeightedTable(weighted);

        double minimumSupport = 1;
        double minProbabilisticConfidence = 0.1;
        double scaleFactor = 0;
        boolean isProbabilityModelBase = false;

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
        map1.put("1", 0.5);
        map1.put("2", 0.7);
        map1.put("4", 0.8);
        map1.put("5", 0.9);
        UncertainTransaction transaction1
                = new UncertainTransaction(1, map1);

        Map<String, Double> map2 = new HashMap<>();
        map2.put("2", 0.6);
        map2.put("3", 0.8);
        map2.put("4", 0.6);
        map2.put("5", 0.8);
        UncertainTransaction transaction2
                = new UncertainTransaction(2, map2);

        Map<String, Double> map3 = new HashMap<>();
        map3.put("3", 0.6);
        map3.put("4", 0.9);
        map3.put("5", 0.5);
        UncertainTransaction transaction3
                = new UncertainTransaction(3, map3);

        Map<String, Double> map4 = new HashMap<>();
        map4.put("1", 0.6);
        map4.put("3", 0.7);
        map4.put("4", 0.8);
        map4.put("5", 0.8);
        UncertainTransaction transaction4
                = new UncertainTransaction(4, map4);

        Map<String, Double> map5 = new HashMap<>();
        map5.put("1", 0.8);
        map5.put("2", 0.9);
        map5.put("3", 0.5);
        map5.put("4", 0.6);
        map5.put("5", 0.7);

        UncertainTransaction transaction5
                = new UncertainTransaction(5, map5);

        Map<String, Double> map6 = new HashMap<>();
        map6.put("2", 0.6);
        map6.put("4", 0.9);
        map6.put("5", 0.8);
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
        weighted.put("1", 0.3);
        weighted.put("2", 0.9);
        weighted.put("3", 0.5);
        weighted.put("4", 0.6);
        weighted.put("5", 0.9);

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
}
