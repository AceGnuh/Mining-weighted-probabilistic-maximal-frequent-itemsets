package pmfi.entities;

import pmfi.functions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrequentItemset<E> implements ISupport, IBound {
    private final UncertainDatabase<E> uncertainDatabase;
    private SupportProbabilisticVector<E> supportProbabilisticVector;
    private final List<E> inputItem;
    private List<SupportProbabilisticItem> supportProbabilisticItemList;
    private List<SummedSupportProbabilisticItem> summedSupportProbabilisticItemList;

    public FrequentItemset( UncertainDatabase<E> uncertainDatabase, SupportProbabilisticVector<E> supportProbabilisticVector, List<E> inputItem) {
        this.uncertainDatabase = uncertainDatabase;
        this.supportProbabilisticVector = supportProbabilisticVector;
        this.inputItem = inputItem;

        //this.supportProbabilisticItemList = supportProbabilisticVector.calculateSupportProbabilistic(inputItem);
        this.summedSupportProbabilisticItemList = supportProbabilisticVector.calculateSummedSupportProbabilistic(inputItem);
    }

    public FrequentItemset( UncertainDatabase<E> uncertainDatabase, List<E> inputItem)
    {
        this.uncertainDatabase = uncertainDatabase;
        this.inputItem = inputItem;
    }

    public List<SummedSupportProbabilisticItem> getSummedSupportProbabilisticItemList() {
        return summedSupportProbabilisticItemList;
    }

    public void setSummedSupportProbabilisticItemList(SupportProbabilisticVector<E> supportProbabilisticVector) {
        this.supportProbabilisticVector = supportProbabilisticVector;
        this.summedSupportProbabilisticItemList = supportProbabilisticVector.calculateSummedSupportProbabilistic(inputItem);
    }

    @Override
    public int calculateSupport() {
        int support = 0;

        for(UncertainTransaction<E> uncertainTransaction: this.uncertainDatabase.getUncertainTransactions()){
            List<E> currItemList = new ArrayList<>();

            for(UncertainItemset<E> uncertainItemset : uncertainTransaction.getTransaction()){
                currItemList.add(uncertainItemset.getItem());
            }

            if(new HashSet<>(currItemList).containsAll(this.inputItem)){
                support++;
            }
        }
        return support;
    }

//    @Override
//    public double calculateExpectFrequentItemset() {
//        double expectFrequentItemset = 0.0;
//
//        for (SummedSupportProbabilisticItem probabilisticItem : this.summedSupportProbabilisticItemList){
//            expectFrequentItemset += probabilisticItem.getFrequent()*probabilisticItem.getProbabilistic();
//        }
//
//        return (double) Math.round(expectFrequentItemset*1000)/1000;
//    }

    @Override
    public double calculateExpectFrequentItemset() {
        double expectFrequentItemset = 0.0;

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase.getUncertainTransactions()){
//            List<E> itemTransactionList = new ArrayList<>();
//            for(UncertainItemset<E> itemTransaction : uncertainTransaction.getTransaction()){
//                itemTransactionList.add(itemTransaction.getItem());
//            }

            //if(itemTransactionList.containsAll(this.inputItem)){
                double probInputItem = 1.0;

                for(E inputItemData: this.inputItem){
                    probInputItem *= uncertainTransaction.getProbabilistic(inputItemData);
                    //int indexItemInputInUncertainItemset = itemTransactionList.indexOf(itemInputData);
                    //probInputItem *= uncertainTransaction.getTransaction().get(indexItemInputInUncertainItemset).getProbability();
                }

                expectFrequentItemset += probInputItem;
            //}
        }

        return (double) Math.round(expectFrequentItemset*1000)/1000;
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
        double result = expectSupport - Math.sqrt(-2 * expectSupport * Math.log(1 - minProbabilisticConfidence));
        return (double) Math.round(result*10) / 10;
    }

    @Override
    public double calculateUpperBound(double expectSupport, double minProbabilisticConfidence) {
        double result = (2 * expectSupport - Math.log(minProbabilisticConfidence) + Math.sqrt(Math.pow(Math.log(minProbabilisticConfidence), 2) - 8 * expectSupport * Math.log(minProbabilisticConfidence)))/2;
        return (double) Math.round(result*10) / 10;
    }
}
