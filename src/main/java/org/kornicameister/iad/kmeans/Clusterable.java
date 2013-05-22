package org.kornicameister.iad.kmeans;

/**
 * Clusterable is an interface for objects which can be clustered.
 * If you intend to use KMeans clustering you may want to implement
 * this interface
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Clusterable {
    Double[] getLocation();
}
