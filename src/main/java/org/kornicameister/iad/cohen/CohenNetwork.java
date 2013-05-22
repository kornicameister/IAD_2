package org.kornicameister.iad.cohen;

import org.kornicameister.iad.cohen.abstracts._CohenNetwork;
import org.kornicameister.iad.cohen.util.CohenUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

abstract public class CohenNetwork
        extends _CohenNetwork
        implements CohenAlgorithm {
    /**
     * CohenAlgorithm implementation, strategy pattern
     */
    protected final CohenAlgorithm cohenAlgorithm;
    protected List<CohenPoint> input;
    protected List<CohenPoint> normalizedInput;

    public CohenNetwork(final CohenAlgorithm cohenAlgorithm) {
        this.cohenAlgorithm = cohenAlgorithm;
    }

    public CohenNetwork(final List<CohenPoint> input, final CohenAlgorithm cohenAlgorithm) {
        this(cohenAlgorithm);
        this.setInput(input);
    }

    public void setInput(List<CohenPoint> input) {
        this.input = input;
        this.normalizedInput = CohenUtilities.normalizePoints(this.input);
    }

    @Override
    public List<CohenPoint> drawNeurons() {
        final Random seed = new Random();
        final List<CohenPoint> cohenPointList = new ArrayList<>();
        final Double delta = Double.valueOf(CohenNetwork.getProperty(CohenNetwork.DELTA));
        final int neurons = Integer.valueOf(CohenNetwork.getProperty(CohenNetwork.K_TO_DRAW));

        CohenPoint toBeModified;
        Double[] location;

        for (int i = 0 ; i < neurons ; i++) {
            toBeModified = (CohenPoint) this.input.get(seed.nextInt(this.input.size())).clone();
            location = toBeModified.getLocation();
            for (int k = 0 ; k < location.length ; k++) {
                location[k] += delta * location[k];
            }
        }

        return cohenPointList;
    }
}
