package pmfi.functions;

public interface IBound{
    double calculateLowerBound(double expectSupport, double minProbabilisticConfidence);
    double calculateUpperBound(double expectSupport, double minProbabilisticConfidence);
}
