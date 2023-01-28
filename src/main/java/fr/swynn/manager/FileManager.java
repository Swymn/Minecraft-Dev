package fr.swynn.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private final String FILENAME;
    private final File FILE;

    public FileManager(String filename, String path) {
        this(filename, Paths.get(path == null ? "" : path));
    }

    public FileManager(String filename, Path path) throws IllegalArgumentException {
        if (Files.exists(path.resolve(filename)))
            throw new IllegalArgumentException("The file " + filename + " already exists");

        this.FILENAME = filename;

        try {
            this.FILE = new File(Files.createFile(path.resolve(FILENAME)).toString());
        } catch (FileAlreadyExistsException e) {
            throw new IllegalArgumentException("The file " + FILENAME + " already exists");
        } catch (SecurityException e) {
            throw new IllegalArgumentException("You don't have the permission to create the file " + FILENAME);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while creating the file " + FILENAME);
        }
    }

    public void write(String content) throws IllegalArgumentException {
        try {
            FileWriter writer = new FileWriter(FILE);

            writer.write(content);

            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while writing in the file " + FILENAME);
        }
    }
}
