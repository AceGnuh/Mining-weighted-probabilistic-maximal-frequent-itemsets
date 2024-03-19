package wPFI.utils;

import org.apache.commons.math3.distribution.NormalDistribution;

import wPFI.entities.UncertainDatabase;
import wPFI.entities.UncertainTransaction;
import wPFI.entities.WeightedTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @param <E> data type of item
 */
public class DatasetUtil<E> {
    private final UncertainDatabase<E> uncertainDatabase;

    private final int NUM_LINE = 100000;

    public DatasetUtil(String filePath, double mean, double variance) {
        this.uncertainDatabase = getUncertainDatabaseFromFile(filePath);
    }

    /**
     * Generate random weighted table
     * @param uncertainDatabase
     * @return weighted table
     * @param <E> type of data
     */
    public static <E> WeightedTable  generateWeightedTable(UncertainDatabase uncertainDatabase){
        WeightedTable<E> weightedTable = new WeightedTable<>();
        Map<E, Double> weightedItemset = new HashMap<>();

        //Get distinct itemset in DB
        Set<Set<E>> distinctItemsetDatabase = uncertainDatabase.getDistinctItemset();

        //init Random
        Random random = new Random(12345L);

        for(Set<E> itemset : distinctItemsetDatabase){
            for(E item : itemset){
                double weighted = (double) Math.round(random.nextDouble() * 10) / 10;
                weightedItemset.put(item, weighted);
            }
        }

        weightedTable.setWeighedTable(weightedItemset);

        return weightedTable;
    }

    /**
     * Read dataset and return uncertain database
     * @param filePath path of dataset
     * @return uncertain database
     */
    private UncertainDatabase<E> getUncertainDatabaseFromFile(String filePath) {
        UncertainDatabase<E> _uncertainDatabase = new UncertainDatabase<>();

        //read dataset
        try{
            File myObj = new File(filePath);
            Scanner sc = new Scanner(myObj);

            //get transaction in dataset
            int currIdTransaction = 0;

            //numline
            int numLine = 1;

            while (sc.hasNextLine() && numLine <= NUM_LINE){
                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                UncertainTransaction<E> curTransaction = new UncertainTransaction<>();

                for (String s : dataLineTransaction) {
                    String[] dataItem = s.split("-");

                    E value = (E) dataItem[0];
                    double prob = Double.parseDouble(dataItem[1]);

                    curTransaction.getTransaction().put(value, prob);
                }

                curTransaction.setID(++currIdTransaction);
                _uncertainDatabase.getUncertainDatabase().add(curTransaction);

                numLine++;
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        return _uncertainDatabase;
    }

    public static <E> void writeData(double mean, double variance){
        double std = Math.sqrt(variance);

        try{
            FileWriter fileWriter = new FileWriter("D:\\ChuyenDeNghienCuu\\wPMFI_wPFI\\src\\main\\java\\wPFI\\utils\\connect4.data");
            File fileReader = new File("D:\\ChuyenDeNghienCuu\\wPMFI_wPFI\\src\\main\\java\\wPFI\\utils\\connect.dat");

            Scanner sc = new Scanner(fileReader);


            Random random = new Random(12345L);

            int count = 0;

            while (sc.hasNextLine() && count < 100000 ){
                String data = sc.nextLine();
                String[] dataLineTransaction = data.split(" ");

                for (String s : dataLineTransaction) {
                    double prob = random.nextGaussian() * std + mean;

                    if(prob >= 1.0){
                        prob = 0.9;
                    }

                    if(prob <= 0){
                        prob = 0.1;
                    }

                    prob = (double) Math.round(prob*10)/10;

                    fileWriter.write(s + "-" + prob + " ");
                }

                fileWriter.write("\n");

                count++;
            }

            sc.close();
            fileWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public UncertainDatabase<E> getUncertainDatabase() {
        return uncertainDatabase;
    }
}
