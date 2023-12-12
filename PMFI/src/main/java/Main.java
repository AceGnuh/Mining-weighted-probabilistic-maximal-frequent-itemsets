import pmfi.datasets.MyDataset;
import pmfi.entities.*;
import pmfi.entities.brute_force.SummedSupportProbabilisticItem;
import pmfi.entities.supports.FrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.pmfit.ApproximatePMFIT;
import pmfi.entities.brute_force.PossibleWorld;
import pmfi.entities.brute_force.SupportProbabilisticVector;
import pmfi.entities.supports.ProbabilisticFrequentItemset;
import pmfi.entities.supports.SummedSupportProbabilisticVector;
import pmfi.pmfit.PMFIT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();

        String path = Paths.get("").toAbsolutePath().toString();
        path += "\\src\\main\\java\\pmfi\\datasets\\";

        String pathDataset = path + "connect4.txt";
        double mean = 0.78;
        double variance = 0.65;

        MyDataset myDataset = new MyDataset(pathDataset, mean, variance);
        UncertainDatabase uncertainDatabase = myDataset.getUncertainDatabase();
        System.out.println(uncertainDatabase);

        //run algorithms

        PMFIT pmfit = new PMFIT(uncertainDatabase, 0.9, 0.1);
        //pmfit.findAllPMFI();

        //examplePossibleWorld();

        //example1();
        //example2();
        //example3();
        //example4();
        //exampleApproximatePMFIT();

        //example3();
        //exampleSummedSupportProbabilisticVector();

        System.out.println();

        // get the end time
        long end = System.nanoTime();

        // execution time in seconds
        long execution = (end - start);
        System.out.println("Execution time of Program is");
        System.out.println(execution / 1000000 + " milliseconds");
    }

    public static void exampleSummedSupportProbabilisticVector(){
        //String data type
        UncertainItemset<String> item1 = new UncertainItemset<>("A", 0.6);
        UncertainItemset<String> item2 = new UncertainItemset<>("B", 0.7);

        UncertainItemset<String> item3 = new UncertainItemset<>("A", 0.2);
        UncertainItemset<String> item4 = new UncertainItemset<>("C", 0.3);

        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(Arrays.asList(item1, item2)));
        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(Arrays.asList(item3, item4)));

        //create uncertain database
        UncertainDatabase uncertainDatabase
                = new UncertainDatabase<>(new ArrayList<>(
                Arrays.asList(
                        transaction1,
                        transaction2
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        PossibleWorld possibleWorld
                = new PossibleWorld(uncertainDatabase)
                .build();

        //System.out.println("Size of possible world: " + possibleWorld.size());

        SupportProbabilisticVector supportProbabilisticVector = new SupportProbabilisticVector(possibleWorld, uncertainDatabase);

        List itemInput = new ArrayList(List.of("A"));

        ProbabilisticFrequentItemset probabilisticFrequentItemset1 = new ProbabilisticFrequentItemset(uncertainDatabase, itemInput);

        List<SummedSupportProbabilisticItem> summedSupportProbabilisticVector = supportProbabilisticVector.calculateSummedSupportProbabilistic(itemInput);
        System.out.println("Summed support probabilistic vector of itemset " +  itemInput + "  with possible world: ");
        for(SummedSupportProbabilisticItem summedSupportProbabilisticItem : summedSupportProbabilisticVector){
            System.out.println(summedSupportProbabilisticItem);
        }

        System.out.println();
        System.out.println("Summed support probabilistic vector of itemset " +  itemInput + " with Divide and Conquer: " + probabilisticFrequentItemset1.getSummedSupportProbabilisticData());
    }

    public static void example3(){
        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.5),
                        new UncertainItemset<>("B", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.9))
        ));

        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("C", 0.8),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction3
                = new UncertainTransaction(3, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("C", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.5))
        ));

        UncertainTransaction transaction4
                = new UncertainTransaction(4, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.6),
                        new UncertainItemset<>("C", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction5
                = new UncertainTransaction(5, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.8),
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("C", 0.5),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.7))
        ));

        UncertainTransaction transaction6
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.8))
        ));

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

        System.out.println(uncertainDatabase);

        /*
        PossibleWorld possibleWorld
                = new PossibleWorld(uncertainDatabase)
                .build();

        SupportProbabilisticVector supportProbabilisticVector = new SupportProbabilisticVector(possibleWorld, uncertainDatabase);

         */
        //System.out.println();
        List itemInput = new ArrayList(Arrays.asList("C", "E", "D"));
        int minimumSupport = 3;
        double minProbabilisticConfidence = 0.1;

        FrequentItemset frequentItemset1 = new FrequentItemset(uncertainDatabase, itemInput);
        ProbabilisticFrequentItemset probabilisticFrequentItemset1 = new ProbabilisticFrequentItemset(uncertainDatabase, itemInput);

        //System.out.println(supportProbabilisticVector.calculateSummedSupportProbabilistic(itemInput));
        System.out.println(probabilisticFrequentItemset1.getSummedSupportProbabilisticData());

        System.out.println();
        double supportItemset = frequentItemset1.calculateSupport();
        System.out.println("Support of Itemset " + itemInput + ": " + supportItemset);

        System.out.println();
        double expectFrequentItemset = frequentItemset1.calculateExpectedSupport();
        System.out.println("Expect Frequent Itemset " + itemInput + ": " + expectFrequentItemset);

