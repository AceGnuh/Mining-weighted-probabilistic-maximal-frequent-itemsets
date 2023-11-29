package pmfi.pmfit;

public class ItemsetTuple<E> {
    private E item;
    private int support;
    private double expectSupport;
    private int probabilisticSuppport;
    private double lowerBound;
    private double upperBound;

    public ItemsetTuple() {
        this(null, 0, 0.0, 0, 0.0, 0.0);
    }

    public ItemsetTuple(E item, int support, double expectSupport, int probabilisticSuppport, double lowerBound, double upperBound) {
        this.item = item;
        this.support = support;
        this.expectSupport = expectSupport;
        this.probabilisticSuppport = probabilisticSuppport;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
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

    public int getProbabilisticSuppport() {
        return probabilisticSuppport;
    }

    public void setProbabilisticSuppport(int probabilisticSuppport) {
        this.probabilisticSuppport = probabilisticSuppport;
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

    @Override
    public String toString() {
        return "ItemsetTuple{" +
                "item=" + item +
                ", support=" + support +
                ", expectSupport=" + expectSupport +
                ", probabilisticSuppport=" + probabilisticSuppport +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }
}
