package com.unitTestGenerator.analyzers.services;
import com.unitTestGenerator.ioc.anotations.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SingletonDetectorService {

    public static boolean isSingleton(String classContent) {

        Pattern instancePattern = Pattern.compile("private\\s+static\\s+[\\w<>]+\\s+(\\w+)\\s*;");
        Matcher instanceMatcher = instancePattern.matcher(classContent);
        boolean hasInstance = instanceMatcher.find();


        Pattern constructorPattern = Pattern.compile("private\\s+" + getClassName(classContent) + "\\s*\\(.*?\\)\\s*\\{");
        Matcher constructorMatcher = constructorPattern.matcher(classContent);
        boolean hasPrivateConstructor = constructorMatcher.find();


        Pattern getInstancePattern = Pattern.compile("public\\s+static\\s+[\\w<>]+\\s+getInstance\\s*\\(\\)\\s*\\{");
        Matcher getInstanceMatcher = getInstancePattern.matcher(classContent);
        boolean hasGetInstanceMethod = getInstanceMatcher.find();


        return hasInstance && hasPrivateConstructor && hasGetInstanceMethod;
    }

    private static String getClassName(String classContent) {
        Pattern classNamePattern = Pattern.compile("public\\s+class\\s+(\\w+)\\s*\\{");
        Matcher classNameMatcher = classNamePattern.matcher(classContent);
        return classNameMatcher.find() ? classNameMatcher.group(1) : "";
    }

}
