package org.kornicameister.iad.cohen;

import org.kornicameister.iad.kmeans.impl.CPoint;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CohenPoint extends CPoint {

    public CohenPoint(final Double... loc) {
        super(loc);
    }

    @Override
    public Object clone() {
        CohenPoint point = new CohenPoint();
        point.position = this.position.clone();
        return point;
    }


}
