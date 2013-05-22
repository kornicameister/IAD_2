package org.kornicameister.iad.cohen.abstracts;

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
    protected static final String TEACHER = "org.kornicameister.iad.cohen.teacher";
    protected static final String K_TO_DRAW = "org.kornicameister.iad.cohen.kToDraw";
    protected static final String METRIC = "org.kornicameister.iad.cohen.distanceMetric";
    protected static final String ITERATIONS = "org.kornicameister.iad.cohen.iterations";
    protected static final String DELTA = "org.kornicameister.iad.cohen.delta";
    private static Properties cohenProperties;

    public static String getProperty(final String key) {
        return cohenProperties.getProperty(key, Defaults.defaultMap.get(key));
    }

    private static class Defaults {
        static final String DEFAULT_ASSOC_TEACHER = "org.kornicameister.iad.cohen.teacher.impl.CohenAutoAssociationTeacher";
        static final String DEFAULT_K_TO_DRAWN = "10";
        static final String DEFAULT_ITERATIONS = "1000";
        static final String DEFAULT_METRIC = "null";
        static final String DEFAULT_DELTA = "0.25";
        static Map<String, String> defaultMap;

        static {
            defaultMap = new HashMap<>();
            defaultMap.put(_CohenNetwork.TEACHER, Defaults.DEFAULT_ASSOC_TEACHER);
            defaultMap.put(_CohenNetwork.DELTA, Defaults.DEFAULT_DELTA);
            defaultMap.put(_CohenNetwork.ITERATIONS, Defaults.DEFAULT_ITERATIONS);
            defaultMap.put(_CohenNetwork.K_TO_DRAW, Defaults.DEFAULT_K_TO_DRAWN);
            defaultMap.put(_CohenNetwork.METRIC, Defaults.DEFAULT_METRIC);
        }
    }

    static {
        _CohenNetwork.cohenProperties = new Properties();
        try {
            _CohenNetwork.cohenProperties.load(new FileReader(new File("/cohen.properties")));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
