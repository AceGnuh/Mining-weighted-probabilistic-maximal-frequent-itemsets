package pmfi.algorithms;

import java.util.HashSet;
import java.util.Set;

public class ItemsetReturnData <E>{
    Set<E> itemset;
    int returnValue;

    public ItemsetReturnData(Set<E> itemset, int returnValue) {
        this.itemset = itemset;
        this.returnValue = returnValue;
    }

    public ItemsetReturnData(int returnValue) {
        this(new HashSet<>(), returnValue);
    }

    @Override
    public String toString() {
        return "ItemsetReturnData{" +
                "itemset=" + itemset +
                ", returnValue=" + returnValue +
                '}';
    }
}
