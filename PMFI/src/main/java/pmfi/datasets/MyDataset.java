package pmfi.datasets;

import org.apache.commons.math3.distribution.NormalDistribution;
import pmfi.entities.UncertainDatabase;
import pmfi.entities.UncertainItemset;
import pmfi.entities.UncertainTransaction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MyDataset {
    private final UncertainDatabase<Integer> uncertainDatabase;
    private final double mean;
    private final double variance;
    private final NormalDistribution distribution;

    public MyDataset(String filePath, double mean, double variance) {
        this.mean = mean;
        this.variance = variance;
        this.distribution = new NormalDistribution(mean, Math.sqrt(variance));
        this.uncertainDatabase = getDatasetFromFile(filePath);


    }

    private double calcProbabilistic(double x, double mean, double variance){
        return (1.0 / Math.sqrt(2 * Math.PI * variance)) * Math.exp(-Math.pow(x - mean, 2)/(2 * variance));
    }

    private UncertainDatabase<Integer> getDatasetFromFile(String filePath) {
        UncertainDatabase<Integer> _uncertainDatabase = new UncertainDatabase<>();

        //doc file thu 1
        try{
            File myObj = new File(filePath);
            Scanner sc = new Scanner(myObj);
            while (sc.hasNextLine()){
                UncertainTransaction<Integer> curTransaction = new UncertainTransaction<>();

                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                for (String s : dataLineTransaction) {
                    int tempData = Integer.parseInt(s);

                    //double probabilisticData = calcProbabilistic(tempData, this.mean, this.variance);

                    double probabilisticData = distribution.density(tempData);
                    UncertainItemset<Integer> uncertainItemset = new UncertainItemset<>(tempData, probabilisticData);
                    curTransaction.getTransaction().add(uncertainItemset);
                }

                _uncertainDatabase.getUncertainTransactions().add(curTransaction);

                //System.out.println(dataLineTransaction);
                //a.add(data);
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
