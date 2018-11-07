import java.math.BigInteger;


public class Main {

    public static void main(String[] arg) {

        BigInteger zac = new BigInteger("3");
        zac = zac.pow(399);

        BigInteger kon = new BigInteger("3");
        kon = kon.pow(400);

        BigInteger nul = new BigInteger("0");
        BigInteger jed = new BigInteger("1");
        BigInteger detel = new BigInteger("3");


        for (BigInteger a = zac; a.compareTo(kon) <= 0; a = a.add(jed)) {
        }
    }
}
