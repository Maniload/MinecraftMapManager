package de.craplezz.mapmanager.gui;

import de.craplezz.mapmanager.data.Advancement;
import de.craplezz.mapmanager.data.Function;
import de.craplezz.mapmanager.data.World;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public class ManagerForm extends JFrame {

    private JPanel contentPane;
    private JComboBox<World> worldSelector;
    private JList<Advancement> advancementList;
    private JList<Function> functionList;
    private JTextField nameSpaceInput;
    private JButton addAdvancementButton;
    private JButton addFunctionButton;

    public ManagerForm() throws IOException {
        setTitle("MineCraft Map Manager - by Overload");
        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final File worldDirectory = new File(System.getenv("APPDATA"), ".minecraft/saves");

        if (!worldDirectory.exists()) {
            throw new IOException("World directory doesn't exists.");
        }

        worldSelector.setModel(new DefaultComboBoxModel<World>() {{

            File[] files = worldDirectory.listFiles(File::isDirectory);

            if (files != null) {
                for (File worldFile : files) {
                    addElement(new World(worldFile));
                }
            }

        }});

        worldSelector.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    World world = (World) event.getItem();

                    update(world, nameSpaceInput.getText());
                }
                else {
                    // Clear lists
                    advancementList.setModel(new DefaultListModel<>());
                    functionList.setModel(new DefaultListModel<>());
                }
            }

        });

        nameSpaceInput.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent event) {
                onChange();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                onChange();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                onChange();
            }

            private void onChange() {
                update((World) worldSelector.getSelectedItem(), nameSpaceInput.getText());
            }

        });

        addAdvancementButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
                    fileChooser.showOpenDialog(ManagerForm.this);

                    World world = (World) worldSelector.getSelectedItem();
                    Advancement advancement = world.addAdvancement(nameSpaceInput.getText(), fileChooser.getSelectedFile());

                    ((DefaultListModel<Advancement>) advancementList.getModel()).addElement(advancement);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(ManagerForm.this, "Datei konnte nicht kopiert werden.");
                }
            }

        });

        addFunctionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("TXT Files", "txt"));
                    fileChooser.showOpenDialog(ManagerForm.this);

                    World world = (World) worldSelector.getSelectedItem();
                    Function function = world.addFunction(nameSpaceInput.getText(), fileChooser.getSelectedFile());

                    ((DefaultListModel<Function>) functionList.getModel()).addElement(function);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(ManagerForm.this, "Datei konnte nicht kopiert werden.");
                }
            }

        });
    }

    public void open() {
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }

    public void update(World world, String nameSpace) {
        advancementList.setModel(new DefaultListModel<Advancement>() {{

            for (Advancement advancement : world.getAdvancements(nameSpace)) {
                addElement(advancement);
            }

        }});
        functionList.setModel(new DefaultListModel<Function>() {{

            for (Function function : world.getFunctions(nameSpace)) {
                addElement(function);
            }

        }});
    }

}
