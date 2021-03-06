package org.kornicameister.iad.cohen;

import org.apache.log4j.Logger;
import org.kornicameister.iad.util.GnuplotSaver;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CohenSOM extends CohenNetwork {
    private static final Logger LOGGER = Logger.getLogger(CohenSOM.class);
    private String outputPrefix = "output/cohen_som/";
    private String fileNamePattern = "it_%d.data";

    public CohenSOM(final List<CohenPoint> input) {
        super(input);
    }

    public CohenSOM(final List<CohenPoint> cohenPoints, final boolean skipAuto) {
        super(cohenPoints, skipAuto);
    }

    public CohenSOM(final List<CohenPoint> input, final boolean skipAuto, final boolean readProp) {
        super(input, skipAuto, readProp);
    }

    public CohenSOM(final List<CohenPoint> pointList, final int neuron) {
        super(pointList, false, false, neuron);
    }

    public static CohenSOM getCohenSOM(final List<CohenPoint> input,
                                       final List<CohenNeuron> neurons,
                                       final String outputPrefix,
                                       final String fileNamePattern) {
        CohenSOM cohenSOM = new CohenSOM(input, true, true);
        cohenSOM.input = input;
        cohenSOM.neurons = neurons;
        cohenSOM.outputPrefix = outputPrefix;
        if (!cohenSOM.outputPrefix.endsWith("/")) {
            cohenSOM.outputPrefix += "/";
        }
        cohenSOM.fileNamePattern = fileNamePattern;
        return cohenSOM;
    }

    @Override
    public void updatePositions(final CohenPoint closestPoint,
                                final CohenPoint winningNeuron,
                                final List<CohenNeuron> neighbours) {
        final double learningFactor = Double.parseDouble(CohenSOM.getProperty(CohenSOM.LEARNING_FACTOR));

        int posLength = winningNeuron.getDimensions();
        Double dist;

        for (int i = 0 ; i < posLength ; i++) {
            dist = this.cohenDistance.distance(closestPoint, winningNeuron);
            winningNeuron.setN(
                    winningNeuron.getN(i) + this.neighbourhoodFunction.evaluate(dist, learningFactor) * (closestPoint.getN(i) - winningNeuron.getN(i)),
                    i);
        }
        for (CohenNeuron neuron : neighbours) {
            for (int i = 0 ; i < posLength ; i++) {
                dist = this.cohenDistance.distance(neuron, neuron);
                neuron.setN(
                        neuron.getN(i) + this.neighbourhoodFunction.evaluate(dist, learningFactor) * (closestPoint.getN(i) - neuron.getN(i)),
                        i);
            }
        }
    }

    @Override
    public void process() {
        final Random seed = new Random(System.nanoTime());
        final int iterations = Integer.parseInt(CohenSOM.getProperty(CohenSOM.ITERATIONS));
        final int size = this.input.size();

        int it = 0;
        boolean onceAgain;
        CohenPoint randomPoint;
        CohenNeuron winningNeuron;
        List<CohenNeuron> neighbours;

        do {
            randomPoint = this.input.get(seed.nextInt(size));
            winningNeuron = this.findWinner(randomPoint, this.neurons);
            neighbours = this.findNeighbours(winningNeuron);
            this.updatePositions(randomPoint, winningNeuron, neighbours);
            onceAgain = this.checkAgainstQuantumError(winningNeuron);

            LOGGER.info(String.format("It[%d]/WN[%d] ==> with %d neighbours", it, winningNeuron.getId(), neighbours.size()));

        } while (onceAgain && (it++) <= iterations);
        for (CohenNeuron neuron : this.neurons) {
            LOGGER.info(String.format("Neuron [id=%d] has won %d times", neuron.getId(), neuron.getWonCount()));
        }
        try {
            GnuplotSaver.saveToFile(String.format(this.outputPrefix + this.fileNamePattern, it), this.neurons);
        } catch (IOException e) {
            LOGGER.warn("Failed to save points to file", e);
        }
    }

    @Override
    protected boolean checkAgainstQuantumError(final CohenNeuron winningNeuron) {
        final Double delta = Double.parseDouble(CohenSOM.getProperty(CohenSOM.NETWORK_THRESHOLD));

        double error = 0.0;

        for (CohenPoint point : this.input) {
            error += this.cohenDistance.distance(point, winningNeuron);
        }

        error /= this.input.size();
        LOGGER.info(String.format("Error of network = %f", error));

        final boolean again = error >= delta;
        if (again) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Repeating iteration, last error[%f] >= networkThreshold[%f]", error, delta));
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Repeating over, last error[%f] < networkThreshold[%f]", error, delta));
            }
        }
        return again;
    }
}
