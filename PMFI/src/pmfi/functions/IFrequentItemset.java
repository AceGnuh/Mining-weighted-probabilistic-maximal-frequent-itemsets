package pmfi.functions;

public interface IFrequentItemset {
    int calculateSupport();
    double calculateExpectFrequentItemset();
    double calculateProbabilisticFrequentItemset(int minSupport);
    int calculateProbabilisticSupport(double minProbabilisticConfidence);
    boolean isProbabilisticFrequentItemset(int minSupport, double minProbabilisticConfidence);
    boolean isProbabilisticMaximalFrequentItemset(int minSupport, double minProbabilisticConfidence);
    double calculateLowerBound(double expectSupport, double minProbabilisticConfidence);
    double calculateUpperBound(double expectSupport, double minProbabilisticConfidence);
}
