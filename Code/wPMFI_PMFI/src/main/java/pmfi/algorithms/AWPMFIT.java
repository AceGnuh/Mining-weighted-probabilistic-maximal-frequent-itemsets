package pmfi.algorithms;

import pmfi.entities.WeightedTable;
import pmfi.supports.FrequentItemset;
import pmfi.entities.UncertainDatabase;
import pmfi.supports.ApproximateProbabilisticFrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;

import java.util.*;

public class AWPMFIT<E> implements ProbabilisticMaximalFrequentItemsetTree {
    /**
     * Root of probabilistic maximal frequent itemset tree
     */
    private ItemsetTuple<E> root;
    private UncertainDatabase<E> uncertainDatabase;
    private double minimumSupport;
    private double minimumProbabilisticConfidence;
    private Set<E> distinctItemList;

    private WeightedTable<E> weightedTable;

    public AWPMFIT(UncertainDatabase<E> uncertainDatabase, WeightedTable<E> weightedTable, double minimumSupport, double minimumProbabilisticConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.weightedTable = weightedTable;
        this.distinctItemList = uncertainDatabase.getDistinctItem();
        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    /**
     * Implement algorithm to find all probabilistic maximal frequent itemsets
     * @return probabilistic maximal frequent item collection
     */
    @Override
    public Set<List<E>> findAllWPMFI(){
        Set<List<E>> probabilisticMaximalFrequentItemsetCollection = new HashSet<>();

        //step 1: get distinct item and sorted them by incremental order according to their expected support
        List<E> sortedItemList = this.getSortedItemList();

        //step 2: init root node with null value
        this.root = new ItemsetTuple<>();

        //step 3 - 5:
//        for(E item: sortedItemList){
//            ItemsetTuple<E> chilNode = new ItemsetTuple<>(item);
//
//            this.root.getChild().add(chilNode);
//
//            int returnValue = this.ApproximatePMFIM(chilNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);
//
//            // find last item in sorted list -> break loop
//            if(returnValue == 1) {
//                break;
//            }
//        }
        this.ApproximatePMFIM(root, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * Get distinct item in database and sorted increase by its expect support
     * @return  sorted item list order by expect support
     */
    public List<E> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemsetTupleList = new ArrayList<>();

        for(E distinctItem : this.distinctItemList){
            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, weightedTable, List.of(distinctItem));

            int support = frequentItemset.calculateSupport();

            //eliminate item with support < min support
            if(support < this.minimumSupport){
                continue;
            }

            //calc expected support and upper bound of item
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            //eliminate item with upper bound < min support
            if(upperBound < this.minimumSupport){
                continue;
            }

            //calc approximate probabilistic support of item
            ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset
                    = new ApproximateProbabilisticFrequentItemset<>(uncertainDatabase, weightedTable, List.of(distinctItem));
            int approximateProbabilisticSupport = approximateProbabilisticFrequentItemset
                    .calculateWeightedProbabilisticSupport(minimumProbabilisticConfidence);

            ItemsetTuple<E> itemsetTuple
                    = new ItemsetTuple<>(
                    List.of(distinctItem),
                    support,
                    expectSupport,
                    approximateProbabilisticSupport,
                    0,
                    upperBound,
                    new ArrayList<>()
            );

            sortedItemsetTupleList.add(itemsetTuple);
        }

        //sorted itemset tuple list by the increase of expect support
        sortedItemsetTupleList.sort((o1, o2) -> {
            int cmpProbabilisticSupport = Double.compare(o2.getProbabilisticSupport(), o1.getProbabilisticSupport());

            if(cmpProbabilisticSupport != 0){
                return cmpProbabilisticSupport;
            }

            return Double.compare(o2.getExpectSupport(), o1.getExpectSupport());
        });

        //get item by increase of expect support from sortedItemsetTupleList
        List<E> sortedItemList = new ArrayList<>();
        sortedItemsetTupleList.forEach((currItem) -> sortedItemList.add(currItem.getItemset().get(0)) );

        System.out.println("Sorted Item List");
        for(ItemsetTuple<E> itemsetTuple : sortedItemsetTupleList){
            System.out.println(itemsetTuple);
        }

        return sortedItemList;
    }

    /**
     * Apply algorithm to find itemset is probabilistic maximal frequent itemset
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemList
     */
    private void ApproximatePMFIM(ItemsetTuple<E> node, Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> sortedItemList){
        List<E> itemsetOfNode = node.getItemset();

        List<List<E>> itemJOrderLargerThanIList = this.getItemsetJOrderLargerThanI(node, sortedItemList);

        System.out.println();
        System.out.println("Current item: " + node.getItemset());
        System.out.println("Item J Order Larger Than I List: " + itemJOrderLargerThanIList );

        for (int i = 0; i < itemJOrderLargerThanIList.size(); i++) {
            List<E> itemsetJ = itemJOrderLargerThanIList.get(i);

            //---
            ItemsetTuple<E> tempNode = new ItemsetTuple<>(itemsetJ);
            List<ItemsetTuple<E>> tempChild = node.getChild();
            tempChild.add(tempNode);
            node.setChild(tempChild);
            //---

            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, weightedTable, tempNode.getItemset());

            int support = frequentItemset.calculateSupport();
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double lowerBound = frequentItemset.calculateLowerBound(expectSupport, this.minimumProbabilisticConfidence);
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            System.out.println("Current itemset:" + tempNode.getItemset());
            System.out.println("Support: " + support);
            System.out.println("Expected Support: " + expectSupport);
            System.out.println("Lower bound: " + lowerBound);
            System.out.println("Upper bound: " + upperBound);

            if (Math.min(support, upperBound) < this.minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                System.out.println("Pruned");

                continue;
            }

            if (lowerBound >= this.minimumSupport) {
                System.out.println("Frequent Node: " + tempNode);

                //calc recursion ApproximatePMFIM()
                ApproximatePMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

            } else {
                //calc approximate probabilistic support of item
                ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(uncertainDatabase, weightedTable, tempNode.getItemset());
                int approximateProbabilisticSupport = approximateProbabilisticFrequentItemset
                        .calculateWeightedProbabilisticSupport(minimumProbabilisticConfidence);

                System.out.println("Approximate probabilistic support: " + approximateProbabilisticSupport);

                if (approximateProbabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);

                    //calc recursion ApproximatePMFIM()
                    ApproximatePMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

                } else {
                    //eliminate node which is invalid condition
                    tempChild.remove(node.getChild().size() - 1);
                    node.setChild(tempChild);
                    System.out.println("Pruned");

                }
            }
        }

