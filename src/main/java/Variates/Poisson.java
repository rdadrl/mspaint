package Variates;

import java.io.*;

public class Poisson {
    /**
     * Poisson generator based upon the inversion by sequential search
     * This method is efficent and also capable of solving for small lambda values.
     */
    public static double randomVariate(double lambda) {
        int x = 0;
        double p = Math.exp(-lambda);
        double s = p;

        double uniformRandom  = Math.random();

        //examine cumulative probabilities
        while (uniformRandom > s) {
            x++;
            p *= lambda / x;
            s += p;
        }

        return x;
    }

    private static double t = 0;
    public static double nonStationaryrandomVariate(double rateMax, double Ttime) {

        double uiidx = Math.random();
        double uiidy = Math.random();

        while (uiidy > randomVariate(t) / rateMax) {
            t -= (1/rateMax) * Math.log(uiidx);
            uiidx = Math.random();
            uiidy = Math.random();
        }
        return t;
        /*double lambda = 0; // l(t) < lambda for all t < T

        for (int tTemp = 0; tTemp < Ttime; tTemp++) {
            double ltemp = randomVariate(tTemp);
            if (ltemp > lambda) lambda = ltemp;
        }
        double t = 0;
        int x = 0;

        while (t <= Ttime) {
            double uniformRandom = Math.random();
            t -= Math.log(uniformRandom) / lambda;
            if (t > Ttime) return x;

            double s = Math.random();
            if (s <= randomVariate(rate) / lambda) x++;
        }
        return x;*/
    }

    public static void main(String[] args) throws IOException {
        Poisson instance = new Poisson();
        File fout = new File(System.getProperty("user.dir") + "/results/poisson.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        double pastS = 0;
        for (int i = 0; i < 1440; i++) {
            pastS = instance.randomVariate(0.2);
            bw.write(Double.toString(pastS) + " ");
        }

        bw.close();
    }
}
