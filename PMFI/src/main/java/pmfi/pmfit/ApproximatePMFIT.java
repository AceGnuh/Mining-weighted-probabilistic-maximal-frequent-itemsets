package pmfi.pmfit;

import pmfi.entities.supports.FrequentItemset;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.approximate.ApproximateProbabilisticFrequentItemset;
import pmfi.entities.supports.ProbabilisticFrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.helper.ListHelper;

import java.util.*;

public class ApproximatePMFIT<E> implements ProbabilisticMaximalFrequentItemsetTree {
    /**
     * Root of probabilistic maximal frequent itemset tree
     */
    private ItemsetTuple<E> root;
    private UncertainDatabase<E> uncertainDatabase;
    private double minimumSupport;
    private double minimumProbabilisticConfidence;
    private Set<E> distinctItemList;

    public ApproximatePMFIT(UncertainDatabase<E> uncertainDatabase, double minimumSupport, double minimumProbabilisticConfidence) {
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

        //step 1: get distinct item and sorted them by incremental order according to their expected support
        List<E> sortedItemList = this.getSortedItemList();

        //step 2: init root node with null value
        this.root = new ItemsetTuple<>();

        //step 3 - 5:
        for(E item: sortedItemList){
            ItemsetTuple<E> chilNode = new ItemsetTuple<>(item);

            this.root.getChild().add(chilNode);

            int returnValue = this.ApproximatePMFIM(chilNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

            // find last item in sorted list -> break loop
            if(returnValue == 1) {
                break;
            }
        }

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * Get distinct item in database and sorted increase by its expect support
     * @return  sorted item list order by expect support
     */
    public List<E> getSortedItemList() {
        List<ItemsetTuple<E>> sortedItemsetTupleList = new ArrayList<>();

        for(E distinctItem : this.distinctItemList){
            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, List.of(distinctItem));

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

            //calc approximate probabilistic support of item
            ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(uncertainDatabase, List.of(distinctItem));
            int approximateProbabilisticSupport = approximateProbabilisticFrequentItemset
                    .calculateProbabilisticSupport(minimumProbabilisticConfidence);

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
        sortedItemsetTupleList.sort((o1, o2) -> Double.compare(o1.getProbabilisticSupport(), o2.getProbabilisticSupport()));

        //get item by increase of expect support from sortedItemsetTupleList
        List<E> sortedItemList = new ArrayList<>();
        sortedItemsetTupleList.forEach((currItem) -> sortedItemList.add(currItem.getItemset().get(0)) );

        return sortedItemList;
    }

    /**
     * Apply algorithm to find itemset is probabilistic maximal frequent itemset
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemList
     * @return
     * -1 if it is infrequent item;
     * 0 if it is probabilistic frequent item;
     * 1 if it is probabilistic maximal frequent item;
     */
    private int ApproximatePMFIM(ItemsetTuple<E> node, Set<List<E>> probabilisticMaximalFrequentItemsetCollection, List<E> sortedItemList){
        int result = -1;

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

            if (ListHelper.isSubListAtEnd(sortedItemList, itemsetJ))
            {
                ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, distinctItemList, itemsetJ);

                if (probabilisticFrequentItemset.isProbabilisticMaximalFrequentItemset(this.minimumSupport, this.minimumProbabilisticConfidence, sortedItemList)) {
                    System.out.println("Found maximal!!!");

                    if (!probabilisticMaximalFrequentItemsetCollection.contains(itemsetJ)) {
                        //add itemset into ApproximatePMFI collection
                        probabilisticMaximalFrequentItemsetCollection.add(itemsetJ);
                        System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

                        return 1;
                    }
                }
            }

            if (probabilisticMaximalFrequentItemsetCollection.contains(itemsetJ)) {
                System.out.println("Frequent Node: " + node);

                //calc recursion ApproximatePMFIM()
                result = ApproximatePMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

                System.out.println(node.getItemset() + "return value: " + result);

                if (result == 0 || result == 1){
                    break;
                }

                continue;
            }

            FrequentItemset<E> frequentItemset = new FrequentItemset<>(this.uncertainDatabase, tempNode.getItemset());

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

                //calc recursion ApproximatePMFIM()
                result = ApproximatePMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

                System.out.println(node.getItemset() + " return value: " + result);

                if (result == 0 || result == 1){
                    break;
                }

            } else {
                //calc approximate probabilistic support of item
                ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(uncertainDatabase, tempNode.getItemset());
                int approximateProbabilisticSupport = approximateProbabilisticFrequentItemset
                        .calculateProbabilisticSupport(minimumProbabilisticConfidence);

                System.out.println("Approximate probabilistic support: " + approximateProbabilisticSupport);

                if (approximateProbabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode);

                    //calc recursion ApproximatePMFIM()
                    result = ApproximatePMFIM(tempNode, probabilisticMaximalFrequentItemsetCollection, sortedItemList);

                    System.out.println(node.getItemset() + " return value: " + result);

                    if (result == 0 || result == 1){
                        break;
                    }

                } else {
                    //eliminate node which is invalid condition
                    tempChild.remove(node.getChild().size() - 1);
                    node.setChild(tempChild);
                    System.out.println(node.getItemset() + " return value: " + result);
                    System.out.println("Pruned");

                }
            }
        }

        if (node.getChild().isEmpty() && !probabilisticMaximalFrequentItemsetCollection.contains(node.getItemset())) {
            //add itemset into ApproximatePMFI collection
            probabilisticMaximalFrequentItemsetCollection.add(node.getItemset());
            System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

            return 0;
        }

        return result;
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
