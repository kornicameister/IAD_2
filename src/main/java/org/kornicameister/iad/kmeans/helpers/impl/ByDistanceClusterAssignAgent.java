package org.kornicameister.iad.kmeans.helpers.impl;

import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.kmeans.helpers.ClusterAssignAgent;
import org.kornicameister.iad.kmeans.impl.Cluster;
import org.kornicameister.iad.util.DistanceUtils;

import java.util.Collections;
import java.util.List;

/**
 * {@link ByDistanceClusterAssignAgent} uses {@link DistanceUtils#getEuclideanDistance(Number[], Number[])}
 * as effective measure ratio to assign values to the nearest one located within cluster
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ByDistanceClusterAssignAgent implements ClusterAssignAgent {
    @Override
    public Cluster[] assignClusters(final Cluster[] clusters, final List<? extends Clusterable> values) {
        for (Clusterable val : values) {
            Cluster nearestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                final Double distance = DistanceUtils.getEuclideanDistance(val.getLocation(), cluster.getLocation());
                if (distance.compareTo(minDistance) < 0) {
                    nearestCluster = cluster;
                    minDistance = distance;
                }
            }
            if (nearestCluster != null) {
                nearestCluster.addClusterable(val);
            }
        }
        return clusters;
    }
}
