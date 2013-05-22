package org.kornicameister.iad.cohen;

import java.util.List;

public class CohenSOM extends CohenNetwork {

    public CohenSOM(final List<CohenPoint> input) {
        super(input);
    }

    @Override
    public void process() {
        final List<CohenPoint> neurons = this.drawNeurons();
    }

    @Override
    public void updatePositions() {

    }
}
