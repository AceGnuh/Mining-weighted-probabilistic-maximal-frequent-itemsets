package pmfi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uncertain Transaction contains:
 * - id of transaction
 * - transaction is map with key: itemset and value: probabilistic of itemset
 * @param <E> data type of itemset
 */
public class UncertainTransaction<E>{
    private Integer ID;

    /**
     * key: data of itemset; value: probability of itemset;
     */
    private final Map<E, Double> transaction;

    public UncertainTransaction(Integer ID, Map<E, Double> transaction) {
        this.ID = ID;
        this.transaction = transaction;
    }

    public UncertainTransaction() {
        this(0, new HashMap<>());
    }

    public Map<E, Double> getTransaction() {
        return transaction;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Get probability of itemset in transaction
     * @param itemset
     * @return probabilistic of item in transaction
     */
    public double getProbabilistic(List<E> itemset){
        double pItem = 1.0;

        for(E currItem: itemset){
            pItem *= transaction.getOrDefault(currItem, 0.0);
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
