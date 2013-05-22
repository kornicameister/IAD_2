package org.kornicameister.iad.kmeans;

import org.kornicameister.iad.kmeans.impl.Cluster;

import java.util.List;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface KCluster {
    Cluster[] cluster(final List<? extends Clusterable> values, int clusterId);
}
