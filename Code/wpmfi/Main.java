import test.*;

public class Main {
    public static void main(String[] args) {

        if(args.length != 4 ){
            System.out.println("Please input 4 arguments: \nalgorithms (1: WPMFIM; 2:AWPMFIM), \nname dataset (T40I10D100K_10K, connect4_10K, accidents_10K, us_10K), \nminimum support, \nminimum confidence!!!");
            return;
        }

        try{
            String nameDataset = args[1];
            double minSupport = Double.parseDouble(args[2]);
            double minConfidence = Double.parseDouble(args[3]);

            if(args[0].equals("1")){
                // if(args[1].equals("T40I10D100K")){
                //     TestT40I10D100K.testPMFI(minSupport, minConfidence);
                // }else if(args[1].equals("connect4")){
                //     TestConnect4.testPMFI(minSupport, minConfidence);
                // }else if(args[1].equals("accidents")){
                //     TestAccidents.testPMFI(minSupport, minConfidence);
                // }else if(args[1].equals("US")){
                //     TestUS.testPMFI(minSupport, minConfidence);
                // }else{
                //     System.out.println("Dataset not found!!!");
                // }
                TestUtil.testPMFI(nameDataset, null, 0, 0, minSupport, minConfidence);
            }else if(args[0].equals("2")){
                // if(args[1].equals("T40I10D100K")){
                //     TestT40I10D100K.testApproximatePMFIT(minSupport, minConfidence);
                // }else if(args[1].equals("connect4")){
                //     TestConnect4.testApproximatePMFIT(minSupport, minConfidence);
                // }else if(args[1].equals("accidents")){
                //     TestAccidents.testApproximatePMFIT(minSupport, minConfidence);
                // }else if(args[1].equals("US")){
                //     TestUS.testApproximatePMFIT(minSupport, minConfidence);
                // }else{
                //     System.out.println("Dataset not found!!!");
                // }
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