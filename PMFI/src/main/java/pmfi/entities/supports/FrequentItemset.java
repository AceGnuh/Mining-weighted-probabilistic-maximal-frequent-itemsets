package pmfi.entities.supports;

import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainItemset;
import pmfi.entities.UncertainTransaction;
import pmfi.entities.brute_force.SummedSupportProbabilisticItem;
import pmfi.entities.brute_force.SupportProbabilisticItem;
import pmfi.entities.brute_force.SupportProbabilisticVector;
import pmfi.functions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @param <E> data type of item
 */
public class FrequentItemset<E> implements ISupport, IBound {
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<E> inputItem;

    public FrequentItemset(UncertainDatabase<E> uncertainDatabase, List<E> inputItem)
    {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItem = inputItem;
    }

    @Override
    public int calculateSupport() {
        int support = 0;

        for(UncertainTransaction<E> uncertainTransaction: this.uncertainDatabase.getUncertainTransactions()){
            Set<E> itemsInTransaction = uncertainTransaction.getTransaction().keySet();
            if(itemsInTransaction.containsAll(this.inputItem)){
                support++;
            }
        }

        return support;
    }

    @Override
    public double calculateExpectedSupport() {
        double expectFrequentItemset = 0.0;

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase.getUncertainTransactions()){
            double probInputItem = uncertainTransaction.getProbabilistic(this.inputItem);
            expectFrequentItemset += probInputItem;
        }

        return expectFrequentItemset;
    }

    /*
    @Override
    public double calculateProbabilisticFrequentItemset(int minSupport) {
        double probabilisticFrequentItemset = 0.0;

        for(SummedSupportProbabilisticItem supportProbabilisticItem : this.summedSupportProbabilisticItemList){
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
                Set<E> distinctIemInput = new HashSet<>(this.inputItem);

                //System.out.println("Loading------" + distinctDataset);

                if(distinctDataset.containsAll(distinctIemInput) && !distinctDataset.equals(distinctIemInput)){
                    FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, this.supportProbabilisticVector, distinctSet);
                    if(frequentItemset.isProbabilisticFrequentItemset(minSupport, minProbabilisticConfidence)){
                        System.out.println("---" + distinctSet);
                        return false;
                    }
                }
            }

            return true; //ko tìm đc tập bao nào là prob frequent itemset
        }
        return false;
    }
     */

    @Override
    public double calculateLowerBound(double expectSupport, double minProbabilisticConfidence) {
        return expectSupport - Math.sqrt(-2 * expectSupport * Math.log(1 - minProbabilisticConfidence));
    }

    @Override
    public double calculateUpperBound(double expectSupport, double minProbabilisticConfidence) {
        return (2 * expectSupport - Math.log(minProbabilisticConfidence) + Math.sqrt(Math.pow(Math.log(minProbabilisticConfidence), 2) - 8 * expectSupport * Math.log(minProbabilisticConfidence)))/2;
    }
}
