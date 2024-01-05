package pmfi.entities.supports;

import pmfi.entities.UncertainDatabase;
import pmfi.functions.IProbabilistic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provide method calculate probabilistic support, whether itemset is frequent and itemset is maximal frequent
 * @param <E> type of items
 */
public class ProbabilisticFrequentItemset<E> implements IProbabilistic {
    private UncertainDatabase<E> uncertainDatabase;
    private List<E> inputItemset;
    private double[] summedSupportProbabilisticData;
    private Set<E> distinctItemDatabase;

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<E> distinctItemDatabase, List<E> inputItemset) {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItemset = inputItemset;
        SummedSupportProbabilisticVector<E> summedSupportProbabilisticVector = new SummedSupportProbabilisticVector<>(uncertainDatabase, inputItemset);
        this.summedSupportProbabilisticData = summedSupportProbabilisticVector.getSummedSupportProbabilisticVector();
        this.distinctItemDatabase = distinctItemDatabase;
    }

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItem) {
        this(uncertainDatabase, new HashSet<>(), inputItem);
    }

    public double[] getSummedSupportProbabilisticData() {
        return summedSupportProbabilisticData;
    }

    /**
     * Calculate probabilistic support of itemset with min probabilistic confidence from summed support probabilistic vector
     * @param minimumProbabilisticConfidence
     * @return Probabilistic Support of Itemset
     */
    @Override
    public int calculateProbabilisticSupport(double minimumProbabilisticConfidence){
        double probabilisticOfSupportItem = 0.0;
        for(int i = summedSupportProbabilisticData.length - 1; i >= 0; i--){
            probabilisticOfSupportItem += summedSupportProbabilisticData[i];

            if(probabilisticOfSupportItem >= minimumProbabilisticConfidence){
                return i;
            }
        }
        return 0;
    }

    /**
     * Whether itemset is probabilistic frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Frequent
     */
    @Override
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
            for(E distinctItem : this.distinctItemDatabase){
                //generate itemset Y
                Set<E> tempDistinctItem = new HashSet<>(this.inputItemset);
                tempDistinctItem.add(distinctItem);

                //current itemset X
                Set<E> distinctIemInput = new HashSet<>(this.inputItemset);

                //if itemset Y is frequent -> itemset X is infrequent
                if(tempDistinctItem.containsAll(distinctIemInput) && !tempDistinctItem.equals(distinctIemInput)){
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

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
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

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
