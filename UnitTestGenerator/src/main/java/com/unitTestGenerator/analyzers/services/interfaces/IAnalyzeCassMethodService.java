package com.unitTestGenerator.analyzers.services.interfaces;
import com.unitTestGenerator.pojos.Clase;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface IAnalyzeCassMethodService {


    default void analyzeMethod(String content, Clase classs) {
        this.analyzeMethodRelationsStatic(content, classs);
        this.analyzeMethodRelationsSingelton(content,classs);
        this.analyzeMethodRelationsStrongDependencyAssociationNew(content,classs);
        this.analyzeMethodRelationsStrongDependencyAssociationBuilder(content,classs);
    }


    default void analyzeMethodRelationsStatic(String content, Clase classs) {
        Pattern pattern = Pattern.compile("\\b([A-Z][a-zA-Z0-9_]*)\\.([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(([^)]*)\\)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String clase = matcher.group(1);
            String metodoEstatico = matcher.group(2);
            String parametros = matcher.group(3);
            if(!classs.getClassRelations().getIdentifieresRelatedClasses().contains(clase)){
                classs.getClassRelations().addIdentifieres(clase);
            }
        }
    }

    default void analyzeMethodRelationsSingelton(String content, Clase classs) {
        Pattern patternSingleton = Pattern.compile("\\b([A-Z][a-zA-Z0-9_]*)\\.getInstance\\(\\)\\.([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(([^)]*)\\)");
        Matcher matcherSingleton = patternSingleton.matcher(content);

        while (matcherSingleton.find()) {
            String clase = matcherSingleton.group(1);
            String metodoEstatico = matcherSingleton.group(2);
            String parametros = matcherSingleton.group(3);
            if(!classs.getClassRelations().getIdentifieresRelatedClasses().contains(clase)){
                classs.getClassRelations().addIdentifieres(clase);
            }
        }
    }

    default void analyzeMethodRelationsStrongDependencyAssociationNew(String content, Clase classs){
        Pattern patternNewObject = Pattern.compile("\\bnew\\s+([A-Z][a-zA-Z0-9_]*)\\s*\\(([^)]*)\\)");
        Matcher matcherNewObject = patternNewObject.matcher(content);

        while (matcherNewObject.find()) {
            String clases = matcherNewObject.group(1);
            String parametros = matcherNewObject.group(2);
            if(!classs.getClassRelations().getStrongDependencyAssociation().contains(clases)){
                classs.getClassRelations().addStrongDependencyAssociation(clases);
            }
        }
    }

    default void analyzeMethodRelationsStrongDependencyAssociationBuilder(String content, Clase classs){
        Pattern patternBuild = Pattern.compile("\\b([A-Z][a-zA-Z0-9_]*)\\.builder\\(\\)(?:\\.\\w+\\([^)]*\\))*\\.build\\(\\)");
        Matcher matcherBuild = patternBuild.matcher(content);

        while (matcherBuild.find()) {
            String Clase = matcherBuild.group(1);
            if(!classs.getClassRelations().getStrongDependencyAssociation().contains(Clase)){
                classs.getClassRelations().addStrongDependencyAssociation(Clase);
            }
        }
    }



}
