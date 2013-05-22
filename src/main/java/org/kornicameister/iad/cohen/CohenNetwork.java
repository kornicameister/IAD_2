package org.kornicameister.iad.cohen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CohenNetwork {
    private static final String TEACHER = "org.kornicameister.iad.cohen.teacher";
    private static final String K_TO_DRAW = "org.kornicameister.iad.cohen.kToDraw";
    private static final String METRIC = "org.kornicameister.iad.cohen.distanceMetric";
    private static final String ITERATIONS = "org.kornicameister.iad.cohen.iterations";
    private static final String DELTA = "org.kornicameister.iad.cohen.delta";
    private static Properties cohenProperties;

    static {
        CohenNetwork.cohenProperties = new Properties();
        try {
            CohenNetwork.cohenProperties.load(new FileReader(new File("/cohen.properties")));
        } catch (IOException e) {
            System.err.println(e);
            cohenProperties.setProperty(CohenNetwork.TEACHER, "org.kornicameister.iad.cohen.teacher.impl.CohenAutoAssociationTeacher");
            cohenProperties.setProperty(CohenNetwork.K_TO_DRAW, "10");
            cohenProperties.setProperty(CohenNetwork.ITERATIONS, "1000");
            cohenProperties.setProperty(CohenNetwork.METRIC, null);
            cohenProperties.setProperty(CohenNetwork.DELTA, "0.25");
        }
    }

    private List<CohenPoint> input;
    private List<CohenPoint> normalizedInput;

    public static CohenPoint normalizePoint(final CohenPoint cohenPoint, final double normalizingFactory) {
        Double[] normalizedLoc = cohenPoint.getLocation().clone();
        for (int i = 0 ; i < cohenPoint.getLocation().length ; i++) {
            normalizedLoc[i] = normalizedLoc[i] / normalizingFactory;
        }
        CohenPoint point = new CohenPoint(cohenPoint.getTeacher(), normalizedLoc);
        return point;
    }

    public void setInput(List<CohenPoint> input) {
        this.input = input;
        this.normalizedInput = this.normalizeInput();
    }

    private List<CohenPoint> normalizeInput() {
        List<CohenPoint> normalizedInput = new ArrayList<>();
        double powerSum = 0.0;
        for (CohenPoint cohenPoint : this.input) {
            for (Double loc : cohenPoint.getLocation()) {
                powerSum += Math.pow(loc, 2);
            }
            normalizedInput.add(CohenNetwork.normalizePoint(cohenPoint, Math.sqrt(powerSum)));
            powerSum = 0.0;
        }
        return normalizedInput;
    }

    protected CohenPoint findWinner(final List<CohenPoint> drawnPoints) {
        CohenPoint winner = null;
        return null;
    }

    protected List<CohenPoint> drawNeurons(final int kPoints) {
        final Random seed = new Random();
        final List<CohenPoint> cohenPointList = new ArrayList<>();
        final Double delta = Double.valueOf(CohenNetwork.cohenProperties.getProperty(CohenNetwork.DELTA));

        CohenPoint toBeModified;
        Double[] location;

        for (int i = 0 ; i < kPoints ; i++) {
            toBeModified = (CohenPoint) this.input.get(seed.nextInt(this.input.size())).clone();
            location = toBeModified.getLocation();
            for (int k = 0 ; k < location.length ; k++) {
                location[k] += delta * location[k];
            }
        }

        return cohenPointList;
    }
}
