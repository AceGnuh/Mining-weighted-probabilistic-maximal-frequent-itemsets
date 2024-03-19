package pmfi.utils;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;
import pmfi.entities.WeightedTable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @param <E> data type of item
 */
public class DatasetUtil<E> {
    private final UncertainDatabase<E> uncertainDatabase;
    private final int NUM_LINE = 10000;

    public DatasetUtil(String filePath, double mean, double variance) {
        this.uncertainDatabase = getUncertainDatabaseFromFile(filePath, mean, variance);
    }

    /**
     * Read dataset and return uncertain database
     * @param filePath path of dataset
     * @param mean
     * @param variance
     * @return uncertain database
     */
    private UncertainDatabase<E> getUncertainDatabaseFromFile(String filePath, double mean, double variance) {
        UncertainDatabase<E> _uncertainDatabase = new UncertainDatabase<>();

        //init Normal Distribution
        NormalDistribution distribution = new NormalDistribution(mean, Math.sqrt(variance));

        //read dataset
        try{
            File myObj = new File(filePath);
            Scanner sc = new Scanner(myObj);

            //get transaction in dataset
            int currIdTransaction = 0;

            Random random = new Random(12345L);

            int countNumLine = 1;

            while (sc.hasNextLine()){
                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                UncertainTransaction<E> curTransaction = new UncertainTransaction<>();

                for (String s : dataLineTransaction) {
                    String[] dataItem = s.split("-");
//
//                    System.out.println(Arrays.toString(dataItem));

                    //using normal distribution to calculate probabilistic for item
                    //double probabilisticData = distribution.probability(tempData - 1, tempData + 1);
//                    E value = (E) s;
//                    double prob = mean + random.nextGaussian() * Math.sqrt(variance);
//                    if(prob <= 0 ){
//                        prob = 0.1;
//                    }
//
//                    if(prob >= 1){
//                        prob = 0.9;
//                    }
//
//                    prob = (double) Math.round(prob * 10)/10;

                    E value = (E) dataItem[0];
                    double prob = Double.parseDouble(dataItem[1]);

                    curTransaction.getTransaction().put((E) value, prob);
                }

                curTransaction.setID(++currIdTransaction);
                _uncertainDatabase.getUncertainDatabase().add(curTransaction);

                countNumLine++;
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        return _uncertainDatabase;
    }

    /**
     * Generate random weighted table
     * @param uncertainDatabase
     * @return weighted table
     * @param <E> type of data
     */
    public static <E> WeightedTable generateWeightedTable(UncertainDatabase uncertainDatabase){
        WeightedTable<E> weightedTable = new WeightedTable<>();
        Map<E, Double> weightedItemset = new HashMap<>();

        //Get distinct itemset in DB
        Set<E> distinctItemsetDatabase = uncertainDatabase.getDistinctItem();

        //init Random
        Random random = new Random(12345L);

        for(E item : distinctItemsetDatabase){
            double weighted = (double) Math.round(random.nextDouble() * 10) / 10;
            weightedItemset.put(item, weighted);
        }

        weightedTable.setWeighedTable(weightedItemset);

        return weightedTable;
    }

    public WeightedTable<E> getWeightedFromFile(String filePath) {
        WeightedTable<E> weightedTable = new WeightedTable<>();
        Map<E, Double> weightedMap = new HashMap();

        //read dataset
        try{
            File myObj = new File(filePath);
            Scanner sc = new Scanner(myObj);

            while (sc.hasNextLine()){
                String data = sc.nextLine();
                String[] dataItem = data.split("-");

                E item = (E) dataItem[0];
                double weighted = Double.parseDouble(dataItem[1]);

                weightedMap.put(item, weighted);
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        weightedTable.setWeighedTable(weightedMap);

        return weightedTable;
    }

    public UncertainDatabase<E> getUncertainDatabase() {
        return uncertainDatabase;
    }
}
