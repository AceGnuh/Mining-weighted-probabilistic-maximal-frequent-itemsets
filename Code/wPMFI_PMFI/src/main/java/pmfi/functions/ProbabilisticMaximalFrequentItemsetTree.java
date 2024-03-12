package pmfi.functions;

import java.util.List;
import java.util.Set;

public interface ProbabilisticMaximalFrequentItemsetTree<E> {
    Set<List<E>> findAllWPMFI();
    void preOrder();
}
