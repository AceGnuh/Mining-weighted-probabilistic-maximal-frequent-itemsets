package pmfi.algorithms;

import java.util.ArrayList;
import java.util.List;

public class ItemsetTuple<E> {
    private List<E> itemset;
    private int support;
    private double expectSupport;
    private int probabilisticSupport;
    private double lowerBound;
    private double upperBound;

    /**
     * list child node of node
     */
    private List<ItemsetTuple<E>> child;

    public ItemsetTuple() {
        this(new ArrayList<>(), 0, 0.0, 0, 0.0, 0.0, new ArrayList<>());
    }

    public ItemsetTuple(List<E> itemset) {
        this(itemset, 0, 0.0, 0, 0.0, 0.0, new ArrayList<>());
    }
    public ItemsetTuple(E item) {
        this(new ArrayList<>(List.of(item)), 0, 0.0, 0, 0.0, 0.0, new ArrayList<>());
    }

    public ItemsetTuple(List<E> itemset, int support, double expectSupport, int probabilisticSupport, double lowerBound, double upperBound, List<ItemsetTuple<E>> child) {
        this.itemset = itemset;
        this.support = support;
        this.expectSupport = expectSupport;
        this.probabilisticSupport = probabilisticSupport;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.child = child;
    }

    public List<E> getItemset() {
        return itemset;
    }

    public void setItemset(List<E> itemset) {
        this.itemset = itemset;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public double getExpectSupport() {
        return expectSupport;
    }

    public void setExpectSupport(double expectSupport) {
        this.expectSupport = expectSupport;
    }

    public int getProbabilisticSupport() {
        return probabilisticSupport;
    }

    public void setProbabilisticSupport(int probabilisticSupport) {
        this.probabilisticSupport = probabilisticSupport;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public List<ItemsetTuple<E>> getChild() {
        return child;
    }

    public void setChild(List<ItemsetTuple<E>> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ItemsetTuple{" +
                "item=" + itemset +
                ", support=" + support +
                ", expectSupport=" + expectSupport +
                ", probabilisticSupport=" + probabilisticSupport +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }
}
