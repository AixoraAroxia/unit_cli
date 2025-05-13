package com.unitTestGenerator.analyzers.services;


import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.ImportAnalizePojo;
import com.unitTestGenerator.util.interfaces.IBaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImportAnalizeService implements IBaseModel {

    public ImportAnalizeService() {
    }

    public ImportAnalizePojo importAnalize(Clase clase) {

        ImportAnalizePojo importAnalizePojo = null;
        String[] parts = clase.getPaquete().split("\\.");
        String packageBaseName = "";

        if (parts != null && parts.length > 0) {
            packageBaseName = parts[0] + "." +parts[1];
        }
        try {
            if(packageBaseName != null && !packageBaseName.equals("")){
                Pattern importPattern = Pattern.compile("import\\s+([\\w.]+);");
                Matcher matcher = importPattern.matcher(clase.getRawClass());

                StringBuilder projectImports = new StringBuilder();
                StringBuilder externalImports = new StringBuilder();
                List<String> projectImportsList = new ArrayList<>();
                List<String> externalImportsList = new ArrayList<>();

                while (matcher.find()) {
                    String importStatement = matcher.group(1);
                    if (importStatement.contains(packageBaseName)) {
                        projectImportsList.add(IBaseModel.stringEnsamble2("import ", importStatement));
                    } else {
                        externalImportsList.add(IBaseModel.stringEnsamble2("import ", importStatement));
                    }
                }
                importAnalizePojo = ImportAnalizePojo.builder().externalImports(externalImportsList).projectImports(projectImportsList).build();
                clase.setImports(importAnalizePojo);
            }
            return importAnalizePojo;

        } catch (Exception e) {
            e.printStackTrace();
            return importAnalizePojo;
        }
    }





    private static String extractBasePackage(String filePath, String projectName) {
        String normalizedPath = filePath.replace("\\", "/");
        int projectIndex = normalizedPath.indexOf("/" + projectName + "/");
        if (projectIndex == -1) {
            throw new IllegalArgumentException("No se pudo determinar el paquete base para el proyecto: " + projectName);
        }

        String basePath = normalizedPath.substring(projectIndex + projectName.length() + 2);
        String[] pathParts = basePath.split("/");
        int srcIndex = -1;

        for (int i = 0; i < pathParts.length; i++) {
            if (pathParts[i].equals("src")) {
                srcIndex = i;
                break;
            }
        }

        if (srcIndex == -1 || srcIndex + 2 >= pathParts.length) {
            throw new IllegalArgumentException("Formato del path no reconocido");
        }

        String[] packageParts = new String[pathParts.length - (srcIndex + 2)];
        System.arraycopy(pathParts, srcIndex + 2, packageParts, 0, packageParts.length);
        return String.join(".", packageParts);
    }




}
