package pmfi.entities;

public class UncertainItemset<E>{
    private E item;
    private double probability;

    public UncertainItemset(E item, double probability) {
        this.item = item;
        this.probability = probability;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "UncertainItem{" +
                "item=" + item +
                ", probability=" + probability +
                '}';
    }
}
