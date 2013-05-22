package org.kornicameister.iad.cohen.loader;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.kornicameister.iad.cohen.CohenFileReader;
import org.kornicameister.iad.cohen.CohenPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class DefaultCohenFileReader implements CohenFileReader {
    private static final Logger LOGGER = Logger.getLogger(DefaultCohenFileReader.class);
    protected final int totalColumns;
    protected final int endColumn;
    protected int startColumn;

    public DefaultCohenFileReader(final int endColumn, final int totalColumns) {
        this(0, endColumn, totalColumns);
    }

    public DefaultCohenFileReader(final int startColumn, final int endColumn, final int totalColumns) {
        Preconditions.checkArgument(endColumn > 0, "EndColumn must be positive");
        Preconditions.checkArgument(endColumn != startColumn, "EndColumn must not be equal to start column");
        Preconditions.checkArgument(endColumn != totalColumns, "EndColumn must not be equal to totalColumn");
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.totalColumns = totalColumns;
    }

    @Override
    public List<CohenPoint> readFromFile(final Scanner scanner) {
        List<CohenPoint> cohenPoints = new ArrayList<>();
        LOGGER.info(String.format("Reading points, startColumn=%d,endColumn=%d,totalColumn=%d",
                this.startColumn,
                this.endColumn,
                this.totalColumns)
        );
        while (scanner.hasNextLine()) {
            Double[] values = this.readValues(scanner);
            cohenPoints.add(this.readPoint(values));
        }
        scanner.close();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Reading points finished, totalPoints=%d", cohenPoints.size()));
        }
        return cohenPoints;
    }

    protected Double[] readValues(final Scanner scanner) {
        String[] line = scanner.nextLine().split(" ");
        List<Double> doubles = new ArrayList<>();
        for (int i = startColumn ; i <= endColumn ; i++) {
            doubles.add(Double.parseDouble(line[i]));
        }
        return doubles.toArray(new Double[]{});
    }

    protected CohenPoint readPoint(Double... values) {
        return new CohenPoint(values[0], values[1]);
    }
}
