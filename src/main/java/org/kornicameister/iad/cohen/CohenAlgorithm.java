package org.kornicameister.iad.cohen;

import org.kornicameister.iad.util.Point;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenAlgorithm {
    void process();
    List<CohenPoint> drawNeurons();
    Point findWinner(final CohenPoint neuron);
}
