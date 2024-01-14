package wPFI.algorithm;

import org.apache.commons.math3.distribution.PoissonDistribution;
import wPFI.entities.UncertainDatabase;
import wPFI.entities.UncertainTransaction;
import wPFI.entities.WeightedTable;
import wPFI.supports.ProbabilisticFrequentItemset;

import java.util.*;

/**
 *
 * @param <E> type of data
 */
public class AlgoW_PFI_Apriori <E> {
    private UncertainDatabase<E> uncertainDatabase;
    private WeightedTable<E> weightedTable;
    /**
     * the size of dataset
     */
    private Integer n;
    private double minSupport;
    private double minConfidence;

    public AlgoW_PFI_Apriori(UncertainDatabase uncertainDatabase, WeightedTable weightedTable, double minSupport, double minConfidence) {
        this.uncertainDatabase = uncertainDatabase;
        this.weightedTable = weightedTable;
        this.n = uncertainDatabase.getUncertainDatabase().size();
        this.minSupport = minSupport;
        this.minConfidence = minConfidence;
    }

    /**
     * This method run wPFI Apriori algorithm
     * @param scaleFactor
     * @param isProbabilityModelBase
     * @return wPFI collection
     */
    public Set<Set<E>> runAlgorithm(double scaleFactor, boolean isProbabilityModelBase){
        //Itemset I is the set of all the distinct itemset in Uncertain database (size-1 itemset)
        Set<Set<E>> I = uncertainDatabase.getDistinctItemset();

        Set<Set<E>> wPFI_k = Scan_Find_Size_k_wPFI(I); // size k = 1
        System.out.println("size-1 wPFI;");
        System.out.println(wPFI_k);

        // Add WPFIk-1 into WPFI.
        Set<Set<E>> wPFI = new HashSet<>(wPFI_k);

        //current size
        int k = 2;

        //find all size-k wPFI
        while(!wPFI_k.isEmpty()){
            Set<Set<E>> Ck = wPFIAprioriGen(wPFI_k, I, scaleFactor, isProbabilityModelBase);
            wPFI_k = Scan_Find_Size_k_wPFI(Ck); // size k
            wPFI.addAll(wPFI_k);

            System.out.printf("size-%d wPFI:%n", k);
            System.out.println(wPFI_k);
            k++;
        }

        return wPFI;
    }

    /**
     * Find all size-k itemset is wPFI
     * @param candidateK
     * @return size-k itemsets is wPFI
     */
    private Set<Set<E>> Scan_Find_Size_k_wPFI(Set<Set<E>> candidateK) {
        Set<Set<E>> wPFICollection = new HashSet<>();

        //check itemset is wPFI
        for(Set<E> itemset : candidateK){
            ProbabilisticFrequentItemset probabilisticFrequentItemset
                    = new ProbabilisticFrequentItemset(uncertainDatabase, weightedTable, itemset);

            if(probabilisticFrequentItemset
                    .isWeightedProbabilisticFrequentItemset(minSupport, minConfidence)
            ){
                wPFICollection.add(itemset);
            }
        }

        return wPFICollection;
    }

    /**
     * This method decide which wPFICandidateGen will use
     * @param wPFIprev previous wPFI
     * @param I size-1 itemset
     * @param alpha scale factor
     * @param isProbabilityModelBase true - use probability model; false - not use
     * @return wPFI collection
     */
    public Set<Set<E>> wPFIAprioriGen( Set<Set<E>> wPFIprev,
                                       Set<Set<E>> I,
                                       double alpha,
                                       boolean isProbabilityModelBase) {
        // Decide which candidate generation method to use based on the probability model flag
        if (isProbabilityModelBase) {
            return wPFICandidateGenPruningProbabilityModelBase(wPFIprev, I, alpha);
        } else {
            return wPFICandidateGenPruning(wPFIprev, I);
        }
    }

    /**
     * This method will generate wPFI candidate by using Probability Model
     * @param wPFIprev previous wPFI
     * @param itemsetI size-1 itemset
     * @param alpha scale factor
     * @return wPFI candidate by using Probability Model
     */
    private Set<Set<E>> wPFICandidateGenPruningProbabilityModelBase(Set<Set<E>> wPFIprev, Set<Set<E>> itemsetI, double alpha) {
        //Initialize Ck = [];
        Set<Set<E>> Ck = new HashSet<>();

        //Set<Set<E>> _I = new HashSet<>(wPFIprev);
        Set<E> _I = new HashSet<>();
        for(Set<E> itemset : wPFIprev){
            _I.addAll(itemset);
        }

        //Find maximum weighted in Weighted table
        double maxWeighted = weightedTable.findMaxWeighted();
        double _mu = calculateMu_(0, n, maxWeighted);

        for(Set<E> X : wPFIprev){
            //init differentSet = _I - X
            Set<E> differentSet = new HashSet<>();
            differentSet.addAll(_I);
            differentSet.removeAll(X);

            for(E Ii: differentSet){
                //init union candidate (X union Ii)
                Set<E> unionCandidate = new HashSet<>();
                unionCandidate.addAll(X);
                unionCandidate.add(Ii);

                //check weighted of union candidate >= minimum threshold
                if(weightedTable.getWeighedItemset(unionCandidate) >= this.minConfidence){
                    //caculate mu of X and mu of Ii
                    double muX = calculateMeanItemset(X);
                    double muIi = calculateMeanItemset(new HashSet<>(List.of(Ii)));

                    if(Math.min(muX, muIi) >= _mu && muX * muIi >= alpha * n * _mu ){
                        Ck.add(unionCandidate);
                    }

                }
            }

            double Im = weightedTable.findMinWeightItemset(X);

            //init differentSet = I - I' - X
            differentSet.clear();
            differentSet = new HashSet<>();

            for(Set<E> itemset : itemsetI){
                differentSet.addAll(itemset);
            }

            differentSet.removeAll(_I);
            differentSet.removeAll(X);

            for(E Ii: differentSet){
                //init union candidate ( X union Ii)
                Set<E> unionCandidate = new HashSet<>();
                unionCandidate.addAll(X);
                unionCandidate.add(Ii);

                //check weighted of union candidate >= minimum threshold
                if(weightedTable.getWeighedItemset(unionCandidate) >= this.minConfidence && weightedTable.getWeighedItemset(new HashSet<>(List.of(Ii))) < Im){
                    //caculate mu of X and mu of Ii
                    double muX = calculateMeanItemset(X);
                    double muIi = calculateMeanItemset(new HashSet<>(List.of(Ii)));

                    if( Math.min(muX, muIi) >= _mu
                        &&
                        muX * muIi >= alpha * n * _mu ){
                        Ck.add(unionCandidate);
                        //unionCandidate.clear();
                    }
                }
            }
        }

        return Ck;
    }

