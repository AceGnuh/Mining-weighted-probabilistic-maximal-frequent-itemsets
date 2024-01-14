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
    private Set<E> distinctItemDatabase;

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<E> distinctItemDatabase, WeightedTable weightedTable, Set<E> inputItemset) {
        this.uncertainDatabase = uncertainDatabase;
        this.weightedTable = weightedTable;
        this.inputItemset = inputItemset;
        SummedSupportProbabilisticVector<E> summedSupportProbabilisticVector = new SummedSupportProbabilisticVector<>(uncertainDatabase, inputItemset);
        this.summedSupportProbabilisticData = summedSupportProbabilisticVector.getSummedSupportProbabilisticVector();
        this.distinctItemDatabase = distinctItemDatabase;
    }

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, WeightedTable weightedTable, Set<E> inputItem) {
        this(uncertainDatabase, new HashSet<>(), weightedTable, inputItem);
    }

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, Set<E> inputItem) {
        this(uncertainDatabase, new HashSet<>(), new WeightedTable(), inputItem);
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

    public boolean isWeightedProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence) {
        return calculateProbabilisticItemset(minSupport) * weightedTable.getWeighedItemset(inputItemset)
                >=
                minProbabilisticConfidence;
    }


}
