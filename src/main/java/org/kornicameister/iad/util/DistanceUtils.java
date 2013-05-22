package org.kornicameister.iad.util;

import com.google.common.base.Preconditions;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DistanceUtils {

    public static <T extends Number> double getEuclideanDistance(T[] doublesA, T[] doublesB) {
        Preconditions.checkArgument(doublesA.length == doublesB.length, "Attempting to compare two clusterables of different dimensions");
        double sum = 0;
        for (int i = 0; i < doublesA.length; i++) {
            sum += Math.pow(DistanceUtils.getEuclideanDistance(doublesA[i], doublesB[i]), 2);
        }
        return Math.sqrt(sum);
    }

    public static <T extends Number> double getEuclideanDistance(T t1, T t2) {
        return Math.abs(t1.doubleValue() - t2.doubleValue());
    }

    public static <T extends Number> double getManhattanDistance(T[] doublesA, T[] doublesB) {
        Preconditions.checkArgument(doublesA.length == doublesB.length, "Attempting to compare two clusterables of different dimensions");
        double sum = 0;
        for (int i = 0; i < doublesA.length; i++) {
            sum += DistanceUtils.getEuclideanDistance(doublesA[i], doublesB[i]);
        }
        return sum;
    }
}
