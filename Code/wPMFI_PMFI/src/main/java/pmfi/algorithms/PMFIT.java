package pmfi.algorithms;

import pmfi.entities.UncertainDatabase;
import pmfi.entities.WeightedTable;
import pmfi.supports.FrequentItemset;
import pmfi.supports.ProbabilisticFrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @param <E> type of items
 */
public class PMFIT<E> implements ProbabilisticMaximalFrequentItemsetTree<E> {
    /**
     * Root of probabilistic maximal frequent itemset tree
     */
    private ItemsetTuple<E> root;
    private UncertainDatabase<E> uncertainDatabase;
    private WeightedTable<E> weightedTable;
    private double minimumSupport;
    private double minimumProbabilisticConfidence;
    private Set<E> distinctItemList;

    public PMFIT(UncertainDatabase<E> uncertainDatabase, WeightedTable<E> weightedTable, double minimumSupport, double minimumProbabilisticConfidence) {
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
    public Set<List<E>> findAllPMFI(){
        Set<List<E>> probabilisticMaximalFrequentItemsetCollection = new HashSet<>();

        //step 1: get distinct item and sorted them by incremental order according to their expected support
        List<E> sortedItemList = this.getSortedItemList();

        //step 2: init root node with null value
        this.root = new ItemsetTuple<>();

        //step 3 - 5:

        //Set<E> itemIsFrequent = new HashSet<>();
        this.PMFIM(root, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * Get distinct item in database and sorted increase by its expect support
     * @return  sorted item list order by expect support
     */
    public List<E> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemsetTupleList = new ArrayList<>();

        for(E distinctItem : this.distinctItemList){
            List<E> distinctItemset = List.of(distinctItem);

            FrequentItemset<E> frequentItemset
                    = new FrequentItemset<>(this.uncertainDatabase, weightedTable, distinctItemset);

            int support = frequentItemset.calculateSupport();

            if(support < minimumSupport){
                continue;
            }

            //calc expected support, lower bound and upper bound of distinct items
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double lowerBound = frequentItemset.calculateLowerBound(
                    expectSupport,
                    minimumProbabilisticConfidence
            );
            double upperBound = frequentItemset.calculateUpperBound(
                    expectSupport,
                    minimumProbabilisticConfidence
            );

            ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(uncertainDatabase, weightedTable, distinctItemset);
            int probabilisticSupport = (int) probabilisticFrequentItemset.calculateWeightedProbabilisticSupport(minimumProbabilisticConfidence);

            ItemsetTuple<E> itemsetTuple
                    = new ItemsetTuple<>(
                            distinctItemset,
                            support,
                            expectSupport,
                            probabilisticSupport,
                            lowerBound,
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
     * Implement algorithm to find itemset is probabilistic maximal frequent itemset
     *
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemList
     */
    private void PMFIM(ItemsetTuple<E> node, Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> sortedItemList){
        List<E> itemsetOfNode = node.getItemset();

        List<List<E>> itemsetJOrderLargerThanIList = this.getItemsetJOrderLargerThanI(itemsetOfNode, sortedItemList);

        System.out.println();
        System.out.println("-----*-----");
        System.out.println("Current item: " + node.getItemset());
        System.out.println("Item J Order Larger Than I List: " + itemsetJOrderLargerThanIList );

        for (int i = 0; i < itemsetJOrderLargerThanIList.size(); i++) {

            List<E> itemsetJ = itemsetJOrderLargerThanIList.get(i);

            //init temp node and its child
            ItemsetTuple<E> tempNode = new ItemsetTuple<>(itemsetJ);

            List<ItemsetTuple<E>> tempChild = node.getChild();
            tempChild.add(tempNode);
            node.setChild(tempChild);

            //calc support, expected support, lower and upper bound of itemset
            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, weightedTable, itemsetJ);
            int support = frequentItemset.calculateSupport();
            double expectSupport = frequentItemset.calculateExpectedSupport();
            double lowerBound = frequentItemset.calculateLowerBound(expectSupport, this.minimumProbabilisticConfidence) ;
            double upperBound = frequentItemset.calculateUpperBound(expectSupport, this.minimumProbabilisticConfidence);

            System.out.println("\nItemset: " + tempNode.getItemset());
            System.out.println("Lower bound: " + lowerBound + " --*-- Upper bound: " + upperBound  );

            //skip itemset which have support less than min support
            if (Math.min(support, upperBound) < minimumSupport) {
                tempChild.remove(node.getChild().size() - 1);
                node.setChild(tempChild);
                System.out.println("Pruned");

                continue;
            }

            //if lower bound is larger than min support -> call PMFIM()
            if (lowerBound  >= minimumSupport) {
                System.out.println("Frequent Node: " + tempNode);
                //recursive call PMFIM()
                PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

            } else {
                //calc probabilistic support of itemset
                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(uncertainDatabase, weightedTable, itemsetJ);
                double probabilisticSupport = probabilisticFrequentItemset.calculateWeightedProbabilisticSupport(minimumProbabilisticConfidence);

                System.out.println("Probabilistic of itemset: " + probabilisticSupport);

                if (probabilisticSupport >= minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);

                    //recursive call PMFIM()
                    PMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

                } else {
                    //eliminate node which is invalid condition
                    tempChild.remove(node.getChild().size() - 1);
                    node.setChild(tempChild);
                    System.out.println("Pruned");
                }
            }
        }

        if (node.getChild().isEmpty()
                && !isCollectionContainsItemset(probabilisticMaximalFrequentItemsetCollection, itemsetOfNode)) {
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
     * Get Itemset J order larger than current item I in sorted item list
     * @param itemsetI
     * @param sortedItemList
     * @return Item order larger than current item in sorted item list
     */
    private List<List<E>> getItemsetJOrderLargerThanI(List<E> itemsetI, List<E> sortedItemList){
        List<List<E>> itemsetJOrderLargerThanI = new ArrayList<>();

        int itemISize = itemsetI.size();
        int indexOfLastItemI = -1;

        //get index of last item of itemset in Sorted Item List
        if (itemISize > 0){
            indexOfLastItemI = sortedItemList.lastIndexOf(itemsetI.get(itemISize - 1));
        }

        //return [] if last item in itemset is last item in Sorted Item List
        if(indexOfLastItemI == sortedItemList.size() - 1){
            return new ArrayList<>();
        }

        for(int i = indexOfLastItemI + 1; i < sortedItemList.size(); i++){
            List<E> tempItemset = new ArrayList<>(itemsetI);
            tempItemset.add(sortedItemList.get(i));
            itemsetJOrderLargerThanI.add(tempItemset);
        }

//        List<E> temptSortedList = new ArrayList<>(sortedItemList);
//        temptSortedList.removeAll(itemsetI);
//
//        for(int i = 0; i < temptSortedList.size(); i++){
//            List<E> tempItemset = new ArrayList<>(itemsetI);
//            tempItemset.add(temptSortedList.get(i));
//            itemsetJOrderLargerThanI.add(tempItemset);
//        }

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
