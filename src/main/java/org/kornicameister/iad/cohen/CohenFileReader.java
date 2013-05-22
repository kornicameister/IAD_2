package org.kornicameister.iad.cohen;

import com.sun.javafx.beans.annotations.NonNull;

import java.util.List;
import java.util.Scanner;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public interface CohenFileReader {
    @NonNull
    List<CohenPoint> readFromFile(final Scanner scanner);
}
