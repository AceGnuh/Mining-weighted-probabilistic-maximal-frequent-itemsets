package pmfi.config;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainItemset;
import pmfi.entities.UncertainTransaction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MyDataset {
    private final UncertainDatabase<Integer> uncertainDatabase;

    public MyDataset(String filePath, double mean, double variance) {
        this.uncertainDatabase = getUncertainDatabaseFromFile(filePath, mean, variance);
    }

//    private double calcProbabilistic(double x, double mean, double variance){
//        return (1.0 / Math.sqrt(2 * Math.PI * variance)) * Math.exp(-Math.pow(x - mean, 2)/(2 * variance));
//    }

    /**
     * Read dataset, return uncertain database with probabilistic of each item
     * @param filePath path of dataset
     * @param mean
     * @param variance
     * @return uncertain database
     */
    private UncertainDatabase<Integer> getUncertainDatabaseFromFile(String filePath, double mean, double variance) {
        UncertainDatabase<Integer> _uncertainDatabase = new UncertainDatabase<>();

        //init Normal Distribution
        NormalDistribution distribution = new NormalDistribution(mean, Math.sqrt(variance));

        //doc dataset
        try{
            File myObj = new File(filePath);
            Scanner sc = new Scanner(myObj);

            int currIdTransaction = 0;
            while (sc.hasNextLine()){
                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                UncertainTransaction<Integer> curTransaction = new UncertainTransaction<>();

                for (String s : dataLineTransaction) {
                    int tempData = Integer.parseInt(s);

                    //double probabilisticData = calcProbabilistic(tempData, this.mean, this.variance);

                    double probabilisticData = distribution.density(tempData);
                    //UncertainItemset<Integer> uncertainItemset = new UncertainItemset<>(tempData, probabilisticData);

                    if (probabilisticData > Math.pow(10, -30)) { // xs item đủ lớn
                        curTransaction.getTransaction().put(tempData, probabilisticData);
                    }
                }

                curTransaction.setID(++currIdTransaction);
                _uncertainDatabase.getUncertainTransactions().add(curTransaction);
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        return _uncertainDatabase;
    }

    public UncertainDatabase<Integer> getUncertainDatabase() {
        return uncertainDatabase;
    }
}
