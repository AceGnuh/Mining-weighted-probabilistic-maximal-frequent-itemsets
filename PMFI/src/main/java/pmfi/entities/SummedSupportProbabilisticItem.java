package pmfi.entities;

public class SummedSupportProbabilisticItem extends SupportProbabilisticItem{
    public SummedSupportProbabilisticItem(Integer frequent, Double probabilistic) {
        super(frequent, probabilistic);
    }

    @Override
    public String toString() {
        return "SummedSupportProbabilisticItem{" +
                "frequent=" + super.getFrequent() +
                ", probability=" + super.getProbabilistic() +
                '}';
    }
}
