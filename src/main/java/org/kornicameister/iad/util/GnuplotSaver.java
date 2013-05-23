package org.kornicameister.iad.util;

import java.io.*;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

public class GnuplotSaver {

    private PrintWriter writer;

    public static void saveToFile(final String file, List<? extends Point> pointList) throws IOException {
        GnuplotSaver gnuplotSaver = new GnuplotSaver();
        gnuplotSaver.openFile(file);
        gnuplotSaver.toFile(pointList);
        gnuplotSaver.closeFile();
    }

    public void openFile(final String file) throws IOException {
        this.writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(file))));
    }

    public void toFile(List<? extends Point> pointList) {
        for (Point point : pointList) {
            for (Double loc : point.getPosition()) {
                this.writer.print(loc);
                this.writer.print(" ");
            }
            this.writer.println();
        }
    }

    public void closeFile() {
        this.writer.close();
        this.writer = null;
    }
}
