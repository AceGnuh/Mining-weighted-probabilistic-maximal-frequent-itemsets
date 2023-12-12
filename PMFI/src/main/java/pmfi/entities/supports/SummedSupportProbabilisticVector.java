package pmfi.entities.supports;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummedSupportProbabilisticVector <E>{
    private final List<E> inputItem;
    private final UncertainDatabase<E> uncertainDatabase;
    private final List<Double> summedSupportProbabilisticVector;

    public SummedSupportProbabilisticVector(UncertainDatabase<E> uncertainDatabase, List<E> inputItem) {
        this.inputItem = inputItem;
        this.uncertainDatabase = uncertainDatabase;
        this.summedSupportProbabilisticVector = new ArrayList<>();
    }

    public List<Double> getSummedSupportProbabilisticVector() {
        double[] summedSPVector = this.DC(this.uncertainDatabase.getUncertainTransactions(), this.inputItem);

        for(int i = 0; i < summedSPVector.length; i++){
            this.summedSupportProbabilisticVector.add(summedSPVector[i]);
        }

        return summedSupportProbabilisticVector;
    }

    private double[] DC(List<UncertainTransaction<E>> transactions, List<E> pattern) {
        int n = transactions.size();

        // Base case
        if (n == 1) {
            double pX = transactions.get(0).getProbabilistic(pattern);

            return new double[]{round(1.0 - pX, 3), round(pX, 3)};
        }

        // Divide
        List<UncertainTransaction<E>> D1 = transactions.subList(0, n / 2);
        List<UncertainTransaction<E>> D2 = transactions.subList(n / 2, n);

        // Recursive calls
        double[] f1X = DC(D1, pattern);
        double[] f2X = DC(D2, pattern);

        // Convolution using FFT

        return convolutionFFT(f1X, f2X);

        // Convolution
        /*
        int len = f1X.length + f2X.length - 1;
        double[] fX = new double[len];
        for (int k = 0; k < len; k++) {
            for (int i = 0; i <= k; i++) {
                if (i < f1X.length && (k - i) < f2X.length) {
                    fX[k] += f1X[i] * f2X[k - i];
                }
            }
        }

        return fX;
         */
    }

    private double[] convolutionFFT(double[] f1X, double[] f2X){
        // Độ dài của vector kết quả tích chập
        int resultLength = f1X.length + f2X.length - 1;

        // Đưa độ dài của vector kết quả lên là một số mũ của 2 để sử dụng FFT hiệu quả hơn
        int fftLength = 1;
        while (fftLength < resultLength) {
            fftLength *= 2;
        }

        // Sử dụng FFT để tính toán tích chập
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftVector1 = transformer.transform(Arrays.copyOf(f1X, fftLength), TransformType.FORWARD);
        Complex[] fftVector2 = transformer.transform(Arrays.copyOf(f2X, fftLength), TransformType.FORWARD);

        // Nhân phổ của hai vector
        Complex[] fftResult = new Complex[fftLength];
        for (int i = 0; i < fftLength; i++) {
            fftResult[i] = fftVector1[i].multiply(fftVector2[i]);
        }

        // Sử dụng FFT ngược để chuyển đổi lại kết quả
        Complex[] convolutionResult = transformer.transform(fftResult, TransformType.INVERSE);

        // Lấy phần thực của kết quả
        double[] realResult = new double[resultLength];
        for (int i = 0; i < resultLength; i++) {
            realResult[i] = convolutionResult[i].getReal();
        }

        return realResult;
    }

    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
