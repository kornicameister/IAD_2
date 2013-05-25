package org.kornicameister.iad.cohen;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;

import java.io.File;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder
public class CohenSOMTest {
    protected static final String PATH = "output/cohen/in/%s";

    @Test
    public void testProcess() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        CohenSOM cohenAlgorithm = new CohenSOM(pointList);
        cohenAlgorithm.process();
    }

    @Test
    public void testSplitter() {
        int[][] columns = {{0, 1}, {2, 3}, {2, 4}, {3, 4}, {5, 6}, {5, 7}, {6, 7}};
        int[] neurons = {2, 3, 5, 10};
        File dir, subDir;
        for (int[] column : columns) {
            dir = new File(String.format(PATH, String.format("wejscia%d,%d", column[0], column[1])));
            for (int neuron : neurons) {
                subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
                List<CohenPoint> pointList = FileLoader.load(
                        String.format("%s/%s", subDir.getAbsolutePath(), "points.txt"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                List<CohenNeuron> neuronsList = FileLoader.loadAsNeurons(
                        String.format("%s/%s", subDir.getAbsolutePath(), "neurons.txt"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                CohenSOM cohenAlgorithm = CohenSOM.getCohenSOM(pointList, neuronsList, subDir.getAbsolutePath(), "output_%d.txt");
                cohenAlgorithm.process();
            }
        }
    }
}
