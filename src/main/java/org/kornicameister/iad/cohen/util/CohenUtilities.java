package org.kornicameister.iad.cohen.util;

import com.google.common.base.Preconditions;
import org.kornicameister.iad.cohen.CohenPoint;
import org.kornicameister.iad.util.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CohenUtilities {
    public static List<CohenPoint> normalizePoints(final List<CohenPoint> cohenPoints) {
        List<CohenPoint> normalizedInput = new ArrayList<>();
        double powerSum = 0.0;
        for (CohenPoint cohenPoint : cohenPoints) {
            for (Double loc : cohenPoint.getLocation()) {
                powerSum += Math.pow(loc, 2);
            }
            normalizedInput.add(CohenUtilities.getNormalizedValue(cohenPoint, Math.sqrt(powerSum)));
            powerSum = 0.0;
        }
        return normalizedInput;
    }

    private static CohenPoint getNormalizedValue(final CohenPoint cohenPoint, final double normalizingValue) {
        Double[] normalizedLoc = cohenPoint.getLocation().clone();
        for (int i = 0 ; i < cohenPoint.getLocation().length ; i++) {
            normalizedLoc[i] = normalizedLoc[i] / normalizingValue;
        }
        CohenPoint point = new CohenPoint(cohenPoint.getTeacher(), normalizedLoc);
        return point;
    }

    public static class DistanceMetrics {

        public static double getEuclideanDistance(CohenPoint cohenPoint, CohenPoint cohenPoint2) {
            Preconditions.checkArgument(cohenPoint.getPosition().length == cohenPoint2.getPosition().length,
                    "Attempting to compare two CohenPoints of different dimensions");
            double sum = 0;
            final int length = cohenPoint.getPosition().length;
            for (int i = 0 ; i < length ; i++) {
                sum += Math.pow(DistanceUtils.getEuclideanDistance(cohenPoint.getN(i), cohenPoint2.getN(i)), 2);
            }
            return Math.sqrt(sum);
        }

        public static double getManhattanDistance(CohenPoint cohenPoint, CohenPoint cohenPoint2) {
            Preconditions.checkArgument(cohenPoint.getPosition().length == cohenPoint2.getPosition().length,
                    "Attempting to compare two CohenPoints of different dimensions");
            double sum = 0;
            final int length = cohenPoint.getPosition().length;
            for (int i = 0 ; i < length ; i++) {
                sum += DistanceUtils.getEuclideanDistance(cohenPoint.getN(i), cohenPoint2.getN(i));
            }
            return sum;
        }
    }
}
