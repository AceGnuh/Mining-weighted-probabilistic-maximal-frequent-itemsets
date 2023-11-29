package pmfi.entities;

import pmfi.functions.IPossibleWorldActionable;
import pmfi.functions.IPossibleWorldBuilder;
import pmfi.helper.SetHelper;

import java.util.*;

public class PossibleWorld<E> implements IPossibleWorldActionable<E>, IPossibleWorldBuilder<E> {
    private UncertainDatabase<E> uncertainDatabase;
    private List<PossibleWorldItem<E>> possibleWorld;

    public PossibleWorld(UncertainDatabase<E> uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
        possibleWorld = new ArrayList<>();
    }

    private PossibleWorld(UncertainDatabase<E> uncertainDatabase, List<PossibleWorldItem<E>> possibleWorld) {
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

            /*
            for(List<E> combineItem: combineItemList){
                List<UncertainItemset> uncertainTransaction
                        = this.uncertainDatabase
                        .getUncertainTransactions()
                        .get(indexCombineItem)
                        .getTransaction();

                Set<UncertainItemset> uncertainItemSet = new HashSet<>(uncertainTransaction);

                Set<UncertainItemset> combineItemSet = new HashSet<>();

                for(E combineItemData : combineItem){
                    for(UncertainItemset uncertainItemset : uncertainTransaction){
                        if(uncertainItemset.getItem().equals(combineItemData)){
                            combineItemSet.add(uncertainItemset);
                        }
                    }
                }

                Set<UncertainItemset> combineItemBelongTransactionSet = new HashSet<>(uncertainItemSet);
                combineItemBelongTransactionSet.retainAll(combineItemSet);

                Set<UncertainItemset> combineItemNotBelongTransaction = new HashSet<>(uncertainItemSet);
                combineItemNotBelongTransaction.retainAll(combineItemBelongTransactionSet);

                for(UncertainItemset uncertainItemset : combineItemBelongTransactionSet){
                    probPossibleWorld *= uncertainItemset.getProbability();
                }

                for(UncertainItemset uncertainItemset : combineItemBelongTransactionSet){
                    probPossibleWorld *= 1.0 - uncertainItemset.getProbability();
                }

                indexCombineItem++;
            }
            */


            for(List<E> combineItem : combineItemList){

                List<UncertainItemset> uncertainTransaction
                        = this.uncertainDatabase.getUncertainTransactions().get(indexCombineItem).getTransaction();

                List<E> uncertainTransactionDataList = new ArrayList<>();

                for(UncertainItemset<E> uncertainItemset : uncertainTransaction ){
                    uncertainTransactionDataList.add(uncertainItemset.getItem());
                }

                List<UncertainItemset> uncertainItemsetIndexList = new ArrayList<>();

                for(UncertainItemset uncertainItemset : uncertainTransaction ){
                    UncertainItemset itemset
                            = new UncertainItemset(uncertainItemset.getItem(), 1 - uncertainItemset.getProbability());

                    uncertainItemsetIndexList.add(itemset);
                }

                for(E combineItemData : combineItem){
                    int index = uncertainTransactionDataList.indexOf(combineItemData);

                    if(index != -1){
                        UncertainItemset itemset = uncertainItemsetIndexList.get(index);
                        itemset.setProbability( 1 - itemset.getProbability());
                        uncertainItemsetIndexList.set(index, itemset);
                    }
                }

                for(UncertainItemset<E> uncertainItemset : uncertainItemsetIndexList){
                    probPossibleWorld *= uncertainItemset.getProbability();
                }

                indexCombineItem++;
            }


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
