package pmfi.entities;

import java.util.ArrayList;
import java.util.List;

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

    public UncertainTransaction() {
        this(0, new ArrayList<>());
    }

    public List<UncertainItemset<E>> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<UncertainItemset<E>> transaction) {
        this.transaction = transaction;
    }

    private double getProbabilistic(E item){
        for(UncertainItemset<E> currItem: this.transaction){
            if(currItem.getItem().equals(item)){
                return currItem.getProbability();
            }
        }

        return 0.0;
    }

    public double getProbabilistic(List<E> item){
        double pItem = 1.0;

        for(E currItem: item){
            pItem *= this.getProbabilistic(currItem);
        }

        return pItem;
    }

    @Override
    public String toString() {
        return "UncertainTransaction{" +
                "ID=" + ID +
                ", transaction=" + transaction +
                '}';
    }
}
