package pmfi.entities;

import pmfi.functions.ISupportProbabilisticVector;

import java.util.*;

public class SupportProbabilisticVector<E> implements ISupportProbabilisticVector<E> {
    private PossibleWorld<E> possibleWorld;
    private UncertainDatabase uncertainDatabase;

    public SupportProbabilisticVector(PossibleWorld<E> possibleWorld, UncertainDatabase uncertainDatabase) {
        this.possibleWorld = possibleWorld;
        this.uncertainDatabase = uncertainDatabase;
    }

    public PossibleWorld<E> getPossibleWorld() {
        return possibleWorld;
    }

    public void setPossibleWorld(PossibleWorld<E> possibleWorld) {
        this.possibleWorld = possibleWorld;
    }

    public UncertainDatabase getUncertainDatabase() {
        return uncertainDatabase;
    }

    public void setUncertainDatabase(UncertainDatabase uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
    }

    public List<SupportProbabilisticItem> calculateSupportProbabilistic(List<E> itemInput) {
        List<SupportProbabilisticItem> supportProbabilisticVector = new ArrayList<>();

        for(PossibleWorldItem<E> possibleWorldItem : this.possibleWorld.getPossibleWorld()){
            int countItemAppear = 0;

            for(List<E> possibleWorldItemData : possibleWorldItem.getListPossibleWorldItem() ){

                Set<E> possibleWorldItemDataSet = new HashSet<>(possibleWorldItemData);
                Set<E> itemInputSet = new HashSet<>(itemInput);

                if(possibleWorldItemDataSet.containsAll(itemInputSet)){
                    countItemAppear++;
                }
            }

            SupportProbabilisticItem supportProbabilisticItem
                    = new SupportProbabilisticItem(countItemAppear, possibleWorldItem.getProbability());

            supportProbabilisticVector.add(supportProbabilisticItem);
        }

        return supportProbabilisticVector;
    }

    public void displaySupportProbabilistic(List<E> itemInput){
        List<SupportProbabilisticItem> supportProbabilisticVector = calculateSupportProbabilistic(itemInput);

        for(SupportProbabilisticItem supportProbabilisticItem : supportProbabilisticVector){
            System.out.println(supportProbabilisticItem);
        }
    }

    public List<SummedSupportProbabilisticItem> calculateSummedSupportProbabilistic(List<E> itemInput){
        List<SummedSupportProbabilisticItem> summedSupportProbabilisticVector = new ArrayList<>();

        List<SupportProbabilisticItem> supportProbabilisticVector = this.calculateSupportProbabilistic(itemInput);

        Map<Integer, Double> summedSupportProbMap = new HashMap<>();

        for(SupportProbabilisticItem supportProbabilisticItem : supportProbabilisticVector){
            Integer frequent = supportProbabilisticItem.getFrequent();
            Double prob = supportProbabilisticItem.getProbabilistic();

            if(summedSupportProbMap.containsKey(supportProbabilisticItem.getFrequent())){
                summedSupportProbMap.put(frequent, summedSupportProbMap.get(frequent) + prob);
            }
            else {
                summedSupportProbMap.put(frequent, prob);
            }
        }

        for(Integer frequent: summedSupportProbMap.keySet()){
            Double prob = summedSupportProbMap.get(frequent);

            summedSupportProbabilisticVector.add(new SummedSupportProbabilisticItem(frequent, prob));
        }

        return summedSupportProbabilisticVector;
    }

    /*
    public void displaySummedSupportProbabilistic(List<E> itemInput){
        List<SummedSupportProbabilisticItem> summedSupportProbabilisticVector = calculateSummedSupportProbabilistic(itemInput);

        for(SummedSupportProbabilisticItem supportProbabilisticItem : summedSupportProbabilisticVector){
            System.out.println(supportProbabilisticItem);
        }
    }

    public double calculateExpectFrequentItemset(List<E> itemInput){
        double expectFrequentItemset = 0.0;

        List<SummedSupportProbabilisticItem> summedSupportProbabilisticVector = calculateSummedSupportProbabilistic(itemInput);

        for (SummedSupportProbabilisticItem probabilisticItem : summedSupportProbabilisticVector){
            expectFrequentItemset += probabilisticItem.getFrequent()*probabilisticItem.getProbabilistic();
        }

        return (double) Math.round(expectFrequentItemset*1000)/1000;
    }

    public boolean isExpectFrequentItemset(List<E> itemInput, int minSupport){
        return calculateExpectFrequentItemset(itemInput) > minSupport;
    }

    public double calculateProbabilisticFrequentItemset(List<E> itemInput, int minSupport){
        double probabilisticFrequentItemset = 0.0;

        List<SupportProbabilisticItem> supportProbabilisticVector = this.calculateSupportProbabilistic(itemInput);
        for(SupportProbabilisticItem supportProbabilisticItem : supportProbabilisticVector){
            if(supportProbabilisticItem.getFrequent() >= minSupport){
                probabilisticFrequentItemset += supportProbabilisticItem.getProbabilistic();
            }
        }

        return (double) Math.round(probabilisticFrequentItemset*1000)/1000;

    }

    public int calculateProbabilisticSupport(List<E> itemInput, double minProbabilisticConfidence){
        List<SummedSupportProbabilisticItem> summedSupportProbabilisticVector = calculateSummedSupportProbabilistic(itemInput);
        int numItemInputOccurrence = summedSupportProbabilisticVector.size();

        for(int i = numItemInputOccurrence + 1; i > 0; i--){
            double currProbabilisticFrequentItemset = calculateProbabilisticFrequentItemset(itemInput, i);

            if (currProbabilisticFrequentItemset > minProbabilisticConfidence){
                return i;
            }
        }

        return 0;
    }

    public boolean isProbabilisticFrequentItemset(List<E> itemInput, int minSupport, double minProbabilisticConfidence){
        return this.calculateProbabilisticSupport(itemInput, minProbabilisticConfidence) >= minSupport;
    }

     */
}
