package pmfi.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UncertainDatabase contains: List of uncertain transaction
 * @param <E>
 */

public class UncertainDatabase<E> {
    private final List<UncertainTransaction<E>> uncertainTransactions;

    public UncertainDatabase(List<UncertainTransaction<E>> uncertainDatabase) {
        this.uncertainTransactions = uncertainDatabase;
    }

    public UncertainDatabase() {
        this(new ArrayList<>());
    }

    public List<UncertainTransaction<E>> getUncertainTransactions() {
        return uncertainTransactions;
    }

    /**
     *
     * @return distinct item in database
     */
    public Set<List<E>> getDistinctItem(){
        Set<List<E>> distinctItemList = new HashSet<>();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainTransactions){
            for(E uncertainItem : uncertainTransaction.getTransaction().keySet()){
                List<E> item = new ArrayList<>(List.of(uncertainItem));
                //item.add(uncertainItem);

                distinctItemList.add(item);
            }
            //Set<E> distinctItemInTransaction = uncertainTransaction.getTransaction().keySet();
            //distinctItemList.addAll(uncertainTransaction.getTransaction().keySet());
        }

        return distinctItemList;
    }

    @Override
    public String toString() {
        StringBuilder dataUncertainDatabase = new StringBuilder();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainTransactions){
            dataUncertainDatabase.append("\t").append(uncertainTransaction).append("\n");
        }

        return "UncertainDatabase: \n" + dataUncertainDatabase + '\n';
    }
}
