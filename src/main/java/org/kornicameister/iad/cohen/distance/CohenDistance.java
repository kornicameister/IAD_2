package org.kornicameister.iad.cohen.distance;

import org.kornicameister.iad.cohen.CohenPoint;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenDistance {
    double distance(CohenPoint point, CohenPoint point2);
}
