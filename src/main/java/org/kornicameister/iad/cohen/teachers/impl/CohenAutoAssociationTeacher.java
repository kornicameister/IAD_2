package org.kornicameister.iad.cohen.teachers.impl;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.kornicameister.iad.cohen.teachers.CohenTeacher;

/**
 * AutoAssociation - force teaching - method
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class CohenAutoAssociationTeacher implements CohenTeacher {
    @Override
    public Double[] teach(@NotNull final Double[] weights, final double factor) {
        Preconditions.checkNotNull(factor, "Factor can not be null");
        Preconditions.checkArgument(factor > 0, "Factor must be positive");
        Double[] doubles = weights.clone();
        return doubles;
    }
}
