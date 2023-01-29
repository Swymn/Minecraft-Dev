package fr.swynn.creators;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryCreator {


    public static void create(String path) {
        create("", Paths.get(path == null ? "" : path));
    }

    public static void create(Path path) {
        create("", path);
    }

    public static void create(String name, String path) {
        create(name, Paths.get(path == null ? "" : path));
    }

    public static void create(String name, Path path) throws IllegalArgumentException {

        if (path.toString().isEmpty() || path.toString().isBlank() || path.toString().equals(".") || path.toString().equals("./"))
            throw new IllegalArgumentException("The path is empty");

        Path PATH = name.equals("") ? path : path.resolve(name);

        try {
            if (Files.exists(PATH))
                throw new IllegalArgumentException("The directory " + PATH + " already exists");
            Files.createDirectories(PATH);
        } catch (FileAlreadyExistsException e) {
            throw new IllegalArgumentException("The directory " + PATH + " already exists");
        } catch (SecurityException e) {
            throw new IllegalArgumentException("You don't have the permission to create the directory " + PATH);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while creating the directory " + PATH);
        }
    }

    public static boolean exists(String path) {
        return Files.exists(Paths.get(path == null ? "" : path));
    }
}
