package pmfi.entities.approximate;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;
import pmfi.entities.supports.ProbabilisticFrequentItemset;
import pmfi.functions.IProbabilistic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApproximateProbabilisticFrequentItemset<E> implements IProbabilistic {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItem;

    public ApproximateProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItem) {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItem = inputItem;
    }

    private double calculateExpectation(){
        double expectation = 0.0;

        for(UncertainTransaction<E> transaction : this.uncertainDatabase.getUncertainTransactions()){
            double probabilisticItem = transaction.getProbabilistic(this.inputItem);

            expectation += probabilisticItem;
        }

        return expectation;
    }

    private double calculateVariance(){
        double variance = 0.0;

        for(UncertainTransaction<E> transaction : this.uncertainDatabase.getUncertainTransactions()){
            double probabilisticItem = transaction.getProbabilistic(this.inputItem);

            variance += probabilisticItem * (1.0 - probabilisticItem);
        }

        return variance;
    }

    public int calculateProbabilisticSupport(double minProbabilisticConfidence) {
        double mean = this.calculateExpectation();
        double variance = this.calculateVariance();
        double stdDev = Math.sqrt(variance);

//        System.out.println("Mean: " + mean);
//        System.out.println("Variance: " + variance);

        NormalDistribution normalDistribution = new NormalDistribution(mean, stdDev);

        double cumulativeProbability = normalDistribution.cumulativeProbability(1.0 - minProbabilisticConfidence);

        return (int) (Math.pow(cumulativeProbability, -1) * Math.sqrt(variance) + mean);
        //return (int) ((1.0 / cumulativeProbability) * Math.sqrt(variance) + mean);
    }

    public boolean isProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        return this.calculateProbabilisticSupport(minProbabilisticConfidence) >= minSupport;
    }

    public boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){

            Set<Set<E>> allDistinctSetList = new HashSet<>();

            Set<List<E>> distinctItemInDatabase = this.uncertainDatabase.getDistinctItem();

            for(List<E> distinctItem : distinctItemInDatabase){
                Set<E> tempDistinctItem = new HashSet<>(this.inputItem);
                tempDistinctItem.add(distinctItem.get(0));

                allDistinctSetList.add(tempDistinctItem);
            }

            for(Set<E> distinctSet : allDistinctSetList){
                Set<E> distinctDataset = new HashSet<>(distinctSet);
                Set<E> distinctIemInput = new HashSet<>(this.inputItem);

                if(distinctDataset.containsAll(distinctIemInput) && !distinctDataset.equals(distinctIemInput)){
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(distinctSet));

                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        System.out.println("---" + distinctSet +" : is probabilistic maximal frequent itemset");
                        return false;
                    }
                }
            }

            return true; //ko tìm đc tập bao nào là prob frequent itemset
        }
        return false;
    }
}
