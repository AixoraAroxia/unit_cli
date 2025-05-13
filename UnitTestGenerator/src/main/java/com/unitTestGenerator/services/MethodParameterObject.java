package com.unitTestGenerator.services;

import com.unitTestGenerator.interfaces.IGenerateVariable;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Metodo;
import com.unitTestGenerator.pojos.Project;

import java.util.*;

@Component
@Singleton
public class MethodParameterObject implements IGenerateVariable {

    private Map<String, Clase> claseDirectory = new HashMap<>();

    public MethodParameterObject(){}


    public String methodParameterObject(Metodo method, Project project, boolean isMock ){
        String response = "";
        if (isMock) {
            response = this.getMockOject(method);
        }else {
            List<Clase> clasesParameters = getParameterClassList( method, project);
            StringBuffer parametes = new StringBuffer();
            if(clasesParameters != null && !clasesParameters.isEmpty()){
                clasesParameters.forEach(clase ->
                        parametes.append(this.generateVariable(clase.getNombre(), clase.getNombre().toLowerCase(),true, false))
                );
              response = parametes.toString();
            }
        }
        return response;
    }

    public String getImportMethodParameterObject(Clase clase, Project project){
        List<Clase> clasesParameters = null;
         String response ="";
        Metodo method = null;
        for (Metodo metodo : clase.getMetodos()) {
            if (clase.getTestMethod().toLowerCase().equals(metodo.getNombre().toLowerCase())){
                method = metodo;
            }
        }

        if(method != null){
            StringBuilder importMethodParameterObject = new StringBuilder();
            clasesParameters = getParameterClassList( method, project);
            clasesParameters.stream().forEach(clase1 -> {
                importMethodParameterObject.append( this.stringEnsamble("import ", clase1.getPaquete(), ".",clase1.getNombre())).append(";").append("\n");
            });
            response = importMethodParameterObject.toString();
        }
        return response;
    }

    private String getMockOject(Metodo method){
            StringBuilder contentWentisMock = new StringBuilder();
            if (method.getParametros() != null && !method.getParametros().isEmpty()) {
                method.getParametros().forEach(parametroMethod -> {
                    contentWentisMock.append(
                            this.generateVariable(
                                    parametroMethod.getTipo(),
                                    parametroMethod.getNombre(),
                                    false, true)
                    ).append("\n");
                });
            }
        return contentWentisMock.toString();
    }

    public List<Clase> getParameterClassList(Metodo method, Project project) {
        List<Clase> clasesParameters = new ArrayList<>();
        this.claseDirectory = new HashMap<>();
        if (method.getParametros() != null && !method.getParametros().isEmpty()) {
            method.getParametros().forEach(parametroMethod -> {
                Clase found = project.getClass(parametroMethod.getTipo());
                if (found != null) {
                    clasesParameters.add(found);
                    this.claseDirectory.put(found.getNombre(), found);
                }
            });
        }
        return clasesParameters;
    }


}
