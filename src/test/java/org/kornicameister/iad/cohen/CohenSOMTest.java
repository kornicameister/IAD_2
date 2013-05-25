package org.kornicameister.iad.cohen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CohenSOMTest {

    private CohenSOM cohenAlgorithm;

    @Before
    public void setUp() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        this.cohenAlgorithm = new CohenSOM(pointList);
    }

    @Test
    public void testProcess() throws Exception {
        this.cohenAlgorithm.process();
    }

    @Test
    public void testSetInput() throws Exception {

    }

    @Test
    public void testDrawNeurons() throws Exception {
        List<CohenNeuron> pointList = this.cohenAlgorithm.drawNeurons();
        Assert.assertNotNull("Null drawnNeurons", pointList);
        Assert.assertTrue("Empty drawnNeurons", pointList.size() > 0);
    }

    @Test
    public void testGetProperty() throws Exception {
        Assert.assertNotNull(CohenNetwork.getProperty("org.kornicameister.iad.cohen.neighbourFunction"));
    }
}
