package org.kornicameister.iad.cohen;

import com.google.common.base.Objects;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class CohenNeuron extends CohenPoint {
    private static int ID = 0;
    private int id;
    private boolean thresholdReached;
    private int activationCount;
    private int wonCount;

    public CohenNeuron(final Double... loc) {
        super(loc);
        this.activationCount = 0;
        this.thresholdReached = false;
        this.wonCount = 0;
        this.id = CohenNeuron.ID++;
    }

    public void activate(final int threshold) {
        this.wonCount++;
        this.thresholdReached = ++this.activationCount == threshold;
    }

    public void deactivate() {
        this.thresholdReached = !(--this.activationCount == 0);
    }

    public boolean isActive(final int threshold) {
        return !this.thresholdReached;
    }

    public int getId() {
        return id;
    }

    public int getWonCount() {
        return wonCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CohenPoint)) return false;
        if (!super.equals(o)) return false;

        CohenNeuron point = (CohenNeuron) o;

        return id == point.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("super", super.toString())
                .add("activationCount", activationCount)
                .add("thresholdReached", thresholdReached)
                .toString();
    }
}
