package wPFI.supports;

import wPFI.entities.UncertainDatabase;
import wPFI.entities.WeightedTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Provide method calculate probabilistic support, whether itemset is frequent and itemset is maximal frequent
 * @param <E> type of items
 */
public class ProbabilisticFrequentItemset<E> {
    private UncertainDatabase<E> uncertainDatabase;
    private WeightedTable<E> weightedTable;
    private Set<E> inputItemset;
    private double[] summedSupportProbabilisticData;
    private Set<Set<E>> distinctItemsetDatabase;

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, WeightedTable weightedTable, Set<E> inputItemset) {
        this.uncertainDatabase = uncertainDatabase;
        this.weightedTable = weightedTable;
        this.inputItemset = inputItemset;
        SummedSupportProbabilisticVector<E> summedSupportProbabilisticVector = new SummedSupportProbabilisticVector<>(uncertainDatabase, inputItemset);
        this.summedSupportProbabilisticData = summedSupportProbabilisticVector.getSummedSupportProbabilisticVector();
        this.distinctItemsetDatabase = uncertainDatabase.getDistinctItemset();
    }

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<E> inputItem) {
        this(uncertainDatabase, new WeightedTable(), inputItem);
    }

    public double[] getSummedSupportProbabilisticData() {
        return summedSupportProbabilisticData;
    }

    /**
     * Calculate probabilistic support of itemset with min probabilistic confidence from summed support probabilistic vector
     * @param minimumSupport
     * @return Probabilistic Support of Itemset
     */
    public double calculateProbabilisticItemset(double minimumSupport){
        double probabilisticOfSupportItem = 0.0;

        for(int i = summedSupportProbabilisticData.length - 1; i >= minimumSupport; i--){
            probabilisticOfSupportItem += summedSupportProbabilisticData[i];

        }

        return probabilisticOfSupportItem;
    }

    public double calculateWeightedProbabilisticItemset(double minimumSupport){
        double probabilisticItemset = calculateProbabilisticItemset(minimumSupport);
        double itemsetWeighted = weightedTable.getWeighedItemset(this.inputItemset);
        return probabilisticItemset * itemsetWeighted;
    }


    /**
     * Whether itemset is probabilistic frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Frequent
     */
    public boolean isWeightedProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        return calculateProbabilisticItemset(minSupport) * weightedTable.getWeighedItemset(inputItemset)
                >=
                minProbabilisticConfidence;
    }

    /**
     * Whether itemset is probabilistic maximal frequent with min support and min probabilistic confidence
     * @param minSupport
     * @param minProbabilisticConfidence
     * @return Itemset is Probabilistic Maximal Frequent
     */
    public boolean isWeightedProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        //whether current itemset X is frequent
        if(this.isWeightedProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){

            //traversal itemset Y so that itemset Y cover current itemset X
            for(Set<E> distinctItemset : this.distinctItemsetDatabase){
                //generate itemset Y
                Set<E> tempDistinctItemset = new HashSet<>(this.inputItemset);

                tempDistinctItemset.addAll(distinctItemset);

                //current itemset X
                Set<E> distinctIemInput = new HashSet<>(this.inputItemset);

                //if itemset Y is frequent -> itemset X is infrequent
                if(tempDistinctItemset.containsAll(distinctIemInput) && !tempDistinctItemset.equals(distinctIemInput)){
                    ProbabilisticFrequentItemset<E> frequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, this.weightedTable,tempDistinctItemset);

                    if(frequentItemset.isWeightedProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        return false;
                    }
                }
            }

            return true; //Not found any frequent itemset Y
        }

        return false; // current itemset is not frequent
    }


}
