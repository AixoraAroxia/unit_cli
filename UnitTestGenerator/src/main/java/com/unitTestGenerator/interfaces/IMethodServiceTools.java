package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.pojos.*;

import java.util.ArrayList;
import java.util.List;

public interface IMethodServiceTools {


    default String getImportInObject(String input, Project project){
        String importName ="";
        try {
            String className = input;
            Clase clase = project.getClass(className);
            if(clase != null && clase.getNombre() !=null && clase.getPaquete() != null){
                importName = String.format("\nimport %s.%s;",clase.getPaquete(), clase.getNombre());
            }
            return importName;
        }catch (Exception e){
            e.printStackTrace();
            return importName;
        }
    }

    default Boolean methodIsVoid(String methodName, String className, Project project){

        if (methodName == null || methodName.isEmpty() || className == null || className.isEmpty() || project == null) {
            return false;
        }
        if (methodName != null && !methodName.isEmpty() && className != null && !className.isEmpty() && project != null) {
            Clase clasS = project.getClass(className);
            boolean result = this.isMethodVoidOrNoExistInClassOrExistInInterface(clasS.getMetodos(), methodName);
            return result;
        }
        return false;
    }

    default boolean isMethodVoidOrNoExistInClassOrExistInInterface(List<Metodo> methods, String methodName) {
        boolean result = false;
        boolean noExistInClassOrExistInInterface = true;
        boolean isMethodVoid = true;

        if (methods != null && !methods.isEmpty() && methodName != null && !methods.isEmpty()) {
            if (methods.stream().anyMatch(method -> method.getNombre().toLowerCase().equals(methodName.toLowerCase()))) {
                if (methods.stream().filter(method -> method.getNombre().toLowerCase().equals(methodName.toLowerCase()))
                        .anyMatch(method -> "void".equals(method.getTipoRetorno()))) {
                    return isMethodVoid;
                } else {
                    return result;
                }
            }
        }
        return result;
    }

    default String addStringParametes(List<ParametroMetodo> parametros) {
        StringBuilder contenido = new StringBuilder();
        for (int i = 0; i < parametros.size(); i++) {
            ParametroMetodo parametro = parametros.get(i);
            contenido.append(parametro.getNombre());

            if (i < parametros.size() - 1) {
                contenido.append(", ");
            }
        }
        return contenido.toString();
    }

    default String methodParameterObject(Metodo method, Project project) {

        List<Clase> clasesParameters = new ArrayList<>();
        if (method.getParametros() != null && !method.getParametros().isEmpty()) {
            method.getParametros().stream()
                    .map(parametroMethod -> project.getClass(parametroMethod.getTipo()))
                    .filter(foundClass -> foundClass != null)
                    .forEach(clasesParameters::add);
        }
        return null;
    }


}
