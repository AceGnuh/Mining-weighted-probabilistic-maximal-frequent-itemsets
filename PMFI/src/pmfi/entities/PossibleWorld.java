package pmfi.entities;

import pmfi.functions.IPossibleWorldActionable;
import pmfi.functions.IPossibleWorldBuilder;
import pmfi.helper.SetHelper;

import java.util.*;

public class PossibleWorld<E> implements IPossibleWorldActionable<E>, IPossibleWorldBuilder<E> {
    private UncertainDatabase uncertainDatabase;
    private List<PossibleWorldItem<E>> possibleWorld;

    public PossibleWorld(UncertainDatabase uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
        possibleWorld = new ArrayList<>();
    }

    private PossibleWorld(UncertainDatabase uncertainDatabase, List<PossibleWorldItem<E>> possibleWorld) {
        this.uncertainDatabase = uncertainDatabase;
        this.possibleWorld = possibleWorld;
    }

    public UncertainDatabase getUncertainDatabase() {
        return uncertainDatabase;
    }

    public void setUncertainDatabase(UncertainDatabase uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
    }

    public List<PossibleWorldItem<E>> getPossibleWorld() {
        return possibleWorld;
    }

    public void setPossibleWorld(List<PossibleWorldItem<E>> possibleWorld) {
        this.possibleWorld = possibleWorld;
    }

    private List<List<E>> getListSetTransactionItem(){
        List<List<E>> listSetTransactionItem = new ArrayList<>();

        for (UncertainTransaction uncertainTransaction : this.uncertainDatabase.getUncertainTransactions()){
            List<E> setItemInTransaction = new ArrayList<>();
            for(UncertainItemset<E> itemset : uncertainTransaction.getTransaction()){
                setItemInTransaction.add(itemset.getItem());
            }
            listSetTransactionItem.add(setItemInTransaction);
        }

        return listSetTransactionItem;
    }

    private List<List<List<E>>> generateListAllSubSet(List<List<E>> listSetTransactionItem){
        List<List<List<E>>> listAllSubset = new ArrayList();

        for(int i = 0; i < listSetTransactionItem.size(); i++){
            listAllSubset.add(SetHelper.generateAllSubsetFromSet(listSetTransactionItem.get(i)));
        }

        return listAllSubset;
    }

    private void calculatePossibleWorldProb(List<List<List<E>>> listCombineItem){
        for(List<List<E>> combineItemList : listCombineItem){
            double probPossibleWorld = 1.0;

            int indexCombineItem = 0;

            for(List<E> combineItem : combineItemList){
                List<UncertainItemset> indexCombineItemInUncertainTransactionList = new ArrayList();

//                System.out.println("Index: " + indexCombineItem);

                List<UncertainItemset> uncertainTransaction
                        = this.uncertainDatabase.getUncertainTransactions().get(indexCombineItem).getTransaction();

                List<E> uncertainTransactionDataList = new ArrayList<>();

                for(UncertainItemset<E> uncertainItemset : uncertainTransaction ){
                    uncertainTransactionDataList.add(uncertainItemset.getItem());
                }

                List<UncertainItemset> uncertainItemsetIndexList = new ArrayList<>();

                for(UncertainItemset uncertainItemset : uncertainTransaction ){
                    UncertainItemset itemset = new UncertainItemset(uncertainItemset.getItem(), 1 - uncertainItemset.getProbability());
                    uncertainItemsetIndexList.add(itemset);
                }

//                int currIndex = 0;
//                for(UncertainItemset uncertainItemset : uncertainTransaction ){
//                    for(E combineItemData : combineItem){
//                        if(!uncertainItemset.getItem().equals(combineItemData)){
//                            UncertainItemset itemset = new UncertainItemset(uncertainItemset.getItem(), uncertainItemset.getProbability());
//                            uncertainItemsetIndexList.set(currIndex, itemset);
//                        }
//                    }
//                    currIndex++;
//                }

                for(E combineItemData : combineItem){
                    int index = uncertainTransactionDataList.indexOf(combineItemData);

                    if(index != -1){
                        UncertainItemset itemset = uncertainItemsetIndexList.get(index);
                        itemset.setProbability( 1 - itemset.getProbability());
                        uncertainItemsetIndexList.set(index, itemset);
                    }
                }



//                System.out.println(uncertainItemsetIndexList);

                for(UncertainItemset<E> uncertainItemset : uncertainItemsetIndexList){
                    probPossibleWorld *= uncertainItemset.getProbability();
                }



//                boolean hadOccurrence = false;
//
//                for(UncertainItemset uncertainItemset : uncertainTransaction){
//                    if(combineItem.get(0) == null){
//                        UncertainItemset itemset = new UncertainItemset(uncertainItemset.getItem(), 1 - uncertainItemset.getProbability());
//                        indexCombineItemInUncertainTransactionList.add(itemset);
//                    }
//                    else{
//                        for(E combineItemData : combineItem){
//                            if(combineItemData.equals(uncertainItemset.getItem()) && hadOccurrence == false){
//                                UncertainItemset itemset = new UncertainItemset(uncertainItemset.getItem(), uncertainItemset.getProbability());
//                                indexCombineItemInUncertainTransactionList.add(itemset);
//                                hadOccurrence = true;
//                            }
//                            else {
//                                UncertainItemset itemset = new UncertainItemset(uncertainItemset.getItem(), 1 - uncertainItemset.getProbability());
//                                indexCombineItemInUncertainTransactionList.add(itemset);
//                            }
//                        }
//                    }
//
//                }
//
//                for(UncertainItemset uncertainItemset : indexCombineItemInUncertainTransactionList){
//                    probPossibleWorld *= uncertainItemset.getProbability();
//                }

                indexCombineItem++;
//
//                System.out.println(indexCombineItemInUncertainTransactionList);
            }



            /*
            for(int i = 0 ; i < combineItem.size(); i++){
                List<UncertainItemset> uncertainTransaction
                        = this.uncertainDatabase.getUncertainTransactions().get(i).getTransaction();

                List<E> uncertainItemsetData = new ArrayList<>();

                for(UncertainItemset uncertainItemset : uncertainTransaction){
                    uncertainItemsetData.add((E) uncertainItemset.getItem());
                }

                if(combineItem.get(i).size() == 1 && combineItem.get(i).get(0) == null){
                    for(UncertainItemset<E> uncertainItemset : uncertainTransaction){
                        probPossibleWorld *= 1.0 - uncertainItemset.getProbability();
                    }
                }
                else {
                    if(combineItem.get(i).size() == uncertainItemsetData.size()){
                        for(UncertainItemset<E> uncertainItemset : uncertainTransaction){
                            probPossibleWorld *= uncertainItemset.getProbability();
                        }
                    }
                    else {
                        for(UncertainItemset<E> uncertainItemset : uncertainTransaction){
                            for(int j = 0 ; j < combineItem.get(i).size(); j++) {
                                if(combineItem.get(i).get(j) != null){
                                    if(combineItem.get(i).get(j).equals(uncertainItemset.getItem())){
                                        probPossibleWorld *= uncertainItemset.getProbability();
                                        break;
                                    }
                                    else {
                                        probPossibleWorld *= 1.0 - uncertainItemset.getProbability();
                                        break;
                                    }
                                }

                            }
                        }
                    }

                }
            }


             */

            PossibleWorldItem<E> _possibleWorldItem = new PossibleWorldItem<>(combineItemList, probPossibleWorld);

            this.possibleWorld.add(_possibleWorldItem);

        }
    }

