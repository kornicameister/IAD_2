package org.kornicameister.iad.kmeans.impl;

import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.util.Point;

/**
 * {@link CPoint} is clusterable version of {@link Point} class.
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CPoint extends Point implements Clusterable {

    public CPoint(Double... loc) {
        super(loc);
    }

    @Override
    public Double[] getLocation() {
        return this.getPosition().clone();
    }
}