    /**
     * Calculate mu of itemset in UD
     * @param itemset
     * @return mu of itemset
     */
    private double calculateMeanItemset(Set<E> itemset) {
        double mu = 0;
        for(UncertainTransaction<E> uncertainTransaction : this.uncertainDatabase.getUncertainDatabase()){
            mu += uncertainTransaction.getProbabilistic(itemset);
        }
        return mu;
    }

    /**
     * This method approximates the mu_ threshold using a binary search algorithm.
     * @param lower lower bound in binary search
     * @param upper upper bound in binary search
     * @param maxWeight maximal weighted in weighted table
     * @return mu_ threshold.
     */
    private double calculateMu_(
            int lower,
            int upper,
            double maxWeight
    ) {

        double epsilon = 0.000001;
        double lowerBound = (double) lower;
        double upperBound = (double) upper;

        //binary search to find the solution
        while (upperBound - lowerBound > epsilon) {
            //double value = 1 - CDF(minSupport - 1, (upperDouble + lowerDouble) / 2.0) - minConfidence / maxWeight;

            double mid = (lowerBound + upperBound) / 2;
            double result = equation(minSupport, mid, minConfidence, maxWeight);

            if (result > 0)
                upperBound = mid;
            else if (result < 0)
                lowerBound = mid;
            else
                return mid;
        }

        return (upperBound + lowerBound) / 2.0;
    }

    /**
     * Function to calculate 1 - F(mSup-1, μ) - t/m
     * @param mSup min support
     * @param mu mean
     * @param t min confidence
     * @param maxWeighted maximum weighted
     * @return 1 - F(mSup-1, μ) - t/m
     */
    private double equation(double mSup, double mu, double t, double maxWeighted) {
        PoissonDistribution poisson = new PoissonDistribution(mu);
        return 1 - poisson.cumulativeProbability((int) mSup - 1) - t / maxWeighted;
    }

    /**
     * generate size-k weighted PFI candidate set Ck
     * @param wPFItemsetPrev
     * @param itemsetI set of size-1 itemset
     * @return size-k weighted PFI candidate set Ck
     */
    private Set<Set<E>> wPFICandidateGenPruning(Set<Set<E>> wPFItemsetPrev, Set<Set<E>> itemsetI) {
        //Initialize Ck = [];
        Set<Set<E>> Ck = new HashSet<>();

        //create _I
//        Set<Set<E>> _I = new HashSet<>(wPFItemsetPrev);
        Set<E> _I = new HashSet<>();
        for(Set<E> itemset : wPFItemsetPrev){
            _I.addAll(itemset);
        }

        for(Set<E> X : wPFItemsetPrev){
            //init differentSet = _I - X
            Set<E> differentSet = new HashSet<>();
            differentSet.addAll(_I);
            differentSet.removeAll(X);

            for(E Ii: differentSet){
                Set<E> unionCandidate = new HashSet<>();
                unionCandidate.addAll(X);
                unionCandidate.add(Ii);

                if(weightedTable.getWeighedItemset(unionCandidate) >= this.minConfidence){
                    Ck.add(unionCandidate);
                }
            }

            double Im = weightedTable.findMinWeightItemset(X);

            //init differentSet = I - I' - X
            differentSet.clear();
            differentSet = new HashSet<>();

            for(Set<E> itemset : itemsetI){
                differentSet.addAll(itemset);
            }

            differentSet.removeAll(_I);
            differentSet.removeAll(X);

            for(E Ii: differentSet){
                Set<E> unionCandidate = new HashSet<>();
                unionCandidate.addAll(X);
                unionCandidate.add(Ii);

                if(weightedTable.getWeighedItemset(unionCandidate) >= this.minConfidence && weightedTable.getWeighedItemset(new HashSet<>(List.of(Ii))) < Im){
                    Ck.add(unionCandidate);
                }
            }
        }

        return Ck;
    }

}
