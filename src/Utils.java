import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    public static ArrayList euclideEtendu(int a, int b) {
        /*
          r[0] = a; r[1] = b;
          s[0] = 1; s[1] = 0;
          t[0] = 0; t[1] = 1;
          q = r[0] div r[1];
          r[2] = r[0] - q*r[1];
          i = 2;
          Tant que r[i] > 0
            s[i] = s[i-2] - q*s[i-1];
            t[i] = t[i-2] - q*t[i-1];
            q = r[i-1] div r[i];
            i = i+1;
            r[i] = r[i-2] - q*r[i-1];
          Fin tant que
          Retourner r[i-1],s[i-1],t[i-1]
          // r[i-1]=pgcd(a,b)
          // et a*s[i-1]+b*t[i-1]=r[i-1]
        */

        int r[] = new int[0], s[] = new int[0], t[] = new int[0], q , i;
        r[0] = a; r[1] = b;
        s[0] = 1; s[1] = 0;
        t[0] = 0; t[1] = 1;
        q = r[0] / r[1];
        r[2] = r[0] - q*r[1];
        i = 2;

        while(r[i] > 0){
            s[i] = s[i-2] - q*s[i-1];
            t[i] = t[i-2] - q*t[i-1];
            q = r[i-1] / r[i];
            i = i+1;
            r[i] = r[i-2] - q*r[i-1];
        }
        ArrayList result = new ArrayList<>();
        result.add(r[i-1]);
        result.add(s[i-1]);
        result.add(t[i-1]);

        return result;
    }

    public static void writeStringInFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }
}
