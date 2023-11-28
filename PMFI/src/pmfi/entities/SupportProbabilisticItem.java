package pmfi.entities;

public class SupportProbabilisticItem {
    private Integer frequent;
    private Double probabilistic;

    public SupportProbabilisticItem(Integer frequent, Double probabilistic) {
        this.frequent = frequent;
        this.probabilistic = probabilistic;
    }

    public Integer getFrequent() {
        return frequent;
    }

    public void setFrequent(Integer frequent) {
        this.frequent = frequent;
    }

    public Double getProbabilistic() {
        return probabilistic;
    }

    public void setProbabilistic(Double probabilistic) {
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
