package pmfi.functions;

import pmfi.entities.SummedSupportProbabilisticItem;
import pmfi.entities.SupportProbabilisticItem;

import java.util.List;

public interface IFrequentItemset {
    double calculateExpectFrequentItemset();
    double calculateProbabilisticFrequentItemset(int minSupport);
    int calculateProbabilisticSupport(double minProbabilisticConfidence);
    boolean isProbabilisticFrequentItemset(int minSupport, double minProbabilisticConfidence);
    boolean isProbabilisticMaximalFrequentItemset(int minSupport, double minProbabilisticConfidence);
}
