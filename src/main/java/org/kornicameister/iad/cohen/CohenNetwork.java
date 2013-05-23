package org.kornicameister.iad.cohen;

import org.kornicameister.iad.cohen.abstracts._CohenNetwork;
import org.kornicameister.iad.cohen.distance.CohenDistance;
import org.kornicameister.iad.cohen.neighbourhood.CohenNeighbourhoodFunction;
import org.kornicameister.iad.cohen.util.CohenUtilities;
import org.kornicameister.iad.util.Point;

import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

abstract public class CohenNetwork
        extends _CohenNetwork
        implements CohenAlgorithm {
    protected final CohenNeighbourhoodFunction neighbourhoodFunction;
    protected final CohenDistance cohenDistance;
    protected List<? extends Point> input;
    protected List<? extends Point> normalizedInput;


    public CohenNetwork(final List<CohenPoint> input) {
        this.setInput(input);
        this.neighbourhoodFunction = (CohenNeighbourhoodFunction) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_FUNCTION));
        this.cohenDistance = (CohenDistance) this.resolveObject(CohenNetwork.getProperty(CohenNetwork.METRIC));
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
            cohenPointList.add(new CohenPoint(location));
        }

        return cohenPointList;
    }

    @Override
    public CohenPoint findWinner(final CohenPoint neuron) {
        List<Point> cohenPoints = new ArrayList<>(this.input);
        Collections.sort(cohenPoints, new Comparator<Point>() {
            @Override
            public int compare(final Point o1, final Point o2) {
                Double o1D = CohenUtilities.DistanceMetrics.getEuclideanDistance((CohenPoint) o1, neuron);
                Double o2D = CohenUtilities.DistanceMetrics.getEuclideanDistance((CohenPoint) o2, neuron);
                return o1D.compareTo(o2D);
            }
        });
        return (CohenPoint) cohenPoints.get(0);
    }

    protected List<CohenPoint> findNeighbours(final CohenPoint winner) {
        final double radius = Double.parseDouble(CohenNetwork.getProperty(CohenNetwork.NEIGHBOUR_RADIUS));
        final List<CohenPoint> neighbours = new ArrayList<>();
        List<Point> cohenPoints = new ArrayList<>(this.input);
        Collections.sort(cohenPoints, new Comparator<Point>() {
            @Override
            public int compare(final Point o1, final Point o2) {
                Double o1D = cohenDistance.distance((CohenPoint) o1, winner);
                Double o2D = cohenDistance.distance((CohenPoint) o2, winner);
                return o1D.compareTo(o2D);
            }
        });
        for (Point point : cohenPoints) {
            if (cohenDistance.distance((CohenPoint) point, winner) <= radius && !winner.equals(point)) {
                neighbours.add((CohenPoint) point);
            }
        }
        return neighbours;
    }

    public abstract void updatePositions(final CohenPoint neuron, final CohenPoint winner, final List<CohenPoint> neighbours);
}
