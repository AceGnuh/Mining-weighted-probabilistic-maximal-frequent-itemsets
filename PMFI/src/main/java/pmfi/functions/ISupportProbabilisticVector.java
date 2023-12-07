package pmfi.functions;

import pmfi.entities.SummedSupportProbabilisticItem;
import pmfi.entities.SupportProbabilisticItem;

import java.util.List;

public interface ISupportProbabilisticVector <E>{
    List<SupportProbabilisticItem> calculateSupportProbabilistic(List<E> itemInput);
    List<SummedSupportProbabilisticItem> calculateSummedSupportProbabilistic(List<E> itemInput);
}
