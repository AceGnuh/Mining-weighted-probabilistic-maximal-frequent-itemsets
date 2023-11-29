package pmfi.pmfit;

import pmfi.entities.FrequentItemset;
import pmfi.entities.PossibleWorld;
import pmfi.entities.SupportProbabilisticVector;
import pmfi.entities.UncertainDatabase;

import java.util.*;

public class PMFIT<E> {
    private Node<E> root;
    private PossibleWorld<E> possibleWorld;
    private UncertainDatabase uncertainDatabase;
    private int minimumSupport;
    private double minimumProbabilisticConfidence;

    public PMFIT(UncertainDatabase uncertainDatabase, int minimumSupport, double minimumProbabilisticConfidence) {
        this.root = new Node<>(null);
        this.uncertainDatabase = uncertainDatabase;
        this.possibleWorld = (PossibleWorld<E>) new PossibleWorld<>(uncertainDatabase).build();

        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    public Node<E> getRoot() {
        return root;
    }

//    public void setRoot(Node<E> root) {
//        this.root = root;
//    }

    public List<ItemsetTuple<E>> getAllPMFI(){
        List<ItemsetTuple<E>> probabilisticMaximalFrequentItemsetList = new ArrayList<>();
        List<ItemsetTuple<E>> sortedItemList =  this.getSortedItemList();
        return null;
    }

    public List<ItemsetTuple<E>> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemList = new ArrayList<>();

        List<E> distinctItemList = this.uncertainDatabase.getDistinctItem();

        for(E distinctItem : distinctItemList){
            SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
            FrequentItemset frequentItemset = new FrequentItemset(this.uncertainDatabase, supportProbabilisticVector, Arrays.asList(distinctItem));

            ItemsetTuple<E> itemsetTuple = new ItemsetTuple<>();
            itemsetTuple.setItem(distinctItem);
            itemsetTuple.setSupport(frequentItemset.calculateSupport());
            itemsetTuple.setExpectSupport(frequentItemset.calculateExpectFrequentItemset());
            itemsetTuple.setProbabilisticSuppport(frequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence));
            itemsetTuple.setLowerBound(frequentItemset.calculateLowerBound(this.minimumSupport, this.minimumProbabilisticConfidence));
            itemsetTuple.setUpperBound(frequentItemset.calculateUpperBound(this.minimumSupport, this.minimumProbabilisticConfidence));

            sortedItemList.add(itemsetTuple);
        }

        Collections.sort(sortedItemList, new Comparator<ItemsetTuple<E>>() {
            @Override
            public int compare(ItemsetTuple<E> o1, ItemsetTuple<E> o2) {
                if(o1.getExpectSupport() > o2.getExpectSupport()){
                    return 1;
                }
                else {
                    if(o1.getExpectSupport() < o2.getExpectSupport()){
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        });

        return sortedItemList;
    }

}
