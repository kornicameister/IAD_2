package org.kornicameister.iad.util;

import junit.framework.Assert;
import org.junit.Test;
import org.kornicameister.iad.AbstractTest;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class DistanceUtilsTest extends AbstractTest {
    @Test
    public void testGetEuclideanDistance1D() throws Exception {
        Assert.assertEquals("Bad distance", DistanceUtils.getEuclideanDistance(1.0, 2.0), 1.0);
        Assert.assertEquals("Bad distance", DistanceUtils.getEuclideanDistance(1.0, 3.0), 2.0);
        Assert.assertEquals("Bad distance", DistanceUtils.getEuclideanDistance(1.0, -6.0), 7.0);
    }

    @Test
    public void testGetEuclideanDistance2D() throws Exception {
        for (int i = 0; i < this.locations.size() - 1; i++) {
            System.out.println(DistanceUtils.getEuclideanDistance(this.locations.get(i), this.locations.get(i + 1)));
        }
    }

    @Test
    public void testGetManhattanDistance2D() throws Exception {
        for (int i = 0; i < this.locations.size() - 1; i++) {
            System.out.println(DistanceUtils.getManhattanDistance(this.locations.get(i), this.locations.get(i + 1)));
        }
    }
}
