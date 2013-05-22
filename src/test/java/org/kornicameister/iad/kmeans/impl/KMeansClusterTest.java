package org.kornicameister.iad.kmeans.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.kmeans.helpers.impl.ByDistanceClusterAssignAgent;
import org.kornicameister.iad.kmeans.helpers.impl.EpsilonClusterChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.kornicameister.iad.util.RandomDouble.nextDouble;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class KMeansClusterTest {
    private List<Clusterable> points;

    @Before
    public void setUp() throws Exception {
        final int numPoints = 500;
        List<Clusterable> points = new ArrayList<>(numPoints);

        for (int i = 1; i <= numPoints; i++) {
            double x = nextDouble(-1, (double) i);
            double y = nextDouble(-1, (double) i);
            points.add(new CPoint(x, y));
        }

        this.points = points;
    }

    @Test
    public void testGetNewClusters() throws Exception {
        final int initialClustersCount = 5;
        final int maxClustering = 100;
        KMeansCluster meansCluster = new KMeansCluster(
                new EpsilonClusterChecker(0.0005d),
                new ByDistanceClusterAssignAgent(),
                maxClustering
        );
        Cluster[] clusters = meansCluster.cluster(this.points, initialClustersCount);
        Assert.assertTrue("No clusters returned", clusters.length >= 1);
        for (Cluster cluster : clusters) {
            for (Clusterable clusterable : cluster.getClusterableList()) {
                System.out.println(String.format("Cluster id=[%d], location=%s", cluster.getId(), Arrays.toString(clusterable.getLocation())));
            }
            System.out.println();
        }
    }
}
