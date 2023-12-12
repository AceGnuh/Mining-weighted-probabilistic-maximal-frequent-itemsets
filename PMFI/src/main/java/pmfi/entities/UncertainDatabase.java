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
    private List<UncertainTransaction<E>> uncertainTransactions;

    public UncertainDatabase(List<UncertainTransaction<E>> uncertainDatabase) {
        this.uncertainTransactions = uncertainDatabase;
    }

    public UncertainDatabase() {
        this(new ArrayList<>());
    }

    public List<UncertainTransaction<E>> getUncertainTransactions() {
        return uncertainTransactions;
    }

    public void setUncertainTransactions(List<UncertainTransaction<E>> uncertainTransactions) {
        this.uncertainTransactions = uncertainTransactions;
    }

    public List<List<E>> getDistinctItem(){
        Set<List<E>> distinctItemList = new HashSet<>();

        for(UncertainTransaction<E> uncertainTransaction : this.uncertainTransactions){
            for(UncertainItemset<E> uncertainItemset : uncertainTransaction.getTransaction()){
                List<E> item = new ArrayList<>();
                item.add(uncertainItemset.getItem());

                distinctItemList.add(item);
            }
        }

        return new ArrayList<>(distinctItemList);
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
