package com.unitTestGenerator.builders;

import com.unitTestGenerator.builders.interfaces.IFileContentEditor;
import com.unitTestGenerator.builders.interfaces.IFileManager;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.TestFileContent;

import java.io.File;
import java.util.Scanner;

@Component
@Singleton
public class BuildTestFile implements IFileContentEditor, IFileManager {

    public BuildTestFile() {
    }

    public void createTestFile(String ruta, TestFileContent content) {
        File file = new File(ruta);
        String oldVlue = this.fileExist(file);

        if (oldVlue != null && !oldVlue.equals("")) {
            this.startWritefiles(oldVlue, content, file);
        } else {
            this.writefilesI(file, content.toString());
        }
    }


    private void startWritefiles(String oldVlue, TestFileContent content, File archivo) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {

            System.out.println("The file exists.");
            System.out.println("Do you want me to analyze it and add only the methods and variables that do not exist,");
            System.out.println(" or do you want me to overwrite the entire file?");
            System.out.println("__________________________________________________________________________________________");
            System.out.println("Choose an option:");
            System.out.println("1. Analyze and add");
            System.out.println("2. Overwrite everything");
            System.out.println("__________________________________________________________________________________________");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    String newContentFile = this.mixFileContentOrAddContent(oldVlue, content);
                    if (newContentFile != null && !newContentFile.equals("")) {
                        this.writefilesI(archivo, newContentFile);
                    } else {
                        System.out.println("Error: Something happened in file Analyze, the file will be Overwrite everything...");
                        this.errorOrInvalidOption(archivo, content.toString());
                    }
                    continuar = false;
                    break;
                case 2:
                    this.writefilesI(archivo, content.toString());
                    continuar = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    this.errorOrInvalidOption(archivo, content.toString());
                    continuar = false;
                    break;
            }
        }
    }

    private void errorOrInvalidOption(File archivo, String content) {
        Scanner scanner = new Scanner(System.in);
        this.message();
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("End the process...");
                break;
            case 2:
                System.out.println("The file will be Overwrite everything...");
                this.writefilesI(archivo, content);
                break;
            default:
                System.out.println("End the process...");
                break;
        }
    }

    private void message() {
        System.out.println("Do you want to overwrite or end the process");
        System.out.println("Choose an option:");
        System.out.println("1. End the process");
        System.out.println("2. Overwrite everything");
    }

}
