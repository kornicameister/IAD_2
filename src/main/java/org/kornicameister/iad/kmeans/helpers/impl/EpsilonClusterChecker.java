package org.kornicameister.iad.kmeans.helpers.impl;

import org.kornicameister.iad.kmeans.helpers.ClusterChecker;
import org.kornicameister.iad.kmeans.impl.Cluster;
import org.kornicameister.iad.util.DistanceUtils;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class EpsilonClusterChecker implements ClusterChecker {
    private final double epsilon;

    public EpsilonClusterChecker(final double epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    public boolean isRecalculateClustersRequired(final Cluster[] clusters) {
        for (Cluster cluster : clusters) {
            if (cluster.getClusterableList().size() > 0) {
                double distanceChange = DistanceUtils.getEuclideanDistance(cluster.getClusterMean(), cluster.getLocation());
                if (distanceChange > this.epsilon) {
                    return true;
                }
            }
        }
        return false;
    }
}
