package com.unitTestGenerator.util.random.servicesRandom;

import com.unitTestGenerator.interfaces.IReturnType;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Variable;
import com.unitTestGenerator.util.interfaces.IConstantModel;

import java.util.Arrays;
import java.util.List;

@Component
@Singleton
public class RandomRulesServices implements IReturnType {

    private static List<String> collectionListTypeNames = Arrays.asList("List", "Set", "ArrayList", "LinkedList", "Vector", "Stack", "HashSet", "TreeSet", "LinkedHashSet", "Queue", "LinkedList", "PriorityQueue", "Deque", "ArrayDeque", "LinkedList");
    private static List<String> collectionMapTypeNames = Arrays.asList("Map", "HashMap", "TreeMap", "LinkedHashMap", "HashTable");


    public RandomRulesServices() {
    }


    public static Boolean nameC(String variableName) {
        if (variableName == null) return false;
        Boolean reponse = variableName.toLowerCase().trim().equals(IConstantModel.NAME) || variableName.toLowerCase().trim().contains(IConstantModel.NAME);
        return reponse;
    }

    public static Boolean name(String variableName) {
        if (variableName == null) return false;
        Boolean reponse = variableName.toLowerCase().trim().equals(IConstantModel.NAME);
        return reponse;
    }

    public static Boolean codeC(String variable) {
        if (variable == null) return false;
        Boolean reponse = variable.toLowerCase().trim().equals(IConstantModel.CODE) || variable.toLowerCase().trim().contains(IConstantModel.CODE);
        return reponse;
    }

    public static Boolean code(String variable) {
        if (variable == null) return false;
        Boolean reponse = variable.toLowerCase().trim().equals(IConstantModel.CODE);
        return reponse;
    }

    public static Boolean descriptionC(String variable) {
        if (variable == null) return false;
        Boolean reponse = variable.toLowerCase().trim().equals(IConstantModel.DESCRIPTION) || variable.toLowerCase().trim().contains(IConstantModel.DESCRIPTION);
        return reponse;
    }

    public static Boolean description(String variable) {
        if (variable == null) return false;
        Boolean reponse = variable.toLowerCase().trim().equals(IConstantModel.DESCRIPTION);
        return reponse;
    }

    public static Boolean checkAllRules(String variable) {
        if (variable == null) return false;
        Boolean reponse = !RandomRulesServices.name(variable) && !RandomRulesServices.code(variable) && !RandomRulesServices.description(variable);
        return reponse;
    }

    public static boolean containTypeNamesMapList(String type) {
        Boolean response = type != null && collectionListTypeNames.contains(type) || collectionMapTypeNames.contains(type);
        return response;
    }

    public static boolean containTypeNamesMap(String type) {
        Boolean response = type != null && collectionMapTypeNames.contains(type);
        return response;
    }

    public static boolean containTypeNamesList(String type) {
        Boolean response = type != null && collectionListTypeNames.contains(type);
        return response;
    }


    public String buildObjectSetValues(Variable variable) {
        String response = "";
        if (variable != null) {
            StringBuilder content = new StringBuilder();
            String variableName = variable.getNombre();

            if (this.isValidTypeReturn(variable.getTipo())) {
                if (RandomRulesServices.nameC(variableName)) content.append(getDefaultValueName());

                if (RandomRulesServices.codeC(variableName)) content.append(getDefaultValueCode());

                if (RandomRulesServices.checkAllRules(variableName))
                    content.append(this.getDefaultValue(variable.getTipo()));

                if (RandomRulesServices.descriptionC(variableName)) content.append(getDefaultValueDescription(5));
            }
            response = content.toString();
        }
        return response;
    }


    public String buildObjectSetContainTypeNames(Variable variable) {
        String objectClass = "";

        if (RandomRulesServices.containTypeNamesList(variable.getTipo())) {
            objectClass = this.extractListContent(variable.getTipo());
        }

        if (RandomRulesServices.containTypeNamesMap(variable.getTipo())) {
            String input = this.extractMapContent(variable.getTipo());
            String key = this.getKey(input);
            String value = this.getValue(input);
            objectClass = value;
        }
        return objectClass;
    }

    }



