package com.unitTestGenerator.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DetectSO {

    private static String pathSeparator = java.nio.file.FileSystems.getDefault().getSeparator();
    private static String sDirectorioTrabajo = System.getProperty("user.dir");
    private static String direccionDeCarpeta = sDirectorioTrabajo + pathSeparator+ "lib"+ pathSeparator;

        public static String os() {
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            String respuesta = "";
            if (sistemaOperativo.contains("win")) {
                respuesta = "win";
            } else if (sistemaOperativo.contains("mac")) {
                respuesta = "mac";
            } else if (sistemaOperativo.contains("nix") || sistemaOperativo.contains("nux") || sistemaOperativo.contains("aix")) {
                respuesta = "Linux/Unix";
            } else {
                System.out.println("Operative system : " + sistemaOperativo);
            }
            return respuesta;
        }



    public String datos_pc() {
        String usar = "";
        try {

            String hostName = InetAddress.getLocalHost().getHostAddress();
            InetAddress addr = InetAddress.getByName(hostName);
            String hostname = addr.getHostName();
            String operativeSystem = System.getProperty("os.name");

            if (operativeSystem.equals("Windows")) {
                usar = "\\";
            }else {
                usar = "//";
            }
            return usar;
        } catch (UnknownHostException e) {
            return "NO SE EJECUTOEL SCRIP EL ERROR: " + e;
        }
    }



    }
