package org.kornicameister.iad.cohen.abstracts;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public abstract class _CohenNetwork {
    protected static final String K_TO_DRAW = "org.kornicameister.iad.cohen.kToDraw";
    protected static final String METRIC = "org.kornicameister.iad.cohen.distanceMetric";
    protected static final String ITERATIONS = "org.kornicameister.iad.cohen.iterations";
    protected static final String DELTA = "org.kornicameister.iad.cohen.delta";
    protected static final String NEIGHBOUR_RADIUS = "org.kornicameister.iad.cohen.neighbourRadius";
    protected static final String NEIGHBOUR_FUNCTION = "org.kornicameister.iad.cohen.neighbourFunction";
    protected static final String LEARNING_FACTOR = "org.kornicameister.iad.cohen.learningFactor";
    private static final String COHEN_PROPERTIES = "src/main/resources/cohen.properties";
    private static final Logger LOGGER = Logger.getLogger(_CohenNetwork.class);
    private static Properties cohenProperties;

    public static String getProperty(final String key) {
        return cohenProperties.getProperty(key, Defaults.defaultMap.get(key));
    }

    protected Object resolveObject(final String name) {
        Object object = null;
        try {
            Class clazz = Class.forName(name);
            object = clazz.newInstance();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Loaded class %s", object));
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn(String.format("Failed to load class %s", object), e);
        } catch (InstantiationException e) {
            LOGGER.warn(String.format("Failed to create class's object %s", object), e);
        } catch (IllegalAccessException e) {
            LOGGER.warn(String.format("Failed to access to class %s", object), e);
        }
        return object;
    }

    private static class Defaults {
        static final String DEFAULT_NEIGHBOUR_FUNCTION = "org.kornicameister.iad.cohen.neighbourhood.impl.GaussNeighbourhoodFunction";
        static final String DEFAULT_K_TO_DRAWN = "10";
        static final String DEFAULT_ITERATIONS = "1000";
        static final String DEFAULT_METRIC = "org.kornicameister.iad.cohen.distance.impl.EuclideanCohenDistance";
        static final String DEFAULT_DELTA = "0.25";
        static final String DEFAULT_NEIGHBOUR_RADIUS = "1";
        static final String DEFAULT_LEARNING_FACTOR = "0.7";
        static Map<String, String> defaultMap;

        static {
            defaultMap = new HashMap<>();
            defaultMap.put(_CohenNetwork.DELTA, Defaults.DEFAULT_DELTA);
            defaultMap.put(_CohenNetwork.ITERATIONS, Defaults.DEFAULT_ITERATIONS);
            defaultMap.put(_CohenNetwork.K_TO_DRAW, Defaults.DEFAULT_K_TO_DRAWN);
            defaultMap.put(_CohenNetwork.METRIC, Defaults.DEFAULT_METRIC);
            defaultMap.put(_CohenNetwork.NEIGHBOUR_RADIUS, Defaults.DEFAULT_NEIGHBOUR_RADIUS);
            defaultMap.put(_CohenNetwork.NEIGHBOUR_FUNCTION, Defaults.DEFAULT_NEIGHBOUR_FUNCTION);
            defaultMap.put(_CohenNetwork.LEARNING_FACTOR, Defaults.DEFAULT_LEARNING_FACTOR);
        }
    }

    static {
        _CohenNetwork.cohenProperties = new Properties();
        try {
            LOGGER.debug(String.format("Loading properties %s from %s", _CohenNetwork.cohenProperties.values(), COHEN_PROPERTIES));
            _CohenNetwork.cohenProperties.load(new FileReader(new File(COHEN_PROPERTIES)));
        } catch (IOException exception) {
            LOGGER.warn("Could not load properties, defaults will be used", exception);
        }
    }

}
