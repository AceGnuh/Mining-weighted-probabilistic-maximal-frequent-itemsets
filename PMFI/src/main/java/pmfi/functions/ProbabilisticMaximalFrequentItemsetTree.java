package pmfi.functions;

import java.util.List;

public interface ProbabilisticMaximalFrequentItemsetTree<E> {
    List<List<E>> findAllPMFI();
    void preOrder();
}
