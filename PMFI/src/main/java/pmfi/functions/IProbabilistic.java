package pmfi.functions;

public interface IProbabilistic{
    int calculateProbabilisticSupport(double minProbabilisticConfidence);
    boolean isProbabilisticFrequentItemset(int minSupport, double minProbabilisticConfidence);
    boolean isProbabilisticMaximalFrequentItemset(int minSupport, double minProbabilisticConfidence);
}
