package pmfi.entities.supports;

import pmfi.entities.UncertainDatabase;
import pmfi.functions.IProbabilistic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provide method calculate probabilistic support, whether itemset is frequent and itemset is maximal frequent
 * @param <E> data type of item
 */
public class ProbabilisticFrequentItemset<E> implements IProbabilistic {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItem;
    private final double[] summedSupportProbabilisticData;
    private Set<List<E>> distinctItemDatabase;

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<List<E>> distinctItemDatabase, List<E> inputItem) {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItem = inputItem;
        SummedSupportProbabilisticVector<E> summedSupportProbabilisticVector = new SummedSupportProbabilisticVector<>(uncertainDatabase, inputItem);
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
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
            for(List<E> distinctItem : this.distinctItemDatabase){
                Set<E> tempDistinctItem = new HashSet<>(this.inputItem);
                tempDistinctItem.add(distinctItem.get(0));

                Set<E> distinctIemInput = new HashSet<>(this.inputItem);

                if(tempDistinctItem.containsAll(distinctIemInput) && !tempDistinctItem.equals(distinctIemInput)){
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        //System.out.println("---" + distinctSet +" : is probabilistic maximal frequent itemset");
                        return false;
                    }
                }
            }

            return true; //ko tìm đc tập bao nào là prob frequent itemset
        }

        return false;
    }

    /**
     * Whether itemset is probabilistic maximal frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Maximal Frequent
     */
    public boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence, List<E> sortedItemValueList) {
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
            for(E distinctItem : sortedItemValueList){
                Set<E> tempDistinctItem = new HashSet<>(this.inputItem);
                tempDistinctItem.add(distinctItem);

                Set<E> distinctIemInput = new HashSet<>(this.inputItem);

                if(tempDistinctItem.containsAll(distinctIemInput) && !tempDistinctItem.equals(distinctIemInput)){
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, new ArrayList<>(tempDistinctItem));

                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        //System.out.println("---" + distinctSet +" : is probabilistic maximal frequent itemset");
                        return false;
                    }
                }
            }

            return true; //ko tìm đc tập bao nào là prob frequent itemset
        }

        return false;
    }
}
