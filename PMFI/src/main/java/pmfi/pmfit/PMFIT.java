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
    private final Set<List<E>> distinctItemList;

    public PMFIT(UncertainDatabase<E> uncertainDatabase, double minimumSupport, double minimumProbabilisticConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.distinctItemList = uncertainDatabase.getDistinctItem();
        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    /**
     * Implement algorithm to find all probabilistic maximal frequent itemsets
     * @return probabilistic maximal frequent item collection
     */
    @Override
    public Set<List<E>> findAllPMFI(){
        Set<List<E>> probabilisticMaximalFrequentItemsetCollection = new HashSet<>();

        //step 1:
        List<ItemsetTuple<E>> sortedItemList = this.getSortedItemList();
        List<E> sortedItemValueList = new ArrayList<>();

        System.out.println("\nSorted Items List: ");
        for(ItemsetTuple<E> itemsetTuple : sortedItemList){
            System.out.println(itemsetTuple);
        }

        for(ItemsetTuple<E> sortedItem : sortedItemList){
            sortedItemValueList.add(sortedItem.getItem().get(0));
        }

        //step 2:
        this.root = new Node<>(new ItemsetTuple<>(new ArrayList<>()));

        //step 3 - 5:
        for(ItemsetTuple<E> item: sortedItemList){
            Node<E> chilNode = new Node<>(item);

            this.root.getChild().add(chilNode);

            int returnValue = this.PMFIM(chilNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

            if(returnValue == 1) { // find last item in sorted list
                break;
            }
        }

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * Get distinct item in database and sorted increase by its expect support
     * @return  sorted item list order by expect support
     */
    public List<ItemsetTuple<E>> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemList = new ArrayList<>();

        for(List<E> distinctItem : this.distinctItemList){
            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, distinctItem);

            int support = frequentItemset.calculateSupport();

            //eliminate item with support < min support
            if(support < this.minimumSupport){
                continue;
            }

            //calc expected support and upper bound of item
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            if(expectSupport <= 0){
                continue;
            }

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
        sortedItemList.sort((o1, o2) -> Double.compare(o1.getExpectSupport(), o2.getExpectSupport()));

        return sortedItemList;
    }

    /**
     * Apply algorithm to find itemset is probabilistic maximal frequent itemset
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemValueList
     * @return
     * -1 if it is infrequent item;
     * 0 if it is probabilistic frequent item;
     * 1 if it is probabilistic maximal frequent item;
     */
    private int PMFIM(Node<E> node, Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> sortedItemValueList){
        int result = -1;

        List<List<E>> itemJOrderLargerThanIList = this.getItemJOrderLargerThanI(node.getItem(), sortedItemValueList);

        System.out.println();
        System.out.println("Current item: " + node.getItem().getItem());
        System.out.println("Item J Order Larger Than I List: " + itemJOrderLargerThanIList );

        for (int i = 0; i < itemJOrderLargerThanIList.size(); i++) {
            List<E> itemJ = itemJOrderLargerThanIList.get(i);
            List<E> itemJList = new ArrayList<>(itemJ);

            //---
            ItemsetTuple<E> itemsetTuple = new ItemsetTuple<>(itemJList);
            Node<E> tempNode = new Node<>(itemsetTuple);

            List<Node<E>> tempChild = node.getChild();
            tempChild.add(tempNode);
            node.setChild(tempChild);
            //---

            if (ListHelper.isSubListAtEnd(sortedItemValueList, itemJList))
            {
                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, distinctItemList, itemJList);

                if (probabilisticFrequentItemset.isProbabilisticMaximalFrequentItemset(this.minimumSupport, this.minimumProbabilisticConfidence, sortedItemValueList)) {
                    System.out.println("Found maximal!!!");

                    if (!probabilisticMaximalFrequentItemsetCollection.contains(itemJList)) {
                        probabilisticMaximalFrequentItemsetCollection.add(itemJList);
                        System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

                        return 1;
                    }
                }
            }

            //System.out.println("J Item: " + itemJList);
            System.out.println("I union J: " + itemJList);

            if (probabilisticMaximalFrequentItemsetCollection.contains(itemJList)) {
            //if(isCoveredByPMFICollection(probabilisticMaximalFrequentItemsetCollection, itemJList)){
                System.out.println("Frequent Node: " + node);
                result = PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

                System.out.println(node.getItem().getItem() + "return value: " + result);


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
            System.out.println("Expected Support: " + expectSupport);
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

                System.out.println(node.getItem().getItem() + " return value: " + result);

                if (result == 0 || result == 1){
                    break;
                }

//                if (result == 1){
//                    break;
//                }

            } else {
                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);
                int probabilisticSupport = probabilisticFrequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);

                System.out.println("Probabilistic support: " + probabilisticSupport);

                if (probabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);
                    result = PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

                    System.out.println(node.getItem().getItem() + " return value: " + result);

                    if (result == 0 || result == 1){
                        break;
                    }
//                    if (result == 1){
//                        break;
//                    }

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
//
//    private boolean isCoveredByPMFICollection(Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> currItem){
//        for(List<E> itemPMFI : probabilisticMaximalFrequentItemsetCollection){
//            Set<E> itemPMFISet = new HashSet<>(itemPMFI);
//            Set<E> currItemSet = new HashSet<>(currItem);
//
//            if(itemPMFISet.containsAll(currItemSet)){
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Get Items J order larger than current item I in sorted item list
     * @param itemI
     * @param sortedItemList
     * @return Item order larger than current item in sorted item list
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
    public void preOrder(){
        preOrder(this.root);
    }
}
