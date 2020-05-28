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

    public double nonStationaryrandomVariate(double lambda, double T) {
        double t = 0;
        int x = 0;

        while (t <= T) {
            double uniformRandom = Math.random();
            t -= Math.log(uniformRandom);
            if (t > T) return x;

            double s = Math.random();
            if (s <= randomVariate(lambda) / lambda) x++;
        }
        return x;
    }
}
