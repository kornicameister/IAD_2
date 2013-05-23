package org.kornicameister.iad.cohen;

import org.apache.log4j.Logger;
import org.kornicameister.iad.util.GnuplotSaver;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CohenSOM extends CohenNetwork {
    private static final Logger LOGGER = Logger.getLogger(CohenSOM.class);

    public CohenSOM(final List<CohenPoint> input) {
        super(input);
    }

    @Override
    public void updatePositions(final CohenPoint neuron, final CohenPoint winner, final List<CohenPoint> neighbours) {
        final double learningFactor = Double.parseDouble(CohenSOM.getProperty(CohenSOM.LEARNING_FACTOR));

        int posLength = winner.getDimensions();
        double dist;

        for (int i = 0 ; i < posLength ; i++) {
            dist = this.cohenDistance.distance(neuron, winner);
            winner.setN(winner.getN(i) + this.neighbourhoodFunction.evaluate(dist, learningFactor) * (neuron.getN(i) - winner.getN(i)), i);
        }
        for (CohenPoint point : neighbours) {
            for (int i = 0 ; i < posLength ; i++) {
                dist = this.cohenDistance.distance(neuron, point);
                point.setN(point.getN(i) + this.neighbourhoodFunction.evaluate(dist, learningFactor) * (winner.getN(i) - point.getN(i)), i);
            }
        }
    }

    @Override
    public void process() {
        final List<CohenPoint> neurons = this.drawNeurons();
        final Random seed = new Random(System.nanoTime());
        final int iterations = Integer.parseInt(CohenSOM.getProperty(CohenSOM.ITERATIONS));

        int it = 0;
        boolean onceAgain = true;
        CohenPoint neuron, winner;
        List<CohenPoint> neighbours;

        do {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("Next iteration [%d] coming up...", it));
            }
            try {
                GnuplotSaver.saveToFile(String.format("output/cohen_som/it_%d", it), this.input);
            } catch (IOException e) {
                LOGGER.warn("Failed to save to file", e);
            }
            neuron = neurons.get(seed.nextInt(neurons.size()));
            winner = this.findWinner(neuron);
            neighbours = this.findNeighbours(winner);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.info(String.format("Found winner %s for neuron %s with %d neighbours", winner, neuron, neighbours.size()));
            }

            this.updatePositions(neuron, winner, neighbours);
            onceAgain = true;
        } while (onceAgain && iterations > (it++));
    }
}
