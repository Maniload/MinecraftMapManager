package de.craplezz.mapmanager.data;

import java.io.File;

/**
 * @author Overload
 * @version 1.0
 */
public class Function {

    private final File file;

    public Function(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

}
