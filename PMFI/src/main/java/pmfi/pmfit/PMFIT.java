package pmfi.pmfit;

import pmfi.entities.supports.FrequentItemset;
import pmfi.entities.brute_force.PossibleWorld;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.supports.ProbabilisticFrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.helper.ListHelper;

import java.util.*;

/**
 *
 * @param <E> data type of itemset
 */
public class PMFIT<E> implements ProbabilisticMaximalFrequentItemsetTree<E> {
    private Node<E> root;
    private final UncertainDatabase<E> uncertainDatabase;
    private final double minimumSupport;
    private final double minimumProbabilisticConfidence;
    private final List<List<E>> distinctItemList;

    public PMFIT(UncertainDatabase<E> uncertainDatabase, double minimumSupport, double minimumProbabilisticConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.distinctItemList = uncertainDatabase.getDistinctItem();

        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    /**
     *
     * @return probabilistic maximal frequent item collection
     */
    @Override
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

        //step 2:
        this.root = new Node<>(new ItemsetTuple<>(new ArrayList<>()));

        //step 3 - 5:
        List<List<E>> probabilisticMaximalFrequentItemsetCollection = new ArrayList<>();

        List<Node<E>> childNodeList = new ArrayList<>();
        for(ItemsetTuple<E> chilNode: sortedItemList){
            childNodeList.add(new Node<>(chilNode));
        }

//        this.root.setChild(childNodeList);

        for(int i = 0; i < childNodeList.size(); i++){
            Node<E> currChildNode = childNodeList.get(i);
            this.root.getChild().add(currChildNode);

            int returnValue = this.PMFIM(currChildNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

            if(returnValue == 1) {
                break;
            }
//            System.out.println();
//            System.out.println(this.root.getChild().get(i) + " return value: " + returnValue);
//            System.out.println();

//            if (returnValue == 1){
                //this.root.getChild().subList(i + 1, this.root.getChild().size() - 1).clear();
//                for(int j = this.root.getChild().size() - 1; j > i; j--){
//                    this.root.getChild().remove(j);
//                }
//                break;
//            }
        }

//        this.PMFIM(this.root, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * @return  sorted item list order by expect support

     */
    private List<ItemsetTuple<E>> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemList = new ArrayList<>();

        for(List<E> distinctItem : this.distinctItemList){
            FrequentItemset<E> frequentItemset = new FrequentItemset<E>(this.uncertainDatabase, distinctItem);

            int support = frequentItemset.calculateSupport();

            //eliminate item with support < min support
            if(support < this.minimumSupport){
                continue;
            }

            double expectSupport = frequentItemset.calculateExpectedSupport();
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            //eliminate item with upper bound < min support
            if(upperBound < this.minimumSupport){
                continue;
            }

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

        //sorted by the increase of expect support
        sortedItemList.sort(new Comparator<ItemsetTuple<E>>() {
            @Override
            public int compare(ItemsetTuple<E> o1, ItemsetTuple<E> o2) {
                return Double.compare(o1.getExpectSupport(), o2.getExpectSupport());
            }
        });

        return sortedItemList;
    }

    /**
     *
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemValueList
     * @return
     */
    private int PMFIM(Node<E> node, List<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> sortedItemValueList){
        int result = -1;

        List<List<E>> itemJOrderLargerThanIList = this.getItemJOrderLargerThanI(node.getItem(), sortedItemValueList);

        System.out.println();
        System.out.println("Current item: " + node.getItem().getItem());
        System.out.println("Item J Order Larger Than I List: " + itemJOrderLargerThanIList );

        for (int i = 0; i < itemJOrderLargerThanIList.size(); i++) {

            List<E> itemJ = itemJOrderLargerThanIList.get(i);
            List<E> itemJList = new ArrayList<>(itemJ);

            //---
            Node<E> tempNode = new Node<>(new ItemsetTuple<>(new ArrayList<>(itemJList)));

            List<Node<E>> tempChild = node.getChild();
            tempChild.add(tempNode);

            node.setChild(tempChild);
            //---

            if (ListHelper.isSubListAtEnd(sortedItemValueList, itemJList))
            {
                /*
                SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
                FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, supportProbabilisticVector, itemJList);
                */

                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);
                if (probabilisticFrequentItemset.isProbabilisticMaximalFrequentItemset(this.minimumSupport, this.minimumProbabilisticConfidence)) {
                    System.out.println("sos maximal");

                    if (!probabilisticMaximalFrequentItemsetCollection.contains(itemJList)) {
                        probabilisticMaximalFrequentItemsetCollection.add(itemJList);
                        System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

                        return 1;
                    }
                }
            }

            System.out.println("I Item: " + node.getItem().getItem());
            System.out.println("J Item: " + itemJList);
            System.out.println("Node of I: " + node);
            System.out.println("I union J: " + itemJList);
            System.out.println("Node of I union J: " + tempNode);

            if (probabilisticMaximalFrequentItemsetCollection.contains(itemJList)) {
                System.out.println("Frequent Node: " + node);
                result = PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

                System.out.println(node.getItem().getItem() + "return value: " + result);

                if (result == -1){
                    continue;
                }

                if (result == 0 || result == 1){
                    break;
                }

                continue;
            }

            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, tempNode.getItem().getItem());

            int support = frequentItemset.calculateSupport();
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double lowerBound = frequentItemset.calculateLowerBound(expectSupport, this.minimumProbabilisticConfidence);
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            System.out.println("Support: " + support);
            System.out.println("Lower bound: " + lowerBound);
            System.out.println("Upper bound: " + upperBound);

            if (support < this.minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                System.out.println("Pruned");

                continue;
            }

            if (upperBound <= this.minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                System.out.println("Pruned");

                continue;
            }

            if (lowerBound >= this.minimumSupport) {
                System.out.println("Frequent Node: " + tempNode);
                result = PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

                System.out.println(node.getItem().getItem() + "return value: " + result);

                if (result == -1){
                    continue;
                }

                if (result == 0 || result == 1){
                    break;
                }


            } else {
                /*
                SupportProbabilisticVector<E> supportProbabilisticVector = new SupportProbabilisticVector<>(this.possibleWorld, this.uncertainDatabase);
                frequentItemset.setSummedSupportProbabilisticItemList(supportProbabilisticVector);
                */
                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);

                int probabilisticSupport = probabilisticFrequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);


                System.out.println("Probabilistic support: " + probabilisticSupport);

                if (probabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);
                    result = PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

                    System.out.println(node.getItem().getItem() + " return value: " + result);

                    if (result == -1){
                        continue;
                    }

                    if (result == 0 || result == 1){
                        break;
                    }

                } else {
                    tempChild.remove(node.getChild().size() - 1);
                    node.setChild(tempChild);
                    System.out.println(node.getItem().getItem() + " return value: " + result);
                    System.out.println("Pruned");

                }
            }
        }

        if (node.getChild().isEmpty() && !probabilisticMaximalFrequentItemsetCollection.contains(node.getItem().getItem())) {
            probabilisticMaximalFrequentItemsetCollection.add(node.getItem().getItem());
            System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

            if(!itemJOrderLargerThanIList.isEmpty()){
                if(itemJOrderLargerThanIList.get(itemJOrderLargerThanIList.size() - 1).equals(node.getItem().getItem())){
                    return 0;
                }
                else {
                    return -1;
                }
            }
            return 0;
        }
        return result;
    }



    /**
     *
     * @param itemI
     * @param sortedItemList
     * @return
     */
    private List<List<E>> getItemJOrderLargerThanI(ItemsetTuple<E> itemI, List<E> sortedItemList){
        List<List<E>> itemJOrderLargerThanI = new ArrayList<>();

        int itemISize = itemI.getItem().size();
        int indexOfLastItemI = -1;

        if (itemISize > 0){
            indexOfLastItemI = sortedItemList.lastIndexOf(itemI.getItem().get(itemISize - 1));
        }

        if(indexOfLastItemI == sortedItemList.size() - 1){
            return itemJOrderLargerThanI;
        }

        for(int i = indexOfLastItemI + 1; i < sortedItemList.size(); i++){
            List<E> tempItem = new ArrayList<>(itemI.getItem());
            tempItem.add(sortedItemList.get(i));
            itemJOrderLargerThanI.add(tempItem);
        }

        return itemJOrderLargerThanI;
    }

    /**
     * Traversal the PMFI tree by DFS
     * @param node
     */
    private void preOrder(Node<E> node){
        if(node != null){
            System.out.println("Node: " + node.getItem().getItem());
            for(Node<E> childNode: node.getChild()){
                preOrder(childNode);
            }
        }
    }

    /**
     * Traversal the PMFI Tree by DFS from root node
     */
    @Override
    public void preOrder(){
        preOrder(this.root);
    }
}
