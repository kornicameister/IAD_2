package org.kornicameister.iad.kmeans.impl;

import org.kornicameister.iad.kmeans.helpers.ClusterAssignAgent;
import org.kornicameister.iad.kmeans.helpers.ClusterChecker;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class KMeansCluster extends AbstractKMeansCluster {

    public KMeansCluster(final ClusterChecker clusterChecker,
                         final ClusterAssignAgent clusterAssignAgent) {
        super(clusterChecker, clusterAssignAgent);
    }

    public KMeansCluster(final ClusterChecker clusterChecker,
                         final ClusterAssignAgent clusterAssignAgent,
                         final int maxClustering) {
        super(clusterChecker, clusterAssignAgent, maxClustering);
    }

    @Override
    protected Cluster[] getNewClusters(final Cluster[] clusters) {
        for (int i = 0; i < clusters.length; i++) {
            if (clusters[i].getClusterableList().size() > 0)
                clusters[i] = new Cluster(clusters[i].getClusterMean());
        }
        return clusters;
    }
}
