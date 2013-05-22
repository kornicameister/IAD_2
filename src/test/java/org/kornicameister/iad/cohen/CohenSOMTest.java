package org.kornicameister.iad.cohen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CohenSOMTest {

    private CohenSOM cohenAlgorithm;

    @Before
    public void setUp() throws Exception {
        this.cohenAlgorithm = new CohenSOM(new ArrayList<CohenPoint>());
    }

    @Test
    public void testProcess() throws Exception {

    }

    @Test
    public void testSetInput() throws Exception {

    }

    @Test
    public void testDrawNeurons() throws Exception {

    }

    @Test
    public void testGetProperty() throws Exception {
        Assert.assertNotNull(CohenNetwork.getProperty("org.kornicameister.iad.cohen.teacher"));
    }
}
