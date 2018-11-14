import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * verifie [nom] [fichier-signature] : vérifie que le fichier-signature est bien la signature du message donné sur l'entrée standard pour l'émetteur [nom]. Le
 * programme accèdera au fichier [nom].pub seulement.
 */
public class Verifie {
    public static void main(String[] arg) {

        // Lecture du message crypté sur l'entrée standard
        final Scanner scan = new Scanner(System.in);
        final List<BigInteger> messageBlocks = new ArrayList<>();

        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            if (!line.startsWith("#")) {
                messageBlocks.add(new BigInteger(line));
            }
        }

        // il faut obligatoirement spécifié le fichier contenant la clé publique ainsi que le signature
        if (arg.length != 2) {
            System.out.println("Usage : key.pub signature");
            return;
        }

        final String publicFilename = arg[0] + ".pub";
        final String signatureFilename = arg[1];

        // ouverture du fichier contenant la clé publique
        List<String> keyLines;
        try {
            keyLines = Files.readAllLines(Paths.get(publicFilename));
        } catch (IOException e) {
            System.err.println("Impossible de lire le fichier contenant la clé publique");
            return;
        }

        // récupération de la clé publique
        String publicKey = null;
        for (String line : keyLines) {
            if (!line.contains("#")) {
                publicKey = line;
                break;
            }
        }

        List<String> signatureLines = null;
        try {
            signatureLines = Files.readAllLines(Paths.get(signatureFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (signatureLines == null) {
            System.err.println("Impossible de lire le fichier contenant la signature");
            return;
        }

        List<BigInteger> signatureBlocks = new ArrayList<>();
        for (String line : signatureLines) {
            signatureBlocks.add(new BigInteger(line));
        }

        if (publicKey != null) {
            final String[] params = publicKey.split(" ");

            if (params.length == 3) {
                final int t = Integer.parseInt(params[0]);
                final BigInteger n = new BigInteger(params[1]);
                final BigInteger b = new BigInteger(params[2]);

                // affichage de la signature de chacun des blocs du message
                for (int i=0; i<messageBlocks.size() && i<signatureBlocks.size(); ++i) {

                    final BigInteger verifiedBlock = signatureBlocks.get(i).modPow(b, n);
                    final BigInteger messageBlock = messageBlocks.get(i);

                    if (!messageBlock.equals(verifiedBlock)) {
                        System.out.println("Verification echouée !");
                        return;
                    }

                }

                System.out.println("Le fichier est authentique !");

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
