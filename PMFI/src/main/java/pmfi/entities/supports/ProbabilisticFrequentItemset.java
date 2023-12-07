package pmfi.entities.supports;

import pmfi.entities.FrequentItemset;
import pmfi.entities.UncertainDatabase;
import pmfi.functions.IProbabilistic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProbabilisticFrequentItemset<E> implements IProbabilistic {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItem;
    private final SummedSupportProbabilisticVector<E> summedSupportProbabilisticVector;
    private final List<Double> summedSupportProbabilisticData;
    private final FrequentItemset<E> frequentItemset;

    public ProbabilisticFrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItem) {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItem = inputItem;
        this.summedSupportProbabilisticVector = new SummedSupportProbabilisticVector<>(uncertainDatabase, inputItem);
        this.summedSupportProbabilisticData = this.summedSupportProbabilisticVector.getSummedSupportProbabilisticVector();
        this.frequentItemset = new FrequentItemset<>(uncertainDatabase, inputItem);
    }

    public List<Double> getSummedSupportProbabilisticData() {
        return summedSupportProbabilisticData;
    }

    public int calculateProbabilisticSupport(double minimumProbabilisticConfidence){
        double probabilisticOfSupportItem = 0.0;
        for(int i = frequentItemset.calculateSupport(); i >= 0; i--){
            probabilisticOfSupportItem += summedSupportProbabilisticData.get(i);

            if(probabilisticOfSupportItem >= minimumProbabilisticConfidence){
                return i;
            }
        }
        return 0;
    }

    public boolean isProbabilisticFrequentItemset(int minSupport, double minProbabilisticConfidence) {
        return this.calculateProbabilisticSupport(minProbabilisticConfidence) >= minSupport;
    }

    public boolean isProbabilisticMaximalFrequentItemset(int minSupport, double minProbabilisticConfidence) {
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){

            Set<Set<E>> allDistinctSetList = new HashSet<>();

            List<List<E>> distinctItemInDatabase = this.uncertainDatabase.getDistinctItem();

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
