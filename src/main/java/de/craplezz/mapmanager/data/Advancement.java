package de.craplezz.mapmanager.data;

import java.io.File;

/**
 * @author Overload
 * @version 1.0
 */
public class Advancement {

    private final File file;

    public Advancement(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

}
