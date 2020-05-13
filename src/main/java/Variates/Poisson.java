package Variates;

public class Poisson {
    /**
     * Poisson generator based upon the inversion by sequential search
     * This method is efficent and also capable of solving for small lambda values.
     */
    public double randomVariate(double lambda) {
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
}
