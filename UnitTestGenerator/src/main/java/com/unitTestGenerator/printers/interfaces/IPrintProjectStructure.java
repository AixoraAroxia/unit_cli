package com.unitTestGenerator.printers.interfaces;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public interface IPrintProjectStructure {


    default String getStructure(String projectPath) {

        File projectDir = new File(projectPath);
        String indent = "";
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.out.println("La ruta proporcionada no es un directorio válido.");
            return "";
        }
        String tree = getDirectoryTree(projectDir, "");
        return tree;
    }

    default String getDirectoryTree(File dir, String indent) {

         List<String> IGNORED_FOLDERS = Arrays.asList("target", ".idea", ".git");

        StringBuilder tree = new StringBuilder();
        File[] files = dir.listFiles();
        if (files == null) return "";

        for (File file : files) {
            if (IGNORED_FOLDERS.contains(file.getName())) {
                continue;
            }
            tree.append(indent).append("|__ ").append(file.getName()).append("\n");
            if (file.isDirectory()) {
                tree.append(getDirectoryTree(file, indent + "  "));
            }
        }
        return tree.toString();
    }

}

