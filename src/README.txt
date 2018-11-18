INFO910 - TP3 RSA
Vincent PEILLEX - Victor BASSET

Etat d'avancement :
    Tous les programmes demandés sont réalisés et fonctionnels

Compilation :
    Se trouver dans le répertoire racine du projet puis -> javac *.java

Exécution pour chaque programme : java <Programme>
    Exemple : java GenCle

Fonctionnement :
    - gencle.jar : java -jar gencle.jar 2048 key
        Génère une clé de 2048 bits et retourne les deux fichiers key.priv et key.pub

    - chiffre.jar : cat message.txt | java -jar chiffre.jar key > chiffre.txt
        Crypte le contenu du fichier message.txt dans le fichier chiffre.txt

    - dechiffre.jar : cat chiffre.txt | java -jar dechiffre.jar key > dechiffre.txt
        Le fichier dechiffre.txt retourné contient bien le message orginal de message.txt

    - signe.jar : cat chiffre.txt | java -jar signe.jar key > signature.txt
        Retourne la signature du chiffrement 'chiffre.txt' dans le fichier signature.txt

    - verifie.jar : cat chiffre.txt | java -jar verifie.jar key signature.txt
        Indique sur la sortie standard si le fichier est authentique ou non en fonction de la signature spécifiée.


Le dossier <samples> contient les JARs précompilés et les fichiers que nous avons utilisés pour effectuer les tests.