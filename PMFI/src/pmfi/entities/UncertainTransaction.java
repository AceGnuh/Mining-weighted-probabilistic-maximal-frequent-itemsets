package pmfi.entities;

import java.util.List;
import java.util.Map;

public class UncertainTransaction{
    private Integer ID;
    private List<UncertainItemset> transaction;

    public UncertainTransaction(Integer ID, List<UncertainItemset> transaction) {
        this.ID = ID;
        this.transaction = transaction;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public List<UncertainItemset> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<UncertainItemset> transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "UncertainTransaction{" +
                "ID=" + ID +
                ", transaction=" + transaction +
                '}';
    }
}
