package pmfi.entities.supports;

import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;
import pmfi.functions.*;

import java.util.List;
import java.util.Set;

/**
 * Provide method calculate support, expect support, lower and upper bound of itemset
 * @param <E> type of items
 */
public class FrequentItemset<E> implements IFrequentItemset, IBound {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItemset;

    public FrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItemset)
    {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItemset = inputItemset;
    }

    /**
     * Calculate lower support of itemset (count the number of itemset appear in database)
     * @return support of itemset
     */
    @Override
    public int calculateSupport() {
        int support = 0;

        for(UncertainTransaction<E> uncertainTransaction: this.uncertainDatabase.getUncertainDatabase()){
            Set<E> itemsInTransaction = uncertainTransaction.getTransaction().keySet();
            if(itemsInTransaction.containsAll(this.inputItemset)){
                support++;
            }
        }

        return support;
    }

    /**
     * Calculate lower expect support of itemset (sum probability of itemset in database)
     * @return expect support of itemset
     */
    @Override
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
    @Override
    public double calculateLowerBound(double expectSupport, double minProbabilisticConfidence) {
        return expectSupport - Math.sqrt(-2 * expectSupport * Math.log(1 - minProbabilisticConfidence));
    }

    /**
     * Calculate upper bound of itemset with minimum probabilistic confidence
     * @param expectSupport
     * @param minProbabilisticConfidence
     * @return upper bound of itemset
     */
    @Override
    public double calculateUpperBound(double expectSupport, double minProbabilisticConfidence) {
        return (2 * expectSupport - Math.log(minProbabilisticConfidence) + Math.sqrt(Math.pow(Math.log(minProbabilisticConfidence), 2) - 8 * expectSupport * Math.log(minProbabilisticConfidence)))/2;
    }
}
