import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * gencle [t] [nom] : génère une paire de clé pour des blocs de taille [t] et les sauve dans les fichiers [nom].pub et [nom].priv
 */
public class GenCle {
    public static void main(String[] arg) {
        if (arg.length != 2) {
            System.err.println("Usage : GenCle [t] [nom]");
            return;
        }

        final int blocSize = Integer.parseInt(arg[0]);
        final String filename = arg[1];

        final Random randomGenerator = new Random();

        final BigInteger p = new BigInteger(
                ThreadLocalRandom.current().nextInt(blocSize / 2, blocSize / 2 + 16),
                randomGenerator).nextProbablePrime();

        final BigInteger q = new BigInteger(
                ThreadLocalRandom.current().nextInt(blocSize / 2, blocSize / 2 + 16),
                randomGenerator).nextProbablePrime();

        final BigInteger n = p.multiply(q);

//        System.out.println("n premier ? " + p.isProbablePrime(1));
//        System.out.println("n premier ? " + q.isProbablePrime(1));
//        System.out.println("n premier ? " + n.isProbablePrime(1));

        final BigInteger N = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        final BigInteger b = BigInteger.valueOf(2).pow(16).add(BigInteger.ONE);
        final BigInteger a = b.modInverse(N);

        final String publicKey = blocSize + " " + n.toString() + " " + b.toString();
        final String privateKey = publicKey + " " + p.toString() + " " + q.toString() + " " + a.toString();

        try {
            Utils.writeStringInFile(filename + ".pub", publicKey);
            Utils.writeStringInFile(filename + ".priv", privateKey);
        } catch (IOException e) {
            System.err.println("Impossible de créer les fichiers");
        }

        // display public key
        System.out.println("public RSA key : t n b");
        System.out.println(publicKey);
    }
}
