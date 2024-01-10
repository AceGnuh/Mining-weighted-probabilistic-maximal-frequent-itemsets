import test.*;

public class Main {
    public static void main(String[] args) {
        double minimumSupport = 0.5;
        double minRelativeSupport = 0.9;
        double minimumConfidence = 0.9;

        //Test.example1();
        //Test.example2();

        TestT40I10D100K.testMinimumSupport(minimumSupport, minimumConfidence);
        //TestT40I10D100K.testMinimumConfidence(minRelativeSupport, minimumConfidence);

//        TestConnect4.testMinimumSupport(minimumSupport, minimumConfidence);
//        TestConnect4.testMinimumConfidence(minRelativeSupport, minimumConfidence);

//        TestAccidents.testMinimumSupport(minimumSupport, minimumConfidence);
//        TestAccidents.testMinimumConfidence(minRelativeSupport, minimumConfidence);

//        TestGAZELLE.testMinimumSupport(minimumSupport, minimumConfidence);
//        TestGAZELLE.testMinimumConfidence(minRelativeSupport, minimumConfidence);

    }

}