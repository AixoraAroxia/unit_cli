package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Constructor;
import com.unitTestGenerator.pojos.ParametroMetodo;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.util.random.servicesRandom.RandomRulesServices;

import java.util.ArrayList;
import java.util.List;

public interface IClassObject {

    default String generateNewClassObject(Clase clase) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("new ").append(clase.getNombre()).append("(");

        if(clase.getConstructores() !=null && !clase.getConstructores().isEmpty() && clase.getConstructores().stream().anyMatch(Constructor::isNoneParam)){

            if(!clase.getConstructores().isEmpty()){
                List<ParametroMetodo> parametros = new ArrayList<>();
                for (Constructor constructor : clase.getConstructores()){
                    if( !constructor.isEmptyParameters()){
                        parametros.addAll(constructor.getParametros());
                        break;
                    }
                }

                for (int i = 0; i < parametros.size(); i++) {
                    ParametroMetodo parametro = parametros.get(i);
                    contenido.append(parametro.getNombre());

                    if (i < parametros.size() - 1) {
                        contenido.append(", ");
                    }
                }
            }
        }
        contenido.append(")");
        return contenido.toString();
    }


    default String generateNewClassObjectConstructoresIsEmpty(Clase clase) {
        StringBuilder contenido = new StringBuilder();
        if (clase.getConstructores() != null && !clase.getConstructores().isEmpty() && clase.getConstructores().stream().anyMatch(Constructor::isEmptyParameters)) {
            contenido.append("new ").append(clase.getNombre()).append("(");
            contenido.append(")");
        }else {
            contenido.append(this.generateNewClassObject(clase));
        }
        return contenido.toString();
    }


    default String buildObject(Clase cClass, Project project) {
        StringBuilder content = new StringBuilder();
        List<String> variablesList = new ArrayList<>();
        variablesList.add("");
        content.append(cClass.getNombre()).append(".builder()").append("\n");

//        for (Variable variable : cClass.getVariables()) {
        cClass.getVariables().forEach(variable -> {
            String variableName = variable.getNombre();

            if (!variablesList.contains(variableName)) {
                String contentBody = ContextIOC.getInstance().getClassInstance(RandomRulesServices.class).buildObjectSetValues(variable);
                content.append("\t\t").append(".").append(variableName).append("(").append(contentBody).append(")").append("\n");
                variablesList.add(variableName);
            }

            if (RandomRulesServices.containTypeNamesMapList(variable.getTipo())) {
                String objectClass = ContextIOC.getInstance().getClassInstance(RandomRulesServices.class).buildObjectSetContainTypeNames(variable);
                Clase classObjectVariable = project.getClass(objectClass);
                String contentBody = this.buildObject(classObjectVariable, project);
                content.append("\t\t").append(".").append(variableName).append("(").append(contentBody).append(")").append("\n");
            }

        }
        );
        content.append("\t\t").append(".build();");
        return content.toString();
    }




}
