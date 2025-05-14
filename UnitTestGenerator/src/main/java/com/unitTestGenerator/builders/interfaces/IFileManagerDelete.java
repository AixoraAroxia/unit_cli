package com.unitTestGenerator.builders.interfaces;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IFileManagerDelete {


  default boolean deleteFileNIO(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        try {
            Path path = Paths.get(filePath);
            if (!verificIsRegularFileAndExist(path)) {
                System.err.println("Path is not a regular file: " + filePath);
                return false;
            }
            return Files.deleteIfExists(path);

        } catch (SecurityException e) {
            System.err.println("Permission error when deleting: "  + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting file: "  + e.getMessage());
            return false;
        }
    }

    default boolean deleteFileIO(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File does not exist: " + filePath);
            return false;
        }
        if (!file.isFile()) {
            System.err.println("Path is not a file: " + filePath);
            return false;
        }
        return file.delete();
    }


    default boolean verificIsRegularFileAndExist(Path path) {
        return !Files.isRegularFile(path);
    }
}
