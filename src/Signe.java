import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * signe [nom]: signe le message sur l'entrée standard par l'émetteur [nom] et sort la signature sur la sortie standard. On note que le programme va rechercher la clé
 * dans le fichier [nom].priv de l'utilisateur.
 */
public class Signe {
    public static void main(String[] arg) {

        // Lecture du message crypté sur l'entrée standard
        final Scanner scan = new Scanner(System.in);
        final List<BigInteger> blocks = new ArrayList<>();

        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            if (!line.startsWith("#")) {
                blocks.add(new BigInteger(line));
            }
        }

        // il faut obligatoirement spécifié le fichier contenant la clé
        if (arg.length != 1) {
            System.out.println("Usage : filename");
            return;
        }

        final String privateFilename = arg[0] + ".priv";

        // ouverture du fichier contenant la clé
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(privateFilename));
        } catch (IOException e) {
            System.err.println("Impossible de lire le fichier contenant la clé privée");
            return;
        }

        // récupération de la clé privée
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

                // affichage de la signature de chacun des blocs du message
                for (BigInteger block : blocks) {
                    final BigInteger signedBlock = block.modPow(a, n);
                    System.out.println(signedBlock);
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
