package pmfi.entities.approximate;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;
import pmfi.functions.IProbabilisticFrequentItemset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @param <E> type of items
 */
public class ApproximateProbabilisticFrequentItemset<E> implements IProbabilisticFrequentItemset {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItemset;

    public ApproximateProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItemset) {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItemset = inputItemset;
    }

    /**
     * Calculate expectation of itemset
     * @return expectation of itemset
     */
    private double calculateExpectation(){
        double expectation = 0.0;

        //sum of probability itemset in UD
        for(UncertainTransaction<E> transaction : this.uncertainDatabase.getUncertainDatabase()){
            double probabilisticItemset = transaction.getProbabilistic(this.inputItemset);

            expectation += probabilisticItemset;
        }

        return expectation;
    }

    /**
     * Calculate Variance of itemset
     * @return variance of itemset
     */
    private double calculateVariance(){
        double variance = 0.0;

        //sum of probability * (1 - probability) itemset in UD
        for(UncertainTransaction<E> transaction : this.uncertainDatabase.getUncertainDatabase()){
            double probabilisticItemset = transaction.getProbabilistic(this.inputItemset);

            variance += probabilisticItemset * (1.0 - probabilisticItemset);
        }

        return variance;
    }

    /**
     * Calculate mean and variance of itemset in UD
     * @return mean and variance of itemset
     */
    public Pair<Double, Double> calculateMeanAndVariance(){
        double expectation = 0.0;
        double variance = 0.0;

        //sum of probability * (1 - probability) itemset in UD
        for(UncertainTransaction<E> transaction : uncertainDatabase.getUncertainDatabase()){
            double probabilisticItemset = transaction.getProbabilistic(inputItemset);

            expectation += probabilisticItemset;
            variance += probabilisticItemset * (1.0 - probabilisticItemset);
        }

        return new Pair<>(expectation, variance);
    }

    /**
     * Calculate approximate probabilistic support of itemset from expectation and variance with min probabilistic confidence
     * @param minProbabilisticConfidence
     * @return approximate probabilistic support of itemset
     */
    public int calculateProbabilisticSupport(double minProbabilisticConfidence) {
        //calc mean and standard deviation of itemset
        //first : mean; second : variance
        Pair<Double, Double> meanAndVariance = calculateMeanAndVariance();

        double mean = meanAndVariance.getFirst();
        double variance = meanAndVariance.getSecond();
        double stdDev = Math.sqrt(variance);

        //using cumulative probability to calculate probabilistic support of itemset
        NormalDistribution normalDistribution = new NormalDistribution(mean, stdDev);
        double inverseCumulativeProbability = normalDistribution.inverseCumulativeProbability(1.0 - minProbabilisticConfidence);

        return (int) (inverseCumulativeProbability * Math.sqrt(variance) + mean);
    }

    /**
     * Whether Itemset is approximate probabilistic frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is approximate probabilistic frequent
     */
    public boolean isProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        return calculateProbabilisticSupport(minProbabilisticConfidence) >= minSupport;
    }

    /**
     * Whether itemset is probabilistic maximal frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Maximal Frequent
     */
    @Override
    public boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        //whether current itemset X is frequent
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){

            //traversal itemset Y so that itemset Y cover current itemset X
            for(E distinctItem : uncertainDatabase.getDistinctItem()){
                //generate itemset Y
                Set<E> tempDistinctItem = new HashSet<>(this.inputItemset);
                tempDistinctItem.add(distinctItem);

                //current itemset X
                Set<E> distinctIemInput = new HashSet<>(this.inputItemset);

                //if itemset Y is frequent -> itemset X is infrequent
                if(tempDistinctItem.containsAll(distinctIemInput) && !tempDistinctItem.equals(distinctIemInput)){
                    ApproximateProbabilisticFrequentItemset<E> frequentItemset = new ApproximateProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        return false;
                    }
                }
            }

            return true; //Not found any frequent itemset Y
        }

        return false; // current itemset is not frequent
    }

    /**
     * Whether itemset is probabilistic maximal frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Maximal Frequent
     */
    public boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence, List<E> sortedItemValueList) {
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
            //traversal itemset Y so that itemset Y cover current itemset X
            for(E distinctItem : sortedItemValueList){

                //generate itemset Y
                Set<E> tempDistinctItem = new HashSet<>(this.inputItemset);
                tempDistinctItem.add(distinctItem);

                //current itemset X
                Set<E> distinctIemInput = new HashSet<>(this.inputItemset);

                //if itemset Y is frequent -> itemset X is infrequent
                if(tempDistinctItem.containsAll(distinctIemInput) && !tempDistinctItem.equals(distinctIemInput)){
                    ApproximateProbabilisticFrequentItemset<E> frequentItemset = new ApproximateProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        return false;
                    }
                }
            }

            return true; //Not found any frequent itemset Y
        }

        return false;
    }
}
