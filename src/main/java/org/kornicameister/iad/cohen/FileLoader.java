package org.kornicameister.iad.cohen;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class FileLoader {
    private static final Logger LOGGER = Logger.getLogger(FileLoader.class);

    public static List<CohenPoint> load(final String fileName, final CohenFileReader fileReader, Character separator) {
        Preconditions.checkArgument(fileName != null, String.format("File %s does not exists", fileName));
        Preconditions.checkArgument(fileReader != null, "FileReader must be provided");
        List<CohenPoint> cohenPoints = new ArrayList<>();

        File file = FileLoader.resolveFile(fileName);

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
            scanner.useDelimiter(String.valueOf(separator));
            cohenPoints.addAll(fileReader.readFromFile(scanner));
        } catch (Exception fnfe) {
            LOGGER.fatal(String.format("Failure in reading from file %s", fileName), fnfe);
        }

        LOGGER.info(String.format("Read points %d", cohenPoints.size()));

        return cohenPoints;
    }

    private static File resolveFile(final String fileName) {
        File file = new File(fileName);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Loaded file %s", file));
        }
        return file;
    }

    public static List<CohenNeuron> loadAsNeurons(final String fileName, final DefaultCohenFileReader reader, final Character separator) {
        List<CohenPoint> cohenPoints = new ArrayList<>(FileLoader.load(fileName, reader, separator));
        List<CohenNeuron> neurons = new ArrayList<>();
        for (CohenPoint point : cohenPoints) {
            neurons.add(new CohenNeuron(point.getLocation()));
        }
        return neurons;
    }
}
