package pmfi.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UncertainDatabase<E> {
    private List<UncertainTransaction> uncertainTransactions;

    public UncertainDatabase(List<UncertainTransaction> uncertainDatabase) {
        this.uncertainTransactions = uncertainDatabase;
    }

    public List<UncertainTransaction> getUncertainTransactions() {
        return uncertainTransactions;
    }

    public void setUncertainTransactions(List<UncertainTransaction> uncertainTransactions) {
        this.uncertainTransactions = uncertainTransactions;
    }

    public List<List<E>> getDistinctItem(){
        Set<List<E>> distinctItemList = new HashSet<>();

        for(UncertainTransaction uncertainTransaction : this.uncertainTransactions){
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
        String dataUncertainDatabase = "";

        for(UncertainTransaction uncertainTransaction : this.uncertainTransactions){
            dataUncertainDatabase += "\t"+ uncertainTransaction + "\n";
        }

        return "UncertainDatabase: \n" + dataUncertainDatabase + '\n';
    }
}
