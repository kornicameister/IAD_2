package org.kornicameister.iad.cohen;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenAlgorithm {
    void process();
    List<CohenPoint> drawNeurons();
    void updatePositions();
}
