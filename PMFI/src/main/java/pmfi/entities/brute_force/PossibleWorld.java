package pmfi.entities.brute_force;

import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainItemset;
import pmfi.entities.UncertainTransaction;
import pmfi.helper.SetHelper;

import java.util.*;

public class PossibleWorld<E> {
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

    public UncertainDatabase<E> getUncertainDatabase() {
        return uncertainDatabase;
    }

    public void setUncertainDatabase(UncertainDatabase<E> uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
    }

    public List<PossibleWorldItem<E>> getPossibleWorld() {
        return possibleWorld;
    }

    public void setPossibleWorld(List<PossibleWorldItem<E>> possibleWorld) {
        this.possibleWorld = possibleWorld;
    }

    /*
    private List<List<E>> getListSetTransactionItem(){
        List<List<E>> listSetTransactionItem = new ArrayList<>();

        for (UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase.getUncertainTransactions()){
            List<E> setItemInTransaction = new ArrayList<>();
            for(UncertainItemset<E> itemset : uncertainTransaction.getTransaction()){
                setItemInTransaction.add(itemset.getItem());
            }
            listSetTransactionItem.add(setItemInTransaction);
        }

        return listSetTransactionItem;
    }

    private List<List<List<E>>> generateListAllSubSet(List<List<E>> listSetTransactionItem){
        List<List<List<E>>> listAllSubset = new ArrayList<>();

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

                List<UncertainItemset<E>> uncertainTransaction
                        = this.uncertainDatabase
                        .getUncertainTransactions()
                        .get(indexCombineItem)
                        .getTransaction();

                List<E> uncertainTransactionDataList = new ArrayList<>();
                for(UncertainItemset<E> uncertainItemset : uncertainTransaction ){
                    uncertainTransactionDataList.add(uncertainItemset.getItem());
                }

                List<UncertainItemset<E>> uncertainItemsetIndexList = new ArrayList<>();
                for(UncertainItemset<E> uncertainItemset : uncertainTransaction ){
                    UncertainItemset<E> itemset
                            =  new UncertainItemset<>(uncertainItemset.getItem(), 1.0 - uncertainItemset.getProbability());

                    uncertainItemsetIndexList.add(itemset);
                }

                for(E combineItemData : combineItem){
                    int index = uncertainTransactionDataList.indexOf(combineItemData);

                    if(index > -1){
                        UncertainItemset<E> itemset = uncertainItemsetIndexList.get(index);
                        itemset.setProbability( 1.0 - itemset.getProbability());
                        //uncertainItemsetIndexList.set(index, itemset);
                    }
                }

                for(UncertainItemset<E> uncertainItemset : uncertainItemsetIndexList){
                    probPossibleWorld *= uncertainItemset.getProbability();
                }

                indexCombineItem++;
            }


            //probPossibleWorld = (double) Math.round(probPossibleWorld*10000)/10000;

            PossibleWorldItem<E> _possibleWorldItem = new PossibleWorldItem<>(combineItemList, probPossibleWorld);

            this.possibleWorld.add(_possibleWorldItem);

        }
    }

    private List<List<List<E>>> generateCombinations(List<List<List<E>>> lists) {
        List<List<List<E>>> result = new ArrayList<>();
        SetHelper.generateCombinationsHelper(lists, 0, new ArrayList<>(), result);

        for(List<List<E>> listListItem : result){
            for(List<E> listItem : listListItem){
                if(listItem.isEmpty()){
                    listItem.add(null);
                }
            }
        }

        return result;
    }
    


    public Set<List<E>> getAllDistinctSet(){
        List<List<E>> listSetTransactionItem = getListSetTransactionItem();

        List<List<List<E>>> listAllSubsetInDatabase = generateListAllSubSet(listSetTransactionItem);

        Set<List<E>> allDistinctSet = new HashSet<>();

        for(List<List<E>> subsetInDatabaseList: listAllSubsetInDatabase){
            allDistinctSet.addAll(subsetInDatabaseList);
        }

        return allDistinctSet;
    }

    private void generatePossibleWorld(){
        List<List<E>> listSetTransactionItem = getListSetTransactionItem();

        List<List<List<E>>> listAllSubsetInDatabase = generateListAllSubSet(listSetTransactionItem);

        List<List<List<E>>> listCombineItem = generateCombinations(listAllSubsetInDatabase);

        calculatePossibleWorldProb(listCombineItem);
    }

    public int size(){
        int possibleWorldSize = 0;

        for(int i = 0; i < this.uncertainDatabase.getUncertainTransactions().size(); i++){
            possibleWorldSize += this.uncertainDatabase.getUncertainTransactions().get(i).getTransaction().size();
        }

        return (int) Math.pow(2.0, possibleWorldSize);
    }

    public void display(){
        int index = 1;

        System.out.println("Possible World");
        for(PossibleWorldItem<E> possibleWorldItem : this.possibleWorld){
            System.out.println(index + ": \t" + "item=" + possibleWorldItem.getListPossibleWorldItem() +
                    ", probability=" + possibleWorldItem.probability +
                    '}');
            ++index;
        }
    }

    public PossibleWorld<E> build() {
        generatePossibleWorld();

        double sum = 0;

        for(PossibleWorldItem<E> ePossibleWorldItem : this.possibleWorld){
            sum += ePossibleWorldItem.getProbability();
        }

        //System.out.println("Check sum probabilistic possible world: " + sum);

        return new PossibleWorld<>(this.uncertainDatabase, this.possibleWorld);
    }

    @Override
    public String toString() {
        return "PossibleWorld{" +
                "possibleWorld=" + possibleWorld +
                '}';
    }

     */

}
