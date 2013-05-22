package org.kornicameister.iad.util;


import java.util.Random;

public class RandomDouble {

    public static double nextDouble(double start, double end) {
        Random random = new Random();
        long value = -1L;
        while (value < 0) {
            value = Math.abs(random.nextLong()); // Caution, Long.MIN_VALUE returns negative !
        }
        double valueAsDouble = (double) value;
        double diff = (end - start) / (double) Long.MAX_VALUE;
        return start + valueAsDouble * diff;
    }

}
