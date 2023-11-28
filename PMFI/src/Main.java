import pmfi.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //create uncertain itemset (String data type)

        UncertainItemset<String> item1 = new UncertainItemset<>("A", 0.6);
        UncertainItemset<String> item2 = new UncertainItemset<>("B", 0.7);
        UncertainItemset<String> item6 = new UncertainItemset<>("D", 0.7);

        UncertainItemset<String> item3 = new UncertainItemset<>("A", 0.2);
        UncertainItemset<String> item4 = new UncertainItemset<>("C", 0.3);

        UncertainItemset<String> item5 = new UncertainItemset<>("A", 0.8);


        //example Integer data type
        /*
        UncertainItemset item1 = new UncertainItemset<>(1, 0.6);
        UncertainItemset item2 = new UncertainItemset<>(2, 0.7);
        UncertainItemset item3 = new UncertainItemset<>(1, 0.2);
        UncertainItemset item4 = new UncertainItemset<>(3, 0.3);
        */



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





        //create uncertain transaction

//        UncertainTransaction transaction1
//                = new UncertainTransaction(1, new ArrayList<>(Arrays.asList(item1, item2, item6)));
//        UncertainTransaction transaction2
//                = new UncertainTransaction(2, new ArrayList<>(Arrays.asList(item3, item4)));
//        UncertainTransaction transaction3
//                = new UncertainTransaction(3, new ArrayList<>(Arrays.asList(item5)));



        //create uncertain database
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

        PossibleWorld possibleWorld
                = new PossibleWorld(uncertainDatabase)
                .build();

        System.out.println("Size of possible world: " + possibleWorld.size());

        //System.out.println(possibleWorld.getAllDistinctSet());


        possibleWorld.display();


        SupportProbabilisticVector supportProbabilisticVector
                = new SupportProbabilisticVector<>(possibleWorld, uncertainDatabase);



        //input data
        List itemInput = new ArrayList<>(Arrays.asList("B", "E"));
        int minimumSupport = 3;
        double minProbabilisticConfidence = 0.1;

        FrequentItemset frequentItemset1 = new FrequentItemset(supportProbabilisticVector, itemInput);

        System.out.println();
        //supportProbabilisticVector.displaySummedSupportProbabilistic(itemInput);

        System.out.println();
        System.out.println("Expect Frequent Itemset " + itemInput + ": " + frequentItemset1.calculateExpectFrequentItemset());

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset " + itemInput + ": " + frequentItemset1.calculateProbabilisticFrequentItemset(minimumSupport));

        System.out.println();
        System.out.println("Probabilistic Support " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + frequentItemset1.calculateProbabilisticSupport( minProbabilisticConfidence));

        System.out.println();
        System.out.println("Probabilistic Frequent Itemset " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + frequentItemset1.isProbabilisticFrequentItemset(minimumSupport, minProbabilisticConfidence));

        System.out.println();
        System.out.println("Probabilistic Maximal Frequent Itemset " + itemInput + " with min probabilistic confidence " + minProbabilisticConfidence +": " + frequentItemset1.isProbabilisticMaximalFrequentItemset(minimumSupport, minProbabilisticConfidence));

    }
}