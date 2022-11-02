package testdata;
import java.util.Random;

public class CreatingRandomData {
    public static String getRandomAlexeyKolyaevString() {return "AleksKo" + new Random().nextInt(10);}
    public static String getRandomAlexeyKolyaevEmail() { return "AleksKo" + new Random().nextInt(10) + "@idscan" + new Random().nextInt(10) + ".api";}
    public static int getRandomInt(int bound) { return  new Random().nextInt(bound);}
}