import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * chiffre [nom] : chiffre le message sur l'entrée standard pour le destinataire de clé [nom].pub et sort le message chiffré sur la sortie standard
 */
public class Chiffre {
    public static void main(String[] arg) {
        String message = "mon message est un peu trop loin je suis vraiement pas content";

        if (arg.length != 1) {
            System.out.println("Usage : filename");
            return;
        }

        final String privateFilename = arg[0] + ".priv";
        final String publicFilename = arg[0] + ".pub";

        List<String> lines;
        try {
             lines = Files.readAllLines(Paths.get(privateFilename));
        } catch (IOException e) {
            System.err.println("Impossible de lire le fichier contenant la clé privée");
            return;
        }

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
                final int t = Integer.parseInt(params[0]);
                final BigInteger n = new BigInteger(params[1]);
                final BigInteger b = new BigInteger(params[2]);
                final BigInteger p = new BigInteger(params[3]);
                final BigInteger q = new BigInteger(params[4]);
                final BigInteger a = new BigInteger(params[5]);

                byte[] bytes = message.getBytes();
                List<BigInteger> splitedMessage = new ArrayList<>();

                // parcours du messag octet par octet
                int nbMessageBits = bytes.length * 8;
                int nbBlocks = nbMessageBits / t;
                if (nbMessageBits % t != 0) {
                    nbBlocks++;
                }
                int cursor = 0;

                System.out.println(nbBlocks);

                for (int i=0; i<nbBlocks; i++) {
                    String temp = "";
                    for (; cursor < cursor + t/8 && cursor < bytes.length; cursor++) {
                        temp += bytes[cursor];
                    }
                    final BigInteger block = new BigInteger(temp);
                    splitedMessage.add(block);
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
}
