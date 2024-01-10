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
public class ApproximateProbabilisticFrequentItemsetFrequentItemset<E> implements IProbabilisticFrequentItemset {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItemset;

    public ApproximateProbabilisticFrequentItemsetFrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItemset) {
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
            double probabilisticItem = transaction.getProbabilistic(this.inputItemset);

            expectation += probabilisticItem;
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
            double probabilisticItem = transaction.getProbabilistic(this.inputItemset);

            variance += probabilisticItem * (1.0 - probabilisticItem);
        }

        return variance;
    }

    /**
     * Calculate approximate probabilistic support of itemset from expectation and variance with min probabilistic confidence
     * @param minProbabilisticConfidence
     * @return approximate probabilistic support of itemset
     */
    public int calculateProbabilisticSupport(double minProbabilisticConfidence) {
        //calc mean and standard deviation of itemset
        double mean = this.calculateExpectation();
        double variance = this.calculateVariance();
        double stdDev = Math.sqrt(variance);

        if(stdDev == 0){
            return 0; //can't use normal distribution
        }

        //using cumulative probability to calculate probabilistic support of itemset
        NormalDistribution normalDistribution = new NormalDistribution(mean, stdDev);
        double cumulativeProbability = normalDistribution.cumulativeProbability(1.0 - minProbabilisticConfidence);

        return (int) (Math.pow(cumulativeProbability, -1) * Math.sqrt(variance) + mean);
    }

    /**
     * Whether Itemset is approximate probabilistic frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is approximate probabilistic frequent
     */
    public boolean isProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        return this.calculateProbabilisticSupport(minProbabilisticConfidence) >= minSupport;
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
                    ApproximateProbabilisticFrequentItemsetFrequentItemset<E> frequentItemset = new ApproximateProbabilisticFrequentItemsetFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

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
                    ApproximateProbabilisticFrequentItemsetFrequentItemset<E> frequentItemset = new ApproximateProbabilisticFrequentItemsetFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

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
