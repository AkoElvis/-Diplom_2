package TestData;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKolyaevAlexeyString() {return "KoliaevAlexey" + new Random().nextInt(10);}
    public static String getRandomKolyaevAlexeyEmail() { return "KoliaevAlexey" + new Random().nextInt(10) + "@example" + new Random().nextInt(10) + ".com";}
    public static int getRandomInt(int bound) { return  new Random().nextInt(bound);}
}