    private List<List<List<E>>> generateCombinations(List<List<List<E>>> lists) {
        List<List<List<E>>> result = new ArrayList<>();
        SetHelper.generateCombinationsHelper(lists, 0, new ArrayList<>(), result);

        for(List<List<E>> listListItem : result){
            for(List<E> listItem : listListItem){
                if(listItem.size() == 0){
                    listItem.add(null);
                }
            }
        }

        return result;
    }
    
    private void generatePossibleWorld(){
        List<List<E>> listSetTransactionItem = getListSetTransactionItem();

        List<List<List<E>>> listAllSubsetInDatabase = generateListAllSubSet(listSetTransactionItem);

        List<List<List<E>>> listCombineItem = generateCombinations(listAllSubsetInDatabase);

        calculatePossibleWorldProb(listCombineItem);

    }

    public Set<List<E>> getAllDistinctSet(){
        List<List<E>> listSetTransactionItem = getListSetTransactionItem();

        List<List<List<E>>> listAllSubsetInDatabase = generateListAllSubSet(listSetTransactionItem);

        Set<List<E>> allDistinctSet = new HashSet<>();

        for(List<List<E>> subsetInDatabaseList: listAllSubsetInDatabase){
            for(List<E> subsetInDatabase : subsetInDatabaseList){
                allDistinctSet.add(subsetInDatabase);
            }
        }

        return allDistinctSet;
    }

    @Override
    public int size(){
        int possibleWorldSize = 0;

        for(int i = 0; i < this.uncertainDatabase.getUncertainTransactions().size(); i++){
            possibleWorldSize += this.uncertainDatabase.getUncertainTransactions().get(i).getTransaction().size();
        }

        return (int) Math.pow(2.0, possibleWorldSize);
    }

    @Override
    public void display(){
        int index = 1;

        System.out.println("Possible World");
        for(PossibleWorldItem<E> possibleWorldItem : this.possibleWorld){
            System.out.println(index + ": \t" + "listPossibleWorldItem=" + possibleWorldItem.getListPossibleWorldItem() +
                    ", probability=" + possibleWorldItem.probability +
                    '}');
            ++index;
        }
    }

    @Override
    public PossibleWorld<E> build() {
        generatePossibleWorld();
        return new PossibleWorld<>(this.uncertainDatabase, this.possibleWorld);
    }

    @Override
    public String toString() {
        return "PossibleWorld{" +
                "possibleWorld=" + possibleWorld +
                '}';
    }

}
