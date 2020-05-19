package utils;

public class MathUtils {

    public static boolean areEquals(double d1, double d2, double epsilon) {
        return ((Math.abs(d1-d2)) < epsilon);
    }

    public static double exp(double baseDist, double dist, double temp) {
        return Math.exp((-((dist - baseDist))/temp));
    }

}
