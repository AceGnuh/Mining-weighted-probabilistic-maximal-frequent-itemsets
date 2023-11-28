package pmfi.entities;

import pmfi.functions.IFrequentItemset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrequentItemset<E> implements IFrequentItemset {
    private SupportProbabilisticVector supportProbabilisticVector;
    private List<E> itemInput;
    private List<SupportProbabilisticItem> supportProbabilisticItemList;
    private List<SummedSupportProbabilisticItem> summedSupportProbabilisticItemList;

    public FrequentItemset(SupportProbabilisticVector supportProbabilisticVector, List<E> itemInput) {
        this.supportProbabilisticVector = supportProbabilisticVector;
        this.itemInput = itemInput;

        this.supportProbabilisticItemList = supportProbabilisticVector.calculateSupportProbabilistic(itemInput);
        this.summedSupportProbabilisticItemList = supportProbabilisticVector.calculateSummedSupportProbabilistic(itemInput);
    }

    @Override
    public double calculateExpectFrequentItemset() {
        double expectFrequentItemset = 0.0;

        for (SummedSupportProbabilisticItem probabilisticItem : this.summedSupportProbabilisticItemList){
            expectFrequentItemset += probabilisticItem.getFrequent()*probabilisticItem.getProbabilistic();
        }

        return (double) Math.round(expectFrequentItemset*1000)/1000;
    }

    @Override
    public double calculateProbabilisticFrequentItemset(int minSupport) {
        double probabilisticFrequentItemset = 0.0;

        for(SupportProbabilisticItem supportProbabilisticItem : this.supportProbabilisticItemList){
            if(supportProbabilisticItem.getFrequent() >= minSupport){
                probabilisticFrequentItemset += supportProbabilisticItem.getProbabilistic();
            }
        }

        return (double) Math.round(probabilisticFrequentItemset*1000)/1000;
    }

    @Override
    public int calculateProbabilisticSupport(double minProbabilisticConfidence) {
        int numItemInputOccurrence = this.summedSupportProbabilisticItemList.size();

        for(int i = numItemInputOccurrence; i >= 0; i--){
            double currProbabilisticFrequentItemset = this.calculateProbabilisticFrequentItemset(i);

            if (currProbabilisticFrequentItemset >= minProbabilisticConfidence){
                return i;
            }
        }

        return 0;
    }

    @Override
    public boolean isProbabilisticFrequentItemset(int minSupport, double minProbabilisticConfidence) {
        return this.calculateProbabilisticSupport(minProbabilisticConfidence) >= minSupport;
    }

    @Override
    public boolean isProbabilisticMaximalFrequentItemset(int minSupport, double minProbabilisticConfidence) {
        if(this.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
            Set<List<E>> allDistinctSetList = this.supportProbabilisticVector.getPossibleWorld().getAllDistinctSet();

            for(List<E> distinctSet : allDistinctSetList){
                Set<E> distinctDataset = new HashSet<>(distinctSet);
                Set<E> distinctIemInput = new HashSet<>(this.itemInput);

                //System.out.println("Loading------" + distinctDataset);

                if(distinctDataset.containsAll(distinctIemInput) && !distinctDataset.equals(distinctIemInput)){
                    FrequentItemset frequentItemset = new FrequentItemset(this.supportProbabilisticVector, distinctSet);
                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        return false;
                    }
                }
            }

            return true; //ko tìm đc tập bao nào là prob frequent itemset
        }
        return false;
    }
}
