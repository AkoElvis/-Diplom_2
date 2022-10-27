package TestData;
import java.util.Random;

public class CreatingRandomData {
    //public static String getRandomAlexeyKolyaevString() {return "AlexeyKoliaev" + new Random().nextInt(10);}
    //public static String getRandomAlexeyKolyaevEmail() { return "AlexeyKoliaev" + new Random().nextInt(10) + "@example" + new Random().nextInt(10) + ".com";}
    public static int getRandomInt(int bound) { return  new Random().nextInt(bound);}
    public static String getRandomAlexeyKolyaevString() {return "capricornus";}
    public static String getRandomAlexeyKolyaevEmail() {return "capricornus@cap.com";}

}