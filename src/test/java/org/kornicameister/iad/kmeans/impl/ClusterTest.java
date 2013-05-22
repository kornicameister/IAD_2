package org.kornicameister.iad.kmeans.impl;

import org.junit.Test;
import org.kornicameister.iad.AbstractTest;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ClusterTest extends AbstractTest {

    @Test
    public void testGetMean() throws Exception {
        Cluster cluster = new Cluster(this.locations.get(0));
        for (int i = 1; i < this.locations.size(); i++) {
            cluster.addClusterable(new Cluster(this.locations.get(i)));
            cluster.getClusterMean();
        }
    }
}
