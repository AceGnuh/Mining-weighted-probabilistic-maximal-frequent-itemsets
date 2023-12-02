package pmfi.entities;

import java.util.List;

public class PossibleWorldItem <E>{
    protected List<List<E>> listPossibleWorldItem;
    protected double probability;

    public PossibleWorldItem(List<List<E>> listPossibleWorldItem, double probability) {
        this.listPossibleWorldItem = listPossibleWorldItem;
        this.probability = probability;
    }

    public void setListPossibleWorldItem(List<List<E>> listPossibleWorldItem) {
        this.listPossibleWorldItem = listPossibleWorldItem;
    }

    public List<List<E>> getListPossibleWorldItem() {
        return listPossibleWorldItem;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "PossibleWorldItem{" +
                "item=" + listPossibleWorldItem +
                ", probability=" + probability +
                '}';
    }
}
