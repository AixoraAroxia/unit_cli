package com.unitTestGenerator.util.interfaces;

public interface ICleanConsole {


    public static void cleanConsoleSO() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            clearConsoleWindows();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac") || os.contains("aix")) {
            clearConsoleUnix();
        } else {
            clearConsola();
        }
    }

    public static void clearConsoleWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearConsoleUnix() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearConsola() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearConsoleOs() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }
}
