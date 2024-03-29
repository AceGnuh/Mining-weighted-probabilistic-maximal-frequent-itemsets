import test.*;

public class Main {
    public static void main(String[] args) {

        if(args[0].equals("test-ex1")){
            Test.example1();

            return;
        }

        if(args[0].equals("test-ex2")){
            Test.example2();

            return;
        }

        if(args.length != 4 ){
            System.out.println("Please input 4 arguments: \nalgorithms (1: WPMFIM; 2:AWPMFIM), \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
            return;
        }

        try{
            String nameDataset = args[1];
            double minSupport = Double.parseDouble(args[2]);
            double minConfidence = Double.parseDouble(args[3]);

            if(args[0].equals("1")){
                TestUtil.testPMFI(nameDataset, null, 0, 0, minSupport, minConfidence);
            }else if(args[0].equals("2")){
                TestUtil.testPMFI(nameDataset, null, 0, 0, minSupport, minConfidence);
            }
            else{
                System.out.println("Please input 4 arguments: \nalgorithms (1: WPMFIM; 2:AWPMFIM), \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Please input 4 arguments: \nalgorithms (1: WPMFIM; 2:AWPMFIM), \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
        }
    }

}