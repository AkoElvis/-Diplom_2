package TestData;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKoliaevString() {return "AKoliaevAAA" + new Random().nextInt(100);}
    public static String getRandomKoliaevEmail() { return "AKoliaevAAA" + new Random().nextInt(100) + "@example" + new Random().nextInt(100) + ".com";}
    public static int getRandomInt(int bound) { return  new Random().nextInt(bound);}
}
