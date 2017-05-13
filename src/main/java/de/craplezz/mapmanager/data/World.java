package de.craplezz.mapmanager.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Overload
 * @version 1.0
 */
public class World {

    private final File file;

    private final Map<String, List<Advancement>> advancements;
    private final Map<String, List<Function>> functions;

    public World(File file) {
        this.file = file;

        this.advancements = new HashMap<>();
        File[] nameSpaceFiles = new File(file, "data/advancements").listFiles(File::isDirectory);
        if (nameSpaceFiles != null) {
            for (File nameSpaceFile : nameSpaceFiles) {
                String nameSpace = nameSpaceFile.getName();
                advancements.put(nameSpace, new ArrayList<>());
                File[] advancementFiles = nameSpaceFile.listFiles(advancementFile -> advancementFile.getName().endsWith(".json"));
                if (advancementFiles != null) {
                    for (File advancementFile : advancementFiles) {
                        advancements.get(nameSpace).add(new Advancement(advancementFile));
                    }
                }
            }
        }

        this.functions = new HashMap<>();
        nameSpaceFiles = new File(file, "data/functions").listFiles(File::isDirectory);
        if (nameSpaceFiles != null) {
            for (File nameSpaceFile : nameSpaceFiles) {
                String nameSpace = nameSpaceFile.getName();
                functions.put(nameSpace, new ArrayList<>());
                File[] functionFiles = nameSpaceFile.listFiles(functionFile -> functionFile.getName().endsWith(".txt"));
                if (functionFiles != null) {
                    for (File functionFile : functionFiles) {
                        functions.get(nameSpace).add(new Function(functionFile));
                    }
                }
            }
        }
    }

    public Advancement addAdvancement(String nameSpace, File file) throws IOException {
        File outputFile = new File(this.file, "data/advancements/" + nameSpace + "/" + file.getName());
        Files.copy(file.toPath(), outputFile.toPath());

        Advancement advancement = new Advancement(outputFile);
        if (!advancements.containsKey(nameSpace)) {
            advancements.put(nameSpace, new ArrayList<>());
        }
        advancements.get(nameSpace).add(advancement);

        return advancement;
    }

    public List<Advancement> getAdvancements(String nameSpace) {
        return advancements.containsKey(nameSpace) ? advancements.get(nameSpace) : Collections.emptyList();
    }

    public Function addFunction(String nameSpace, File file) throws IOException {
        File outputFile = new File(this.file, "data/functions/" + nameSpace + "/" + file.getName());
        Files.copy(file.toPath(), outputFile.toPath());

        Function function = new Function(outputFile);
        if (!functions.containsKey(nameSpace)) {
            functions.put(nameSpace, new ArrayList<>());
        }
        functions.get(nameSpace).add(function);

        return function;
    }

    public List<Function> getFunctions(String nameSpace) {
        return functions.containsKey(nameSpace) ? functions.get(nameSpace) : Collections.emptyList();
    }

    @Override
    public String toString() {
        return file.getName();
    }

}
