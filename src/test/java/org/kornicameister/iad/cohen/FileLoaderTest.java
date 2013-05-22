package org.kornicameister.iad.cohen;

import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.iad.cohen.loader.CombiningCohenFileReader;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class FileLoaderTest {
    @Test
    public void testDefaultCohenFileReader() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        Assert.assertNotNull("PointList is null", pointList);
        Assert.assertTrue("PointList is empty", pointList.size() > 0);
    }

    @Test
    //TODO reading triples fails strong
    public void testCombiningCohenFileReader() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new CombiningCohenFileReader(2, 5, 8), ',');
        Assert.assertNotNull("PointList is null", pointList);
        Assert.assertTrue("PointList is empty", pointList.size() > 0);
    }
}
