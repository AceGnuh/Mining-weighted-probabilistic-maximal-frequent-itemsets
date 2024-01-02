package pmfi.pmfit;

import pmfi.entities.supports.FrequentItemset;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.approximate.ApproximateProbabilisticFrequentItemset;
import pmfi.functions.ProbabilisticMaximalFrequentItemsetTree;
import pmfi.helper.ListHelper;

import java.util.*;

public class ApproximatePMFIT<E> implements ProbabilisticMaximalFrequentItemsetTree {
    private Node<E> root;
    private final UncertainDatabase<E> uncertainDatabase;
    private final double minimumSupport;
    private final double minimumProbabilisticConfidence;
    private final Set<List<E>> distinctItemList;

    public ApproximatePMFIT(UncertainDatabase<E> uncertainDatabase, double minimumSupport, double minimumProbabilisticConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.distinctItemList = uncertainDatabase.getDistinctItem();

        this.minimumSupport = minimumSupport;
        this.minimumProbabilisticConfidence = minimumProbabilisticConfidence;
    }

    /**
     * Build Algorithm to find all approximate probabilistic maximal frequent itemsets
     * @return probabilistic maximal frequent item collection
     */
    public Set<List<E>> findAllPMFI(){
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
        List<Node<E>> childNodeList = new ArrayList<>();
        for(ItemsetTuple<E> chilNode: sortedItemList){
            childNodeList.add(new Node<>(chilNode));
        }

        this.root.setChild(childNodeList);

        //step 3 - 5:
        Set<List<E>> probabilisticMaximalFrequentItemsetCollection = new HashSet<>();

        for(int i = 0; i < this.root.getChild().size(); i++){
            int returnValue = this.PMFIM(this.root.getChild().get(i), probabilisticMaximalFrequentItemsetCollection, sortedItemValueList);

            System.out.println();
            System.out.println(this.root.getChild().get(i) + " return value: " + returnValue);
            System.out.println();

            if (returnValue == 1){
                if (this.root.getChild().size() > i + 1) {
                    this.root.getChild().subList(i + 1, this.root.getChild().size()).clear();
                }
                break;
            }
        }

        return probabilisticMaximalFrequentItemsetCollection;
    }

    /**
     * Get distinct item in database and sorted increase by its probabilistic support
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

            if(expectSupport <= 0){
                continue;
            }

            //eliminate item with upper bound < min support
            if(upperBound < this.minimumSupport){
                continue;
            }

            ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(this.uncertainDatabase, distinctItem);
            int probabilisticSupport = approximateProbabilisticFrequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);

            ItemsetTuple<E> itemsetTuple
                    = new ItemsetTuple<>(
                            distinctItem,
                            support,
                            expectSupport,
                            probabilisticSupport,
                            0,
                            upperBound
            );

            sortedItemList.add(itemsetTuple);
        }

        //sorted by the increase of expect support
        sortedItemList.sort(new Comparator<ItemsetTuple<E>>() {
            @Override
            public int compare(ItemsetTuple<E> o1, ItemsetTuple<E> o2) {
                return Double.compare(o1.getProbabilisticSupport(), o2.getProbabilisticSupport());
            }
        });

        return sortedItemList;
    }

    /**
     * Apply algorithm to find itemset is approximate probabilistic maximal frequent
     * @param node
     * @param probabilisticMaximalFrequentItemsetCollection
     * @param sortedItemValueList
     * @return
     * -1 if it is infrequent item;
     * 0 if it is approximate probabilistic frequent item;
     * 1 if it is approximate probabilistic maximal frequent item;
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

            if (ListHelper.isSubListAtEnd(sortedItemValueList, itemJList))
            {

                //ProbabilisticFrequentItemset<E> probabilisticFrequentItemset = new ProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);
                ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);
                if (approximateProbabilisticFrequentItemset.isProbabilisticMaximalFrequentItemset(this.minimumSupport, this.minimumProbabilisticConfidence, sortedItemValueList)) {
                    //---
                    Node<E> tempNode = new Node<>(new ItemsetTuple<>(new ArrayList<>(itemJList)));

                    List<Node<E>> tempChild = node.getChild();
                    tempChild.add(tempNode);

                    node.setChild(tempChild);
                    //---

                    System.out.println("sos maximal");

                    if (!probabilisticMaximalFrequentItemsetCollection.contains(itemJList)) {
                        probabilisticMaximalFrequentItemsetCollection.add(itemJList);
                        System.out.println("PMFI collection: " + probabilisticMaximalFrequentItemsetCollection);

                        return 1;
                    }
                }
            }

            //---
            Node<E> tempNode = new Node<>(new ItemsetTuple<>(new ArrayList<>(itemJList)));

            List<Node<E>> tempChild = node.getChild();
            tempChild.add(tempNode);

            node.setChild(tempChild);
            //---

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

                ApproximateProbabilisticFrequentItemset<E> approximateProbabilisticFrequentItemset = new ApproximateProbabilisticFrequentItemset<>(this.uncertainDatabase, itemJList);

                int probabilisticSupport = approximateProbabilisticFrequentItemset.calculateProbabilisticSupport(this.minimumProbabilisticConfidence);

                System.out.println("Probabilistic support: " + probabilisticSupport);

                if (probabilisticSupport >= this.minimumSupport) {
                    System.out.println("Frequent Node: " + tempNode.getItem().getItem());
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
