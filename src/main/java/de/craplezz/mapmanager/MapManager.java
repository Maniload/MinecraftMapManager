package de.craplezz.mapmanager;

import de.craplezz.mapmanager.gui.ManagerForm;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public class MapManager {

    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            new ManagerForm().open();
        } catch (IOException | IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
