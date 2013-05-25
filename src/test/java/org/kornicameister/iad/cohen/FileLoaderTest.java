package org.kornicameister.iad.cohen;

import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.iad.cohen.loader.CombiningCohenFileReader;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;
import org.kornicameister.iad.util.GnuplotSaver;

import java.io.File;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class FileLoaderTest {
    protected static final String PATH = "output/cohen/in/%s";

    @Test
    public void testDefaultCohenFileReader() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        Assert.assertNotNull("PointList is null", pointList);
        Assert.assertTrue("PointList is empty", pointList.size() > 0);
    }

    @Test
    public void testCombiningCohenFileReader() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new CombiningCohenFileReader(2, 5, 8), ',');
        Assert.assertNotNull("PointList is null", pointList);
        Assert.assertTrue("PointList is empty", pointList.size() > 0);
    }

    @Test
    public void test_cohen_split_to_files() throws Exception {
        int[][] columns = {{0, 1}, {2, 3}, {2, 4}, {3, 4}, {5, 6}, {5, 7}, {6, 7}};
        int[] neurons = {2, 3, 5, 10};
        File dir, subDir;
        for (int[] column : columns) {
            dir = new File(String.format(PATH, String.format("wejscia%d,%d", column[0], column[1])));
            if (!dir.exists()) {
                dir.mkdir();
            }
            for (int neuron : neurons) {
                subDir = new File(String.format("%s/%d", dir.getAbsolutePath(), neuron));
                if (!subDir.exists()) {
                    subDir.mkdir();
                }
                List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(column[0], column[1], 8), ',');
                GnuplotSaver.saveToFile(String.format("%s/%s", subDir.getAbsolutePath(), "points.txt"), pointList);
                CohenSOM cohenAlgorithm = new CohenSOM(pointList);
                GnuplotSaver.saveToFile(String.format("%s/%s", subDir.getAbsolutePath(), "neurons.txt"), cohenAlgorithm.getNeurons());
            }
        }
    }
}
