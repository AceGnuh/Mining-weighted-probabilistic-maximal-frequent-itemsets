package pmfi.entities;

import java.util.List;
import java.util.Map;

/**
 * Uncertain Transaction contains:
 * - id of transaction
 * - list of Uncertain itemset
 * @param <E>
 */
public class UncertainTransaction<E>{
    private Integer ID;
    private List<UncertainItemset<E>> transaction;

    public UncertainTransaction(Integer ID, List<UncertainItemset<E>> transaction) {
        this.ID = ID;
        this.transaction = transaction;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public List<UncertainItemset<E>> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<UncertainItemset<E>> transaction) {
        this.transaction = transaction;
    }

    public double getProbabilistic(E item){
        for(UncertainItemset<E> currItem: this.transaction){
            if(currItem.getItem().equals(item)){
                return currItem.getProbability();
            }
        }

        return 0.0;
    }

    @Override
    public String toString() {
        return "UncertainTransaction{" +
                "ID=" + ID +
                ", transaction=" + transaction +
                '}';
    }
}