//        System.out.println();
//        double probabilisticFrequentItemset = probabilisticFrequentItemset1.calculateProbabilisticFrequentItemset(minimumSupport);
//        System.out.println("Probabilistic Frequent Itemset " + itemInput + ": " + probabilisticFrequentItemset);

        System.out.println();
        double probabilisticSupport = probabilisticFrequentItemset1.calculateProbabilisticSupport(minProbabilisticConfidence);
        System.out.println("Probabilistic Support " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + probabilisticSupport);

        System.out.println();
        double lowerBoundItemset = frequentItemset1.calculateLowerBound(expectFrequentItemset, minProbabilisticConfidence);
        System.out.println("Lower bound " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + lowerBoundItemset);

        System.out.println();
        double upperBoundItemset = frequentItemset1.calculateUpperBound(expectFrequentItemset, minProbabilisticConfidence);
        System.out.println("Upper bound " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + upperBoundItemset);

        System.out.println();
        boolean isProbabilisticFrequentItemset = probabilisticFrequentItemset1.isProbabilisticFrequentItemset(minimumSupport, minProbabilisticConfidence);
        System.out.println("Probabilistic Frequent Itemset " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + isProbabilisticFrequentItemset);

        System.out.println();
        boolean isProbabilisticMaximalFrequentItemset = probabilisticFrequentItemset1.isProbabilisticMaximalFrequentItemset(minimumSupport, minProbabilisticConfidence);
        System.out.println("Probabilistic Maximal Frequent Itemset " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + isProbabilisticMaximalFrequentItemset);
    }

    private static void exampleApproximatePMFIT() {
        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.5),
                        new UncertainItemset<>("B", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.9))
        ));

        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("C", 0.8),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction3
                = new UncertainTransaction(3, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("C", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.5))
        ));

        UncertainTransaction transaction4
                = new UncertainTransaction(4, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.6),
                        new UncertainItemset<>("C", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.8))
        ));


        UncertainTransaction transaction5
                = new UncertainTransaction(5, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.8),
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("C", 0.5),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.7))
        ));

        UncertainTransaction transaction6
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.8))
        ));

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
        System.out.println("Approximate Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllPMFI());

        System.out.println();
        System.out.println("Approximate Probabilistic Frequent Itemset Tree with pre-order");
        pmfit.preOrder();
    }

    public static void examplePossibleWorld(){
        //example Integer data type

        /*
        UncertainItemset item1 = new UncertainItemset<>(1, 0.6);
        UncertainItemset item2 = new UncertainItemset<>(2, 0.7);
        UncertainItemset item3 = new UncertainItemset<>(1, 0.2);
        UncertainItemset item4 = new UncertainItemset<>(3, 0.3);
         */

        //String data type
        UncertainItemset<String> item1 = new UncertainItemset<>("A", 0.6);
        UncertainItemset<String> item2 = new UncertainItemset<>("B", 0.7);

        UncertainItemset<String> item3 = new UncertainItemset<>("A", 0.2);
        UncertainItemset<String> item4 = new UncertainItemset<>("C", 0.3);

        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(Arrays.asList(item1, item2)));
        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(Arrays.asList(item3, item4)));

        //create uncertain database
        UncertainDatabase uncertainDatabase
                = new UncertainDatabase<>(new ArrayList<>(
                Arrays.asList(
                        transaction1,
                        transaction2
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        PossibleWorld possibleWorld
                = new PossibleWorld(uncertainDatabase)
                .build();

        System.out.println("Size of possible world: " + possibleWorld.size());

        possibleWorld.display();
    }

    public static void example1(){

//        UncertainItemset item1 = new UncertainItemset<>(1, 0.6);
//        UncertainItemset item2 = new UncertainItemset<>(2, 0.7);
//        UncertainItemset item3 = new UncertainItemset<>(1, 0.2);
//        UncertainItemset item4 = new UncertainItemset<>(3, 0.3);

        UncertainItemset<String> item1 = new UncertainItemset<>("A", 0.6);
        UncertainItemset<String> item2 = new UncertainItemset<>("B", 0.7);

        UncertainItemset<String> item3 = new UncertainItemset<>("A", 0.2);
        UncertainItemset<String> item4 = new UncertainItemset<>("C", 0.3);


        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(Arrays.asList(item1, item2)));
        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(Arrays.asList(item3, item4)));

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

        PossibleWorld possibleWorld
                = new PossibleWorld(uncertainDatabase)
                .build();

        System.out.println("Size of possible world: " + possibleWorld.size());

        possibleWorld.display();

        int minimumSupport = 1;
        double minProbabilisticConfidence = 0.1;
        List inputItem = new ArrayList(Arrays.asList("A"));

        SupportProbabilisticVector supportProbabilisticVector = new SupportProbabilisticVector(possibleWorld, uncertainDatabase);
        System.out.println(supportProbabilisticVector.calculateSummedSupportProbabilistic(inputItem));

        SummedSupportProbabilisticVector summedSupportProbabilisticVector = new SummedSupportProbabilisticVector(uncertainDatabase, inputItem);
        System.out.println(summedSupportProbabilisticVector.getSummedSupportProbabilisticVector());


        PMFIT pmfit = new PMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree: " + pmfit.findAllPMFI());

    }

    public static void example2(){
        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.5),
                        new UncertainItemset<>("B", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.9))
        ));

        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("C", 0.8),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction3
                = new UncertainTransaction(3, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("C", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.5))
        ));

        UncertainTransaction transaction4
                = new UncertainTransaction(4, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.6),
                        new UncertainItemset<>("C", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction5
                = new UncertainTransaction(5, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.8),
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("C", 0.5),
                        new UncertainItemset<>("D", 0.6),
                        new UncertainItemset<>("E", 0.7))
        ));

        UncertainTransaction transaction6
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.8))
        ));

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

        ProbabilisticMaximalFrequentItemsetTree pmfit = new PMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllPMFI());

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
        pmfit.preOrder();
    }

    public static void example4(){
        UncertainTransaction transaction1
                = new UncertainTransaction(1, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.5),
                        new UncertainItemset<>("B", 0.8),
                        new UncertainItemset<>("C", 0.7),
                        new UncertainItemset<>("D", 0.8),
                        new UncertainItemset<>("E", 0.9))
        ));

        UncertainTransaction transaction2
                = new UncertainTransaction(2, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("C", 0.9)
                )
        ));

        UncertainTransaction transaction3
                = new UncertainTransaction(3, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.6),
                        new UncertainItemset<>("D", 0.9)
                        )
        ));

        UncertainTransaction transaction4
                = new UncertainTransaction(4, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.6),
                        new UncertainItemset<>("E", 0.8))
        ));


        UncertainTransaction transaction5
                = new UncertainTransaction(5, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.8),
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("C", 0.5),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.7))
        ));

        UncertainTransaction transaction6
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.9))
        ));

        UncertainTransaction transaction7
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 0.9)
                )
        ));

        UncertainTransaction transaction8
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("D", 0.3)
                )
        ));

        UncertainTransaction transaction9
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("B", 1.0),
                        new UncertainItemset<>("C", 0.7),
                        new UncertainItemset<>("D", 0.9),
                        new UncertainItemset<>("E", 0.8))
        ));

        UncertainTransaction transaction10
                = new UncertainTransaction(6, new ArrayList<>(
                Arrays.asList(
                        new UncertainItemset<>("A", 0.7),
                        new UncertainItemset<>("B", 0.9),
                        new UncertainItemset<>("D", 0.3))
        ));

        UncertainDatabase uncertainDatabase
                = new UncertainDatabase(new ArrayList<>(
                Arrays.asList(
                        transaction1,
                        transaction2,
                        transaction3,
                        transaction4,
                        transaction5,
                        transaction6,
                        transaction7,
                        transaction8,
                        transaction9,
                        transaction10
                )
        ));

        System.out.println("Uncertain database");
        System.out.println(uncertainDatabase);

        int minimumSupport = 4;
        double minProbabilisticConfidence = 0.1;

        System.out.println(uncertainDatabase.getDistinctItem());

        PMFIT pmfit = new PMFIT(uncertainDatabase, minimumSupport, minProbabilisticConfidence);

        //Probabilistic maximal frequent itemset
        System.out.println();
        System.out.println("Probabilistic Maximal Frequent Itemset Collection: " + pmfit.findAllPMFI());

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset Tree with pre-order");
        pmfit.preOrder();
    }


}