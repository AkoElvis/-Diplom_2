package TestData;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKoliaevString() {return "Koliaev" + new Random().nextInt(10);}
    public static String getRandomKoliaevEmail() { return "Koliaev" + new Random().nextInt(10) + "@example" + new Random().nextInt(10) + ".com";}
    public static int getRandomInt() { return  new Random().nextInt(100);}
}
