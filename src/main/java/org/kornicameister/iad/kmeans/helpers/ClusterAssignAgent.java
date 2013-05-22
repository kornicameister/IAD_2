package org.kornicameister.iad.kmeans.helpers;


import org.kornicameister.iad.kmeans.Clusterable;
import org.kornicameister.iad.kmeans.impl.Cluster;

import java.util.List;

/**
 * Interface that implementation allows to assign cluster by any desired implementation
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ClusterAssignAgent {
    Cluster[] assignClusters(Cluster[] clusters, List<? extends Clusterable> values);
}
