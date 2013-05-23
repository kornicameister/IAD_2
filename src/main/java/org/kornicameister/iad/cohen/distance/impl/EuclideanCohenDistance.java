package org.kornicameister.iad.cohen.distance.impl;

import org.kornicameister.iad.cohen.CohenPoint;
import org.kornicameister.iad.cohen.distance.CohenDistance;
import org.kornicameister.iad.cohen.util.CohenUtilities;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class EuclideanCohenDistance implements CohenDistance {
    @Override
    public double distance(final CohenPoint point, final CohenPoint point2) {
        return CohenUtilities.DistanceMetrics.getEuclideanDistance(point, point2);
    }
}
