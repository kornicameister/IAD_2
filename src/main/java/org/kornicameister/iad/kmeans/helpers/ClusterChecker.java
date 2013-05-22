package org.kornicameister.iad.kmeans.helpers;

import org.kornicameister.iad.kmeans.impl.Cluster;

/**
 * Consider {@link ClusterChecker} as the useful utility that
 * can decide whether or not another clustering is required.
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ClusterChecker {

    boolean isRecalculateClustersRequired(Cluster[] clusters);
}
