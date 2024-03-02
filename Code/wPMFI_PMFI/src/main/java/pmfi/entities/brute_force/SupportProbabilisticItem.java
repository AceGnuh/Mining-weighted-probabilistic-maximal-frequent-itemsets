package pmfi.entities.brute_force;

public class SupportProbabilisticItem {
    private Integer frequent;
    private double probabilistic;

    public SupportProbabilisticItem(Integer frequent, double probabilistic) {
        this.frequent = frequent;
        this.probabilistic = probabilistic;
    }

    public Integer getFrequent() {
        return frequent;
    }

    public void setFrequent(Integer frequent) {
        this.frequent = frequent;
    }

    public double getProbabilistic() {
        return probabilistic;
    }

    public void setProbabilistic(double probabilistic) {
        this.probabilistic = probabilistic;
    }

    @Override
    public String toString() {
        return "SupportProbabilisticItem{" +
                "frequent=" + frequent +
                ", probabilistic=" + probabilistic +
                '}';
    }
}
