package org.kornicameister.iad.cohen.loader;

import org.kornicameister.iad.cohen.CohenPoint;

import java.util.Random;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CombiningCohenFileReader extends DefaultCohenFileReader {

    public CombiningCohenFileReader(final int endColumn, final int totalColumns) {
        super(endColumn, totalColumns);
    }

    public CombiningCohenFileReader(final int startColumn, final int endColumn, final int totalColumns) {
        super(startColumn, endColumn, totalColumns);
    }

    @Override
    protected CohenPoint readPoint(final Double... values) {
        Random seed = new Random(System.nanoTime());
        return new CohenPoint(values[seed.nextInt(values.length)],
                values[seed.nextInt(values.length)]
        );
    }
}
