package TestData;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKolyaevAlexString() {return "KolyaevAlex" + new Random().nextInt(10);}
    public static String getRandomKolyaevAlexEmail() { return "KolyaevAlex" + new Random().nextInt(10) + "@example" + new Random().nextInt(10) + ".com";}
    public static int getRandomInt(int bound) { return  new Random().nextInt(bound);}
}
