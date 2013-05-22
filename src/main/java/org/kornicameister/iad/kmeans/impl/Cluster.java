package org.kornicameister.iad.kmeans.impl;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.kornicameister.iad.kmeans.Clusterable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Cluster implements Clusterable {
    private static int ID = 0;
    private Double[] location;
    private Double[] currentMeanLocation;
    private List<Clusterable> clusterableList;
    private Integer id;
    private Double[] lastMeanValue;

    public Cluster(final Cluster cluster) {
        this(cluster.location);
    }

    public Cluster(final Double[] location) {
        Preconditions.checkArgument(location != null, "Location must not be null");
        this.location = location;
        this.clusterableList = new LinkedList<>();
        this.id = (++Cluster.ID);
    }

    public boolean addClusterable(@NotNull final Clusterable clusterable) {
        if (this.currentMeanLocation == null) {
            this.currentMeanLocation = clusterable.getLocation().clone();
        } else {
            this.sumArrays(clusterable);
        }
        return this.clusterableList.add(clusterable);
    }

    private void sumArrays(@NotNull final Clusterable clusterable) {
        Double[] location = clusterable.getLocation();
        for (int i = 0; i < this.currentMeanLocation.length; i++) {
            this.currentMeanLocation[i] += location[i];
        }
    }

    public boolean removeClusterable(@NotNull final Object clusterable) {
        return this.clusterableList.remove(clusterable);
    }

    public
    @NotNull
    Double[] getClusterMean() {
        Double[] meanLocation = new Double[this.currentMeanLocation.length];
        for (int i = 0; i < this.currentMeanLocation.length; i++) {
            meanLocation[i] = this.currentMeanLocation[i] / ((double) this.clusterableList.size());
        }
        this.lastMeanValue = meanLocation;
        return meanLocation;
    }

    @Override
    public Double[] getLocation() {
        return this.location;
    }

    public List<Clusterable> getClusterableList() {
        return clusterableList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cluster{");
        sb.append("id=").append(id);
        sb.append(", lastMeanValue=").append(Arrays.toString(lastMeanValue));
        sb.append(", currentMeanLocation=").append(Arrays.toString(currentMeanLocation));
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return this.id;
    }
}
