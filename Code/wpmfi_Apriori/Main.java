import wPFI.test.TestUtil;

public class Main {
    public static void main(String[] args) {

        if(args.length != 3 ){
            System.out.println("Please input 3 arguments: \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
            return;
        }

        try{
            String nameDataset= args[0];
            double minSupport = Double.parseDouble(args[1]);
            double minConfidence = Double.parseDouble(args[2]);

            TestUtil.test(nameDataset, minSupport, minConfidence);

        }catch(Exception e){
            System.out.println("Please input 3 arguments: \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
        }
    }

}