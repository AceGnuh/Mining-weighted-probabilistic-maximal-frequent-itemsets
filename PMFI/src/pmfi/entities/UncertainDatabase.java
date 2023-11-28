package pmfi.entities;

import java.util.List;

public class UncertainDatabase {
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

    @Override
    public String toString() {
        String dataUncertainDatabase = "";

        for(UncertainTransaction uncertainTransaction : this.uncertainTransactions){
            dataUncertainDatabase += "\t"+ uncertainTransaction + "\n";
        }

        return "UncertainDatabase: \n" + dataUncertainDatabase + '\n';
    }
}
