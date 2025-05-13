package com.unitTestGenerator.analyzers.services;
import com.unitTestGenerator.ioc.anotations.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SingletonDetectorService {

    public static boolean isSingleton(String classContent) {
        // 1. Verificar si hay un campo estático privado de la misma clase
        Pattern instancePattern = Pattern.compile("private\\s+static\\s+[\\w<>]+\\s+(\\w+)\\s*;");
        Matcher instanceMatcher = instancePattern.matcher(classContent);
        boolean hasInstance = instanceMatcher.find();

        // 2. Verificar si hay un constructor privado
        Pattern constructorPattern = Pattern.compile("private\\s+" + getClassName(classContent) + "\\s*\\(.*?\\)\\s*\\{");
        Matcher constructorMatcher = constructorPattern.matcher(classContent);
        boolean hasPrivateConstructor = constructorMatcher.find();

        // 3. Verificar si hay un método estático que retorna la instancia
        Pattern getInstancePattern = Pattern.compile("public\\s+static\\s+[\\w<>]+\\s+getInstance\\s*\\(\\)\\s*\\{");
        Matcher getInstanceMatcher = getInstancePattern.matcher(classContent);
        boolean hasGetInstanceMethod = getInstanceMatcher.find();

        // La clase es Singleton si tiene las tres características
        return hasInstance && hasPrivateConstructor && hasGetInstanceMethod;
    }

    private static String getClassName(String classContent) {
        Pattern classNamePattern = Pattern.compile("public\\s+class\\s+(\\w+)\\s*\\{");
        Matcher classNameMatcher = classNamePattern.matcher(classContent);
        return classNameMatcher.find() ? classNameMatcher.group(1) : "";
    }

}
