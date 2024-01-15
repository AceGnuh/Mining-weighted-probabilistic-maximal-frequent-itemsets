package pmfi.pmfit;

import java.util.ArrayList;
import java.util.List;

public class ItemsetReturnData<E>{
    List<E> itemset;
    int returnValue;

    public ItemsetReturnData(List<E> itemset, int returnValue) {
        this.itemset = itemset;
        this.returnValue = returnValue;
    }

    public ItemsetReturnData(int returnValue) {
        this(new ArrayList<>(), returnValue);
    }

    @Override
    public String toString() {
        return "ItemsetReturnData{" +
                "itemset=" + itemset +
                ", returnValue=" + returnValue +
                '}';
    }
}
