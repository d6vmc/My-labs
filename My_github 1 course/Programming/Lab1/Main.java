import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public class Main {
    public static void main(String []args) {
        int[] w = new int[] { 6,7,8,9,10,11,12,13,14,15,16,17};
        float[] x = new float[18];

        for (int i = 0; i < x.length; i++) {
            x[i] = (float) ThreadLocalRandom.current().nextFloat(-12.0f, 8.0f);
        }

        double[][] s = new double[12][18];

        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < x.length; j++){
                if (w[i] == 16) {
                    s[i][j] = w16(x[j]);
                }
                else if (w[i] == 7 || (w[i] >= 11 && w[i] <= 15)) {
                    s[i][j] = w7(x[j]);
                }
                else {
                    s[i][j] = w0(x[j]);
                }
            }
        }
        Print(s);
    }

    public static double w16(double x) {
        double f = Math.pow(Math.cbrt((3.0 + Math.tan(x)) / 4.0), 2);
        return f;
    }

    public static double w7(double x) {
        double pi = Math.PI;
        double e = Math.E;
        double f = Math.log(Math.abs(x) / (2 * pi + Math.pow(e, x)));
        return f;
    }

    public static double w0(double x) {
        double e = Math.E;
        double f = 1 / Math.pow(e, Math.pow(Math.tan(Math.atan(Math.pow(e, -Math.abs(x)))), 2));
        return Math.asin(Math.pow(f, 2));
    }

    public static void Print(double[][] s){
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 18; j++) {
                System.out.printf("%.3f \t", s[i][j]);
            }
            System.out.println();
        }
    }
}