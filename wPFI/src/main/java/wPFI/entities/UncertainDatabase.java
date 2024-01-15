package wPFI.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UncertainDatabase contains: List of uncertain transaction
 * @param <E> type of items
 */

public class UncertainDatabase<E> {
    private final List<UncertainTransaction<E>> uncertainDatabase;

    public UncertainDatabase(List<UncertainTransaction<E>> uncertainDatabase) {
        this.uncertainDatabase = uncertainDatabase;
    }

    public UncertainDatabase() {
        this(new ArrayList<>());
    }

    public List<UncertainTransaction<E>> getUncertainDatabase() {
        return uncertainDatabase;
    }

    /**
     * get distinct item in UD
     * @return distinct item in database
     */
    public Set<Set<E>> getDistinctItemset(){
        Set<Set<E>> distinctItemsetList = new HashSet<>();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase){
            //add all key set into distinct item list
            for(E currItem : uncertainTransaction.getTransaction().keySet()){
                distinctItemsetList.add(new HashSet<>(List.of(currItem)));
            }
        }

        return distinctItemsetList;
    }

    /**
     * Calculate mean of itemset
     * @param itemset
     * @return mean of itemset
     */
    public double calculateMeanItemset(Set<E> itemset){
        double mean = 0;
        for(UncertainTransaction<E> uncertainTransaction : getUncertainDatabase()){
            mean += uncertainTransaction.getProbabilistic(itemset);
        }
        return mean;
    }

    @Override
    public String toString() {
        StringBuilder dataUncertainDatabase = new StringBuilder();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase){
            dataUncertainDatabase.append("\t").append(uncertainTransaction).append("\n");
        }

        return "UncertainDatabase: \n" + dataUncertainDatabase + '\n';
    }
}
