package org.kornicameister.iad.cohen;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;
import org.kornicameister.iad.util.GnuplotSaver;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@FixMethodOrder
public class CohenSOMTest {
    private static final String PATH = "output/cohen/in/%s";
    private static final String PATH_E3 = "output/cohen/e3/%s";
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

    @Test
    public void test_E_3() throws IOException {
        final int neuron = 100;
        final int[][] columnsK = {{5, 6}};
        final int[][] columnsA = {{2, 4}, {3, 4}};
        final int[][] columns = {{2, 4}, {3, 4}, {5, 6}};


        File dir, subDir;

        for (int[] column : columnsK) {
            dir = new File(String.format(PATH_E3, String.format("wejscia%d,%d", column[0], column[1])));
            if (!dir.exists()) {
                dir.mkdir();
            }
            subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
            if (!subDir.exists()) {
                subDir.mkdir();
            }
            this.generatePointsNeurons("data/165535-2.txt", neuron, subDir, column);
        }
        for (int[] column : columnsA) {
            dir = new File(String.format(PATH_E3, String.format("wejscia%d,%d", column[0], column[1])));
            if (!dir.exists()) {
                dir.mkdir();
            }
            subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
            if (!subDir.exists()) {
                subDir.mkdir();
            }
            this.generatePointsNeurons("data/165433-2.txt", neuron, subDir, column);
        }

        for (int[] column : columns) {
            dir = new File(String.format(PATH_E3, String.format("wejscia%d,%d", column[0], column[1])));
            subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
            List<CohenPoint> pointList = FileLoader.load(
                    String.format("%s/%s", subDir.getAbsolutePath(), "e3_points.txt"),
                    new DefaultCohenFileReader(0, 1, 2), ',');
            List<CohenNeuron> neuronsList = FileLoader.loadAsNeurons(
                    String.format("%s/%s", subDir.getAbsolutePath(), "e3_neurons.txt"),
                    new DefaultCohenFileReader(0, 1, 2), ',');
            CohenSOM cohenAlgorithm = CohenSOM.getCohenSOM(pointList, neuronsList, subDir.getAbsolutePath(), "output_%d.txt");
            cohenAlgorithm.process();
        }
    }

    private void generatePointsNeurons(final String path, final int neuron, final File subDir, final int[] column) throws IOException {
        List<CohenPoint> pointList = FileLoader.load(path, new DefaultCohenFileReader(column[0], column[1], 8), ',');
        GnuplotSaver.saveToFile(String.format("%s/%s", subDir.getAbsolutePath(), "e3_points.txt"), pointList);
        CohenSOM cohenAlgorithm = new CohenSOM(pointList, neuron);
        GnuplotSaver.saveToFile(String.format("%s/%s", subDir.getAbsolutePath(), "e3_neurons.txt"), cohenAlgorithm.getNeurons());
    }
}
