package com.unitTestGenerator.printers;

import com.unitTestGenerator.ioc.anotations.Component;

@Component
public class PrintService {

    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_DARK_GREEN = "\u001B[38;5;22m";
    private String ANSI_DARK_GREEN_RGB = "\u001B[38;2;0;100;0m";

    public PrintService() {
    }

    public void print_BLUE(String Element){
       System.out.println(ANSI_BLUE + Element+ ANSI_RESET);
   }


    public void print_YELLOW(String Element){
        System.out.println(ANSI_YELLOW + Element+ ANSI_RESET);
    }


    public void print_RED(String Element){
        System.out.println(ANSI_RED + Element+ ANSI_RESET);
    }


    public void print_GREEN(String Element){
        System.out.println(ANSI_GREEN + Element+ ANSI_RESET);
    }


    public void print_DARKGREEN(String Element){
        System.out.println(ANSI_DARK_GREEN + Element+ ANSI_RESET);
    }


}
