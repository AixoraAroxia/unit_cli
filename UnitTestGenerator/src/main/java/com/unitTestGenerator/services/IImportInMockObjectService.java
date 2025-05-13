package com.unitTestGenerator.services;

import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.unitTestGenerator.util.interfaces.IConstantModel.COMMON_IMPORTS;

public interface IImportInMockObjectService {

    default String getImportInMockObject(String input, Project project){
        String importName ="";
        try {
            String regex = "\\bprivate\\b\\s+(\\w+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String className = matcher.group(1);
                Clase clase = project.getClass(className);
                if(clase != null && clase.getNombre() !=null && clase.getPaquete() != null){
                    importName = String.format("\nimport %s.%s;",clase.getPaquete(), clase.getNombre());
                }else if(COMMON_IMPORTS.contains(className)){
                    importName = String.format("\nimport %s.%s;","java.util", className);
                }
            }
            return importName;
        }catch (Exception e){
            e.printStackTrace();
            return importName;
        }
    }
}
