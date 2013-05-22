package org.kornicameister.iad.kmeans.impl;

import com.google.common.base.Preconditions;
import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.kmeans.KCluster;
import org.kornicameister.iad.kmeans.helpers.ClusterAssignAgent;
import org.kornicameister.iad.kmeans.helpers.ClusterChecker;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class AbstractKMeansCluster implements KCluster {
    private static final int MAX_CLUSTERING = 100;
    protected int maxClustering;
    protected ClusterChecker clusterChecker;
    protected ClusterAssignAgent clusterAssignAgent;

    protected AbstractKMeansCluster(final ClusterChecker clusterChecker,
                                    final ClusterAssignAgent clusterAssignAgent) {
        this(clusterChecker, clusterAssignAgent, AbstractKMeansCluster.MAX_CLUSTERING);
    }

    protected AbstractKMeansCluster(final ClusterChecker clusterChecker,
                                    final ClusterAssignAgent clusterAssignAgent,
                                    final int maxClustering) {
        this.clusterChecker = clusterChecker;
        this.maxClustering = maxClustering;
        this.clusterAssignAgent = clusterAssignAgent;
    }

    @Override
    public Cluster[] cluster(final List<? extends Clusterable> values, final int initialClustersCount) {

        Preconditions.checkNotNull(this.clusterChecker, "ClusterChecker not provided...");
        Preconditions.checkNotNull(this.clusterAssignAgent, "ClusterAssignAgent not provided...");
        Preconditions.checkArgument(initialClustersCount >= 0, "Count of clusters to generate must be positive number");

        Cluster[] clusters = this.getInitialClusters(values, initialClustersCount);

        boolean recalculateClusters = true;

        int numIterations = 0;
        while (recalculateClusters && numIterations < this.maxClustering) {
            clusters = this.clusterAssignAgent.assignClusters(clusters, values);
            recalculateClusters = this.clusterChecker.isRecalculateClustersRequired(clusters);
            if (recalculateClusters) {
                clusters = this.getNewClusters(clusters);
                numIterations++;
            }
        }

        return clusters;
    }

    protected Cluster[] getInitialClusters(final List<? extends Clusterable> clusterables, final int count) {
        Cluster[] clusters = new Cluster[count];
        Random seed = new Random(System.nanoTime());
        Set<Integer> clusterCenters = new HashSet<>();

        for (int i = 0; i < count; i++) {
            int index = seed.nextInt(clusterables.size());
            while (clusterCenters.contains(index)) {
                index = seed.nextInt(clusterables.size());
            }
            clusterCenters.add(index);
            clusters[i] = new Cluster(clusterables.get(i).getLocation());
        }

        return clusters;
    }

    protected abstract Cluster[] getNewClusters(final Cluster[] clusters);
}
