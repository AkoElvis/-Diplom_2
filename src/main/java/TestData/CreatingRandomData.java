package TestData;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKoliaevString() {return "KoliaevAAA" + new Random().nextInt(100);}
    public static String getRandomKoliaevEmail() { return "KoliaevAAA" + new Random().nextInt(100) + "@example" + new Random().nextInt(100) + ".com";}
    public static int getRandomInt() { return  new Random().nextInt(100);}
}
