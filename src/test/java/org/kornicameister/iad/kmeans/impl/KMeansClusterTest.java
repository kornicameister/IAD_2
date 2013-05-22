package org.kornicameister.iad.kmeans.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.iad.cohen.CohenPoint;
import org.kornicameister.iad.cohen.FileLoader;
import org.kornicameister.iad.cohen.loader.DefaultCohenFileReader;
import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.kmeans.helpers.impl.ByDistanceClusterAssignAgent;
import org.kornicameister.iad.kmeans.helpers.impl.EpsilonClusterChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class KMeansClusterTest {
    private List<Clusterable> points;

    @Before
    public void setUp() throws Exception {
        List<CohenPoint> pointList = FileLoader.load("data/165535-2.txt", new DefaultCohenFileReader(0, 1, 8), ' ');
        List<Clusterable> points = new ArrayList<>(pointList.size());

        for(CohenPoint point : pointList){
            points.add(point);
        }

        this.points = points;
    }

    @Test
    public void testGetNewClusters() throws Exception {
        final int initialClustersCount = 5;
        final int maxClustering = 20;
        KMeansCluster meansCluster = new KMeansCluster(
                new EpsilonClusterChecker(0.0005d),
                new ByDistanceClusterAssignAgent(),
                maxClustering
        );
        Cluster[] clusters = meansCluster.cluster(this.points, initialClustersCount);
        Assert.assertTrue("No clusters returned", clusters.length >= 1);
    }
}
