package utils;

import java.util.Random;

public class RandUtils {

    /**
     * Returns a random int, lower bound included, upper bound excluded
     * @param min lower bound
     * @param max upper bound
     * @return a random int in [min, max[
     */
    public static int randInt(int min , int max) {
        return (int) ( min + new Random().nextDouble() * (max - min) );
    }
}
