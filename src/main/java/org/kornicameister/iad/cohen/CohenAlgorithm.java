package org.kornicameister.iad.cohen;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenAlgorithm {
    void process();

    List<CohenNeuron> drawNeurons();

    CohenNeuron findWinner(final CohenPoint neuron, final List<CohenNeuron> neurons);
}
