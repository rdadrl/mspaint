package Variates;

public class NormalTruncated {
    public static double drawRandomNormalVariate(double variance, double mean, double cutoff) {
        double serviceTime = 0;

        while(serviceTime < cutoff)
        {serviceTime = randomNormalVariate(variance, mean);}
        return serviceTime;
    }


    public static double[] randomStandardNormalVariate() {
        double u1 = Math.random();
        double u2 = Math.random();

        double part1 = Math.sqrt(-2 * Math.log(u1));
        double part2 = 2 * Math.PI * u2;
        double x1 = part1 * Math.cos(part2);
        double x2 = part1 * Math.sin(part2);

        double[] x1x2 = {x1, x2};
        return x1x2;
    }

    public static double randomNormalVariate(double variance, double mean) {

        double[] x1x2 = randomStandardNormalVariate();
        double x1 = x1x2[0];
        //double x2 = x1x2[1];
        double y1 = (x1 * variance) + mean;
        //double y2 = (x2 * variance) + mean;

        return y1;
    }
}
