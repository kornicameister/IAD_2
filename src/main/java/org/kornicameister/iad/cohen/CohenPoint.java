package org.kornicameister.iad.cohen;

import org.kornicameister.iad.cohen.teachers.CohenTeacher;
import org.kornicameister.iad.kmeans.impl.CPoint;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CohenPoint extends CPoint {
    private CohenTeacher cohenTeacher;

    public CohenPoint(final CohenTeacher cohenTeacher, final Double... loc) {
        super(loc);
        this.cohenTeacher = cohenTeacher;
    }

    @Override
    public Object clone() {
        CohenPoint point = (CohenPoint) super.clone();
        point.cohenTeacher = this.cohenTeacher;
        return point;
    }

    public CohenTeacher getTeacher() {
        return cohenTeacher;
    }
}
