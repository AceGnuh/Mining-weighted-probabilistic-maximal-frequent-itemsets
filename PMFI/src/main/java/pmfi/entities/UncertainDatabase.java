package pmfi.entities;

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
    public Set<E> getDistinctItem(){
        Set<E> distinctItemList = new HashSet<>();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase){
            //add all key set into distinct item list
            distinctItemList.addAll(uncertainTransaction.getTransaction().keySet());
        }

        return distinctItemList;
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
