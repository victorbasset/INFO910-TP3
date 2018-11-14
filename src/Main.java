public class Main {

    public static void main(String[] arg) {
        //Test Euclide étendu
        int p = 36163;
        int q = 21199;
        int vals[] = Utils.euclideEtendu(p, q);
        System.out.println("euclideEtendu(" + p + ", " + q + ") = " + vals[0]);
        System.out.println(vals[1] + "(" + p + ") + " + vals[2] + "(" + q + ") = " + vals[0]);
    }
}
