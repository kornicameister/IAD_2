package org.kornicameister.iad.cohen;

import org.apache.log4j.Logger;
import org.kornicameister.iad.cohen.abstracts._CohenNetwork;
import org.kornicameister.iad.cohen.distance.CohenDistance;
import org.kornicameister.iad.cohen.neighbourhood.CohenNeighbourhoodFunction;
import org.kornicameister.iad.cohen.util.CohenUtilities;
import org.kornicameister.iad.util.Point;
import org.kornicameister.iad.util.RandomDouble;

import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

abstract public class CohenNetwork
        extends _CohenNetwork
        implements CohenAlgorithm {
    private static final Logger LOGGER = Logger.getLogger(CohenNetwork.class);
    protected final CohenNeighbourhoodFunction neighbourhoodFunction;
    protected final CohenDistance cohenDistance;
    protected Integer enforcedKToDraw;
    protected List<CohenPoint> input;
    protected List<CohenNeuron> neurons;
    protected double radius = 0.0;
    protected double maxX = Double.MIN_VALUE,
            maxY = Double.MIN_VALUE,
            minX = Double.MAX_VALUE,
            minY = Double.MAX_VALUE;

    public CohenNetwork(final List<CohenPoint> input) {
        super(false);
        this.setInput(input);
        this.neighbourhoodFunction = (CohenNeighbourhoodFunction) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_FUNCTION));
        this.cohenDistance = (CohenDistance) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.METRIC));
        this.enforcedKToDraw = 0;
        this.analyzeBoundaries();
        this.updateNeighbourhoodRadius();
        this.neurons = this.drawNeurons2();
    }

    public CohenNetwork(final List<CohenPoint> input, final boolean skipAuto) {
        super(false);
        this.setInput(input);
        this.neighbourhoodFunction = (CohenNeighbourhoodFunction) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_FUNCTION));
        this.cohenDistance = (CohenDistance) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.METRIC));
        this.enforcedKToDraw = 0;
        this.analyzeBoundaries();
        this.updateNeighbourhoodRadius();
        if (!skipAuto) {
            this.neurons = this.drawNeurons2();
        }
    }

    public CohenNetwork(final List<CohenPoint> input, final boolean skipAuto, final boolean readProp) {
        super(readProp);
        this.setInput(input);
        this.neighbourhoodFunction = (CohenNeighbourhoodFunction) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_FUNCTION));
        this.cohenDistance = (CohenDistance) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.METRIC));
        this.analyzeBoundaries();
        this.updateNeighbourhoodRadius();
        if (!skipAuto) {
            this.neurons = this.drawNeurons2();
        }
    }

    public CohenNetwork(final List<CohenPoint> input, final boolean skipAuto, final boolean readProp, final int neuron) {
        super(readProp);
        this.setInput(input);
        this.neighbourhoodFunction = (CohenNeighbourhoodFunction) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_FUNCTION));
        this.cohenDistance = (CohenDistance) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.METRIC));
        this.analyzeBoundaries();
        this.updateNeighbourhoodRadius();
        this.enforcedKToDraw = neuron;
        if (!skipAuto) {
            this.neurons = this.drawNeurons2();
        }
    }

    private void updateNeighbourhoodRadius() {
        double radius = Double.parseDouble(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_RADIUS));
        double radius2 = Math.abs(this.maxX - this.minX) + Math.abs(this.minY - this.maxY);
        this.radius = radius > radius2 ? radius : radius2;
    }

    public void setInput(List<CohenPoint> input) {
        final Boolean normalize = Boolean.valueOf(CohenNetwork.getProperty(CohenNetwork.NORMALIZE));
        LOGGER.info(String.format("Points normalization = %s", normalize));
        if (normalize) {
            this.input = CohenUtilities.normalizePoints(input);
        } else {
            this.input = input;
        }
    }

    @Override
    public List<CohenNeuron> drawNeurons() {
        final Random seed = new Random();
        final List<CohenNeuron> neuronList = new ArrayList<>();
        final Double delta = Double.valueOf(CohenNetwork.getProperty(CohenNetwork.DELTA));
        final int neurons = this.enforcedKToDraw == 0 ?
                Integer.valueOf(CohenNetwork.getProperty(CohenNetwork.K_TO_DRAW)) :
                this.enforcedKToDraw;

        CohenPoint toBeModified;
        Double[] location;

        for (int i = 0 ; i < neurons ; i++) {
            toBeModified = (CohenPoint) this.input.get(seed.nextInt(this.input.size())).clone();
            location = toBeModified.getLocation();
            for (int k = 0 ; k < location.length ; k++) {
                location[k] += delta * location[k];
            }
            neuronList.add(new CohenNeuron(location));
        }

        return neuronList;
    }

    public List<CohenNeuron> drawNeurons2() {
        final List<CohenNeuron> neuronList = new ArrayList<>();
        final int neurons = this.enforcedKToDraw == 0 ?
                Integer.valueOf(CohenNetwork.getProperty(CohenNetwork.K_TO_DRAW)) :
                this.enforcedKToDraw;

        for (int i = 0 ; i < neurons ; i++) {
            neuronList.add(new CohenNeuron(
                    RandomDouble.nextDouble(minX, maxX),
                    RandomDouble.nextDouble(minY, maxY)
            ));
        }

        return neuronList;
    }

    private void analyzeBoundaries() {
        for (CohenPoint point : this.input) {
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
            if (point.getX() < minX) {
                minX = point.getX();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
            if (point.getY() < minY) {
                minY = point.getY();
            }
        }
    }

    @Override
    public CohenNeuron findWinner(final CohenPoint randomPoint, final List<CohenNeuron> neurons) {
        final int thresholdRead = Integer.parseInt(CohenSOM.getProperty(CohenSOM.NEURON_THRESHOLD));
        final int threshold = thresholdRead > this.neurons.size() ? this.neurons.size() : thresholdRead;
        final List<CohenNeuron> activeNeurons = new ArrayList<>();

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Threshold = %d", threshold));
        }

        // extract active
        for (CohenNeuron neuron : neurons) {
            if (!neuron.isActive(threshold)) {
                neuron.deactivate();
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(String.format("Deactivating neuron %d, activeStatus=%s", neuron.getId(), false));
                }
            }
        }
        for (CohenNeuron neuron : neurons) {
            if (neuron.isActive(threshold)) {
                activeNeurons.add(neuron);
            }
        }
        // extract active

        // sort against distance
        Collections.sort(activeNeurons, new Comparator<CohenNeuron>() {
            @Override
            public int compare(final CohenNeuron o1, final CohenNeuron o2) {
                Double o1D = cohenDistance.distance(o1, randomPoint);
                Double o2D = cohenDistance.distance(o2, randomPoint);
                return o1D.compareTo(o2D);
            }
        });
        // sort against distance


        final CohenNeuron winner = activeNeurons.get(0);
        winner.activate(threshold);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("Activating neuron ID=[%d], activeStatus=%s", winner.getId(), true));
        }

        return winner;
    }

    protected List<CohenNeuron> findNeighbours(final CohenNeuron winningNeuron) {
        final double radiusDec = Double.parseDouble(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_RADIUS_DECR));
        final List<CohenNeuron> neighbours = new ArrayList<>();
        List<CohenNeuron> copy = new ArrayList<>(this.neurons);
        Collections.sort(copy, new Comparator<Point>() {
            @Override
            public int compare(final Point o1, final Point o2) {
                Double o1D = cohenDistance.distance((CohenPoint) o1, winningNeuron);
                Double o2D = cohenDistance.distance((CohenPoint) o2, winningNeuron);
                return o1D.compareTo(o2D);
            }
        });
        for (CohenNeuron point : copy) {
            if (this.cohenDistance.distance(point, winningNeuron) <= radius && !winningNeuron.equals(point)) {
                neighbours.add(point);
            }
        }
        if (radius - radiusDec > 0) {
            radius -= radiusDec;
        }
        return neighbours;
    }

    protected abstract void updatePositions(final CohenPoint closestPoint, final CohenPoint winningNeuron, final List<CohenNeuron> neighbours);

    protected abstract boolean checkAgainstQuantumError(final CohenNeuron winningNeuron);

    public List<CohenNeuron> getNeurons() {
        return neurons;
    }
}
