package pmfi.utils;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainTransaction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @param <E> data type of item
 */
public class DatasetUtil<E> {
    private final UncertainDatabase<E> uncertainDatabase;

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
            while (sc.hasNextLine()){
                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                UncertainTransaction<E> curTransaction = new UncertainTransaction<>();

                for (String s : dataLineTransaction) {
                    int tempData = Integer.parseInt(s);

                    //using normal distribution to calculate probabilistic for item
                    double probabilisticData = distribution.probability(tempData - 0.5, tempData + 0.5);
                    curTransaction.getTransaction().put((E) s, probabilisticData);
                }

                curTransaction.setID(++currIdTransaction);
                _uncertainDatabase.getUncertainDatabase().add(curTransaction);
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        return _uncertainDatabase;
    }

    public UncertainDatabase<E> getUncertainDatabase() {
        return uncertainDatabase;
    }
}
