package com.unitTestGenerator.menus.interfaces;


import java.util.Scanner;

public interface MainMenue {

    default void welcomeMenu(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Welcome to the unit test creation tool").append("\n");
        buffer.append("Choose an option:").append("\n");
        buffer.append("1. Analyze project").append("\n");
        buffer.append("2. Generate unit tests").append("\n");
        buffer.append("3. Exit*").append("\n");
        String ANSI_YELLOW = "\u001B[33m";
        System.out.println(ANSI_YELLOW + buffer.toString());
//        System.out.println(buffer.toString());
    }

    default boolean questionAboutUseMock(Scanner scanner) {
        String respuesta ="n";
        System.out.println("Do you want to use Mock in test Class?.(y/n)");
        respuesta = scanner.next().toLowerCase();
        while (!respuesta.equals("y") && !respuesta.equals("n")) {
            System.out.println("Invalid response. Please enter 'y' for yes or 'n' for no.");
            respuesta = scanner.next().toLowerCase();
        }
        return respuesta.equals("y");
    }


    default boolean askContinue(Scanner scanner) {
        System.out.println("Return to main menu? (y/n)");
        String response = scanner.next().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Invalid response. Please enter 'y' for yes or 'n' for no.");
            response = scanner.next().toLowerCase();
        }
        return response.equals("y");
    }


    default Integer getNumber(Scanner scanner){
        System.out.println("What method do you want to test?");
        System.out.println("Choose an option:");
        System.out.println("1. All");
        System.out.println("2. Name of Specific method");
        int opcion = 0;

        try {
            opcion = scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("No valid data... ");
            System.out.println("Choose an option:");
            System.out.println("Only numbers 1 or 2");
            System.out.println("1. All");
            System.out.println("2. Name of Specific method");
            opcion = scanner.nextInt();
        }
        return opcion;
    }

    default String questionOfMethodToTest(Scanner scanner){
        String respuesta ="";
        Integer opcion = this.getNumber(scanner);

        if(opcion == 1){
            respuesta = "all";
        }
        if(opcion == 2 ){
            System.out.println("Enter the name of the method to be tested");
            respuesta = scanner.next().toLowerCase();
        }

        if( opcion != 1 && opcion != 2){
            System.out.println("Invalid response. Please enter '1' for yes or '2' .");
            if( opcion != 1 && opcion != 2){
                System.out.println("You have entered an invalid option again. Do you want to continue or cancel?");
                System.out.println("1. All");
                System.out.println("2. Name of Specific method");
                int opcionInvalid = scanner.nextInt();
                respuesta = opcionInvalid == 1? "all":"CANCEL";
            }
        }
        return respuesta;
    }

}
