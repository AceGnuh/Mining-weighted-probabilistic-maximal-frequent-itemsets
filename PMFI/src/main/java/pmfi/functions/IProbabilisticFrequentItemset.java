package pmfi.functions;

public interface IProbabilisticFrequentItemset {
    int calculateProbabilisticSupport(double minProbabilisticConfidence);
    boolean isProbabilisticFrequentItemset(double minSupport, double minProbabilisticConfidence);
    boolean isProbabilisticMaximalFrequentItemset(double minSupport, double minProbabilisticConfidence);
}
