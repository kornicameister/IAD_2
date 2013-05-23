package org.kornicameister.iad.cohen.neighbourhood.impl;

import org.kornicameister.iad.cohen.neighbourhood.CohenNeighbourhoodFunction;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class GaussNeighbourhoodFunction implements CohenNeighbourhoodFunction {
    @Override
    public double evaluate(final Double... args) {
        double distance = args[0];
        double factor = args[1];
        return Math.exp(-(Math.pow(distance, 2) / 2 * Math.pow(factor, 2)));
    }
}
