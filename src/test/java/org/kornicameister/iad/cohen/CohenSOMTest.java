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
    private static final String PATH = "output/cohen/in/%s";
    private static final String A_K_PATH = "output/cohen/kmeans/%s";
    private static final String A_G_PATH = "output/cohen/gaz/%s";
    private int[][] columns = {{0, 1}, {2, 3}, {2, 4}, {3, 4}, {5, 6}, {5, 7}, {6, 7}};
    private int[] neurons = {2, 3, 5, 10};

    @Test
    public void testProcess() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        CohenSOM cohenAlgorithm = new CohenSOM(pointList);
        cohenAlgorithm.process();
    }

    @Test
    public void testSplitter() {
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

    @Test
    public void test_KMeans_A() {
        File dir, subDir;
        for (int[] column : columns) {
            dir = new File(String.format(A_K_PATH, String.format("wejscia%d,%d", column[0], column[1])));
            for (int neuron : neurons) {
                subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
                List<CohenPoint> pointList = FileLoader.load(
                        String.format("%s/%s", subDir.getAbsolutePath(), "points.dat"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                List<CohenNeuron> neuronsList = FileLoader.loadAsNeurons(
                        String.format("%s/%s", subDir.getAbsolutePath(), "centroids.dat"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                CohenSOM cohenAlgorithm = CohenSOM.getCohenSOM(pointList, neuronsList, subDir.getAbsolutePath(), "output_%d.txt");
                cohenAlgorithm.process();
            }
        }
    }

    @Test
    public void test_Gas_A() {
        File dir, subDir;
        for (int[] column : columns) {
            dir = new File(String.format(A_G_PATH, String.format("wejscia%d,%d", column[0], column[1])));
            for (int neuron : neurons) {
                subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
                List<CohenPoint> pointList = FileLoader.load(
                        String.format("%s/%s", subDir.getAbsolutePath(), "points.dat"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                List<CohenNeuron> neuronsList = FileLoader.loadAsNeurons(
                        String.format("%s/%s", subDir.getAbsolutePath(), "neurons.dat"),
                        new DefaultCohenFileReader(0, 1, 2), ',');
                CohenSOM cohenAlgorithm = CohenSOM.getCohenSOM(pointList, neuronsList, subDir.getAbsolutePath(), "output_%d.txt");
                cohenAlgorithm.process();
            }
        }
    }
}
