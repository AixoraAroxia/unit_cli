package com.unitTestGenerator.analyzers.services.interfaces;

import com.unitTestGenerator.interfaces.IReturnType;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.ClassRelations;
import com.unitTestGenerator.pojos.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface IAnalyzeClassRelationsService extends IReturnType {

    default void getClassSignatureLine(String contenido, Clase clase){
        Pattern patronClase =  Pattern.compile("public class (\\w+)(?:\\s+extends\\s+[^{]+)?(?:\\s+implements\\s+[^{]+)?\\s*\\{");
        Matcher matcherClase = patronClase.matcher(contenido);

        if (matcherClase.find()) {
            clase.setClassSignatureLine(matcherClase.group(0));
        } else {
            Pattern patronInterface = Pattern.compile("public interface (\\w+)(?:\\s+extends\\s+[^{]+)?(?:\\s+implements\\s+[^{]+)?\\s*\\{");
            Matcher matcherInterface = patronInterface.matcher(contenido);
            if (matcherInterface.find()) {
                clase.setClassSignatureLine(matcherInterface.group(0));
            }
        }
    }


    default void getClassSignatureLineAnotations(String contenido, Clase clase) {

        String signatureWithAnotations = "";

        if ("class".equals(clase.getTypeClass())) {
            Pattern patronClase = Pattern.compile("(@[^\\n\\r]+[\\n\\r]+)*\\s*public class (\\w+)(?:\\s+extends\\s+[^{]+)?(?:\\s+implements\\s+[^{]+)?\\s*\\{");
            Matcher matcherClase = patronClase.matcher(contenido);
            if (matcherClase.find()) {
                signatureWithAnotations = matcherClase.group(0).trim();
            }
        }

        if ("interface".equals(clase.getTypeClass())) {
            Pattern patronInterface = Pattern.compile("(@[^\\n\\r]+[\\n\\r]+)*\\s*public interface (\\w+)(?:\\s+extends\\s+[^{]+)?\\s*\\{");
            Matcher matcherInterface = patronInterface.matcher(contenido);
            if (matcherInterface.find()) {
                signatureWithAnotations = matcherInterface.group(0).trim();
            }
        }

        if(signatureWithAnotations != null && !signatureWithAnotations.equals("")){
            Pattern patronAnotacion = Pattern.compile("@[^\\n]*");
            Matcher matcherAnotacion = patronAnotacion.matcher(signatureWithAnotations);
            StringBuilder anotations = new StringBuilder();
            while (matcherAnotacion.find()) {
                anotations.append(matcherAnotacion.group()).append(",");
            }
            clase.setClassAnotations(anotations.toString());
        }
    }


    default void getClassRelationsInClassSignatureLine(Clase clase){
        String classExtends ="";
        List<String> classImplements = new ArrayList<>();
        String firmaClase = clase.getClassSignatureLine();

        Pattern patronExtends = Pattern.compile("extends\\s+([\\w\\s,]+)");
        Matcher matcherExtends = patronExtends.matcher(firmaClase);

        Pattern patronImplements = Pattern.compile("implements\\s+([\\w\\s,]+)");
        Matcher matcherImplements = patronImplements.matcher(firmaClase);

        if (matcherExtends.find()) {
            classExtends = matcherExtends.group(1);
        }

        if (matcherImplements.find()) {
            String temp = matcherImplements.group(1);
            String[] arreglo =  temp.trim().split(",");
            classImplements = Arrays.stream(arreglo).collect(Collectors.toList());
        }

        if((classExtends != null && !classExtends.equals("")) || (classImplements != null && !classImplements.isEmpty())){
            ClassRelations relations = ClassRelations.builder().className(clase.getNombre()).classType(clase.getTypeClass()).build();
            relations.setClassExtends(classExtends);
            relations.setImplementsList(classImplements);
            clase.setClassRelations(relations);
        }

    }

    default void getRelationsIOCInClass(Clase xclass){

        List<String> iocAnotations = Arrays.asList("@Componet", "@Service", "@Bean", "@Singleton");

        if(xclass.getVariables() != null && !xclass.getVariables().isEmpty()){
            xclass.getVariables().stream().forEach(variable -> {
                if(!this.isValidTypeReturn(variable.getTipo()) && variable.getAnotation().equals("@Autowired")){
                    if(!xclass.getClassRelations().getDependencyInjectionIoC().contains(variable.getTipo())){
                        xclass.getClassRelations().addDependencyInjectionIoC(variable.getTipo());
                    }
                }
            });
        }

        if(xclass.getConstructores() != null && !xclass.getConstructores().isEmpty()){
            xclass.getConstructores().stream().forEach(constructor -> {
                constructor.getParametros().stream().forEach(parametroMetodo -> {
                    Variable var = xclass.getVariable(parametroMetodo.getTipo());
                    if(var != null && xclass.checkAnotation(iocAnotations) ){
                        if(!xclass.getClassRelations().getDependencyInjectionIoC().contains(var.getTipo())){
                            xclass.getClassRelations().addDependencyInjectionIoC(var.getTipo());
                        }
                    }
                });
            });
        }
    }






}
