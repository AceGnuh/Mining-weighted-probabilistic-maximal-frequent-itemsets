package wPFI.supports;

import wPFI.entities.UncertainDatabase;
import wPFI.entities.UncertainTransaction;

import java.util.List;
import java.util.Set;

/**
 * Provide method calculate support, expect support, lower and upper bound of itemset
 * @param <E> type of items
 */
public class FrequentItemset<E>  {
    private final UncertainDatabase<E> uncertainDatabase;
    private final Set<E> inputItemset;

    public FrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<E> inputItemset)
    {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItemset = inputItemset;
    }

    /**
     * Calculate lower expect support of itemset (sum probability of itemset in database)
     * @return expect support of itemset
     */
    public double calculateExpectedSupport() {
        double expectFrequentItemset = 0.0;

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase.getUncertainDatabase()){
            double probInputItem = uncertainTransaction.getProbabilistic(this.inputItemset);
            expectFrequentItemset += probInputItem;
        }

        return expectFrequentItemset;
    }

    /**
     * Calculate lower bound of itemset with minimum probabilistic confidence
     * @param expectSupport
     * @param minProbabilisticConfidence
     * @return lower bound of itemset
     */
    public double calculateLowerBound(double expectSupport, double minProbabilisticConfidence) {
        return expectSupport - Math.sqrt(-2 * expectSupport * Math.log(1 - minProbabilisticConfidence));
    }

    /**
     * Calculate upper bound of itemset with minimum probabilistic confidence
     * @param expectSupport
     * @param minProbabilisticConfidence
     * @return upper bound of itemset
     */
    public double calculateUpperBound(double expectSupport, double minProbabilisticConfidence) {
        return (2 * expectSupport - Math.log(minProbabilisticConfidence) + Math.sqrt(Math.pow(Math.log(minProbabilisticConfidence), 2) - 8 * expectSupport * Math.log(minProbabilisticConfidence)))/2;
    }
}
