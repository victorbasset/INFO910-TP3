import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * chiffre [nom] : chiffre le message sur l'entrée standard pour le destinataire de clé [nom].pub et sort le message chiffré sur la sortie standard
 */
public class Chiffre {
    public static void main(String[] arg) {

        // Lecture du message claire sur l'entrée standard
        final Scanner scan = new Scanner(System.in);
        final StringBuilder message = new StringBuilder();
        while (scan.hasNextLine()) {
            message.append(scan.nextLine());
            if (scan.hasNextLine()) {
                message.append('\n');
            }
        }

        // Il faut obligatoirement spécifier un fichier
        if (arg.length != 1) {
            System.out.println("Usage : filename");
            return;
        }

        // TODO : only use public file
        final String privateFilename = arg[0] + ".priv";
        final String publicFilename = arg[0] + ".pub";

        // Lecture du fichier spécifié
        List<String> lines;
        try {
             lines = Files.readAllLines(Paths.get(privateFilename));
        } catch (IOException e) {
            System.err.println("Impossible de lire le fichier spécifié");
            return;
        }

        // Lecture de la clé dans le fichier
        String privateKey = null;
        for (String line : lines) {
            if (line.contains("#")) {
                continue;
            }
            privateKey = line;
        }

        if (privateKey != null) {
            final String[] params = privateKey.split(" ");

            if (params.length == 6) {

                // On parse la clé en variables
                final int t = Integer.parseInt(params[0]);
                final BigInteger n = new BigInteger(params[1]);
                final BigInteger b = new BigInteger(params[2]);
                final BigInteger p = new BigInteger(params[3]);
                final BigInteger q = new BigInteger(params[4]);
                final BigInteger a = new BigInteger(params[5]);

                final List<BigInteger> splitedMessage = splitMessage(message.toString().getBytes(), t);
                final List<BigInteger> cryptedBlocks = new ArrayList<>();

                // crypte chaque bloc de taille t
                for (BigInteger block : splitedMessage) {
                   cryptedBlocks.add(block.modPow(b, n));
                }

                // affiche les blocs sur la sortie standard
                for (BigInteger block : cryptedBlocks) {
                    System.out.println(block);
                }

            }
            else {
                System.err.println("La clé privée n'est pas correctement formatée");
            }
        }
        else {
            System.err.println("Impossible de récupérer la clé privée dans le fichier");
        }
    }

    /**
     * Découpe un message en block de taille <blockSize>
     *
     * @param message byte[]
     * @param blockSize int
     * @return List of BigInteger
     */
    private static List<BigInteger> splitMessage(byte[] message, int blockSize) {
        final List<BigInteger> splitedMessage = new ArrayList<>();

        final int nbMessageBits = message.length * 8;
        int nbBlocks = nbMessageBits / blockSize;
        if (nbMessageBits % blockSize != 0) {
            nbBlocks++;
        }

        for (int i=0; i<nbBlocks; i++) {
            final byte[] slice = Arrays.copyOfRange(message, i * blockSize/8, i * blockSize/8 + blockSize/8);
            splitedMessage.add(new BigInteger(slice));
        }

        return splitedMessage;
    }
}
