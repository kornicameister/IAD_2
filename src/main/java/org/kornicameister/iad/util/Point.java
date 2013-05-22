package org.kornicameister.iad.util;

import com.google.common.base.Objects;

import java.util.Arrays;


/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Point implements Comparable<Point>, Cloneable {
    protected Double[] position;

    public Point() {
        this(0d, 0d);
    }

    public Point(Double x, Double y) {
        this.position = new Double[]{x, y};
    }

    public Point(Double... loc) {
        this.position = loc;
    }

    @Override
    public Object clone() {
        Point point = new Point();
        point.position = this.position.clone();
        return point;
    }

    public final Double getX() {
        return this.getN(0);
    }

    public final void setX(final Double x) {
        this.setN(x, 0);
    }

    public final Double getY() {
        return this.getN(1);
    }

    public final void setY(final Double y) {
        this.setN(y, 1);
    }

    public Double[] getPosition() {
        return position;
    }

    public void setPosition(final Double[] position) {
        this.position = position;
    }

    public Double getN(int position) {
        return this.position[position];
    }

    public void setN(double value, int position) {
        this.position[position] = value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.position);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        return Objects.equal(this.position, other.position);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("position", Arrays.toString(position))
                .toString();
    }

    @Override
    public int compareTo(final Point point) {
        Double[] loc = point.position;
        int comparison = this.position[0].compareTo(loc[0]);
        int size = this.position.length >= loc.length ? loc.length : this.position.length;
        if (comparison == 0) {
            for (int i = 1 ; i < size ; i++) {
                comparison += 31 * comparison + loc[i].compareTo(this.position[i]);
            }
        }
        return comparison;
    }

    public final int getDimensions() {
        return this.position.length;
    }
}
