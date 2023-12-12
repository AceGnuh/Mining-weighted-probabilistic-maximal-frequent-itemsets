package pmfi.functions;

public interface IProbabilistic{
    int calculateProbabilisticSupport(double minProbabilisticConfidence);
    boolean isProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence);
    boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence);
}