        if (node.getChild().isEmpty() && !isCollectionContainsItemset(probabilisticMaximalFrequentItemsetCollection, itemsetOfNode)) {
            //add itemset into ApproximatePMFI collection
            probabilisticMaximalFrequentItemsetCollection.add(itemsetOfNode);
            System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);
        }
    }

    /**
     * This method check itemset is covered by any other itemsets in collection
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param itemsetJ
     * @return whether itemset is covered by any other itemsets in collection
     */
    private boolean isCollectionContainsItemset(Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> itemsetJ) {
        for(List<E> itemset : probabilisticMaximalFrequentItemsetCollection){
            if(new HashSet<>(itemset).containsAll(itemsetJ)){
                return true;
            }
        }

        return false;
    }

    /**
     * Get Items J order larger than current item I in sorted item list
     * @param itemsetI
     * @param sortedItemList
     * @return Item order larger than current item in sorted item list
     */
    private List<List<E>> getItemsetJOrderLargerThanI(ItemsetTuple<E> itemsetI, List<E> sortedItemList){
        List<List<E>> itemsetJOrderLargerThanI = new ArrayList<>();

        int itemISize = itemsetI.getItemset().size();
        int indexOfLastItemI = -1;

        //get index of last item of itemset in Sorted Item List
        if (itemISize > 0){
            indexOfLastItemI = sortedItemList.lastIndexOf(itemsetI.getItemset().get(itemISize - 1));
        }

        //return [] if last item in itemset is last item in Sorted Item List
        if(indexOfLastItemI == sortedItemList.size() - 1){
            return new ArrayList<>();
        }

        //get Item J larger than curr Item I in Sorted Item List
        for(int i = indexOfLastItemI + 1; i < sortedItemList.size(); i++){
            List<E> tempItem = new ArrayList<>(itemsetI.getItemset());
            tempItem.add(sortedItemList.get(i));
            itemsetJOrderLargerThanI.add(tempItem);
        }

        return itemsetJOrderLargerThanI;
    }

    /**
     * Traversal the PMFI tree by DFS
     * @param node
     */
    private void preOrder(ItemsetTuple<E> node){
        if(node != null){
            System.out.println("Node: " + node.getItemset());
            for(ItemsetTuple<E> childNode: node.getChild()){
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
