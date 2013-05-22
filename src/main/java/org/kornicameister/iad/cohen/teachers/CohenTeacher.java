package org.kornicameister.iad.cohen.teachers;

import com.sun.istack.internal.NotNull;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenTeacher {
    Double[] teach(@NotNull final Double[] weights, final double factor);
}
