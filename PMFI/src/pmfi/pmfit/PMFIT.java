package pmfi.pmfit;

import pmfi.entities.FrequentItemset;
import pmfi.entities.PossibleWorld;
import pmfi.entities.SupportProbabilisticVector;
import pmfi.entities.UncertainDatabase;
import pmfi.helper.ListHelper;
import pmfi.helper.SetHelper;

import java.util.*;

public class PMFIT<E> {
    private Node<E> root;
    private final PossibleWorld<E> possibleWorld;
    private final UncertainDatabase<E> uncertainDatabase;
    private final int minimumSupport;
    private final double minimumProbabilisticConfidence;
    List<List<E>> distinctItemList;

    public PMFIT(UncertainDatabase<E> uncertainDatabase, int minimumSupport, double minimumProbabilisticConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.possibleWorld = new PossibleWorld<>(uncertainDatabase).build();
        this.distinctItemList = uncertainDatabase.getDistinctItem();

        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    public Node<E> getRoot() {
        return root;
    }

    public List<ItemsetTuple<E>> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemList = new ArrayList<>();

        for(List<E> distinctItem : this.distinctItemList){
            SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
            FrequentItemset<E> frequentItemset = new FrequentItemset<E>(this.uncertainDatabase, supportProbabilisticVector, distinctItem);

            int support = frequentItemset.calculateSupport();

            if(support < this.minimumSupport){
                continue;
            }

            double expectSupport = frequentItemset.calculateExpectFrequentItemset();

            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            if(upperBound < this.minimumSupport){
                continue;
            }

            //int probabilisticSupport = 0; // frequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);
            //double lowerBound = frequentItemset.calculateLowerBound(this.minimumSupport, this.minimumProbabilisticConfidence);

            ItemsetTuple<E> itemsetTuple
                    = new ItemsetTuple<>(
                            distinctItem,
                            support,
                            expectSupport,
                            0,
                            0,
                            upperBound
            );

            sortedItemList.add(itemsetTuple);
        }

        //sorted by increase of expect support
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

    private void PMFIM(Node<E> node, List<List<E>> probabilisticMaximalFrequentItemsetCollection, List<ItemsetTuple<E>> sortedItemList, List<E> sortedItemValueList){

        List<List<E>> itemJOrderLargerThanIList = this.getItemJOrderLargerThanI(node.getItem(), sortedItemValueList);

        System.out.println();
        System.out.println("Current item: " + node.getItem().getItem());
        System.out.println("Item J Order Larger Than I List: " + itemJOrderLargerThanIList );


        for (List<E> itemJ : itemJOrderLargerThanIList) {

            List<E> itemJList = new ArrayList<>(itemJ);

            if (ListHelper.isSubListAtEnd(sortedItemValueList, itemJList)) {
                SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
                FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, supportProbabilisticVector, itemJList);

                if (frequentItemset.isProbabilisticMaximalFrequentItemset(this.minimumSupport, this.minimumProbabilisticConfidence)) {
                    System.out.println("sos maximal");
                    break;
                }
            }

            Set<E> IUnionJ = new HashSet<>();
            IUnionJ.addAll(node.getItem().getItem());
            IUnionJ.addAll(itemJList);

            Node<E> tempNode = new Node<>(new ItemsetTuple<>(new ArrayList<>(IUnionJ)));

            List<Node<E>> tempChild = node.getChild();
            tempChild.add(tempNode);

            node.setChild(tempChild);

            System.out.println("I Item: " + node.getItem().getItem());
            System.out.println("J Item: " + itemJList);
            System.out.println("Node of I: " + node);
            System.out.println("I union J: " + IUnionJ);
            System.out.println("Node of I union J: " + tempNode);

            if (probabilisticMaximalFrequentItemsetCollection.contains(IUnionJ)) {
                System.out.println("Frequent Node: " + node);
                PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList, sortedItemValueList);
                continue;
            }

            SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, supportProbabilisticVector, tempNode.getItem().getItem());

            double expectSupport = frequentItemset.calculateExpectFrequentItemset();
            int support = frequentItemset.calculateSupport();
            double lowerBound = frequentItemset.calculateLowerBound(this.minimumSupport, this.minimumProbabilisticConfidence);
            double upperBound = frequentItemset.calculateUpperBound(this.minimumSupport, this.minimumProbabilisticConfidence);

            System.out.println("Support: " + support);
            System.out.println("Lower bound: " + lowerBound);
            System.out.println("Upper bound: " + upperBound);

            if (support < this.minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                continue;
            }

            if (upperBound <= this.minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                continue;
            }

            if (lowerBound >= this.minimumSupport) {
                System.out.println("Frequent Node: " + tempNode);
                PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList, sortedItemValueList);
            } else {
                int probabilisticSupport = frequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);

                System.out.println("Probabilistic support: " + probabilisticSupport);

                if (probabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);
                    PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList, sortedItemValueList);
                } else {
                    tempChild.remove(node.getChild().size() - 1);
                    node.setChild(tempChild);
                }

            }
        }

        if (node.getChild().isEmpty() && !probabilisticMaximalFrequentItemsetCollection.contains(node.getItem().getItem())) {
            probabilisticMaximalFrequentItemsetCollection.add(node.getItem().getItem());
            System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);
        }
    }

    public List<List<E>> findAllPMFI(){
        //step 1:
        List<ItemsetTuple<E>> sortedItemList = this.getSortedItemList();

        System.out.println();
        System.out.println("Sorted Items List");
        for(ItemsetTuple<E> itemsetTuple : sortedItemList){
            System.out.println(itemsetTuple);
        }

        List<E> sortedItemValueList = new ArrayList<>();
        for(ItemsetTuple<E> sortedItem : sortedItemList){
            sortedItemValueList.add(sortedItem.getItem().get(0));
        }

        System.out.println("Sorted item value: " + sortedItemValueList);

        //step 2:
        this.root = new Node<>(new ItemsetTuple<>(new ArrayList<>()));

        //step 3 - 5:
        List<List<E>> probabilisticMaximalFrequentItemsetCollection = new ArrayList<>();

        this.PMFIM(this.root, probabilisticMaximalFrequentItemsetCollection, sortedItemList, sortedItemValueList);

        return probabilisticMaximalFrequentItemsetCollection;
    }

    private List<List<E>> getItemJOrderLargerThanI(ItemsetTuple<E> itemI, List<E> sortedItemList){
        List<List<E>> itemJOrderLargerThanI = new ArrayList<>();

        int itemISize = itemI.getItem().size();

        int indexOfLastItemI = -1;

        if (itemISize > 0){
            indexOfLastItemI = sortedItemList.lastIndexOf(itemI.getItem().get(itemISize - 1));
        }

//        for(int i = indexOfLastItemI + 1; i < sortedItemList.size(); i++){
//            List<E> tempItem = new ArrayList<>(itemI.getItem());
//            tempItem.add(sortedItemList.get(i));
//            itemJOrderLargerThanI.add(tempItem);
//        }

        return null;
    }
}
