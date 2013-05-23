package org.kornicameister.iad.cohen.neighbourhood.impl;

import org.kornicameister.iad.cohen.neighbourhood.CohenNeighbourhoodFunction;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class RectangularNeighbourHoodFunction implements CohenNeighbourhoodFunction {
    @Override
    public double evaluate(final Double... args) {
        double distance = args[0];
        double factor = args[1];
        return distance < factor ? 1 : 0;
    }
}
