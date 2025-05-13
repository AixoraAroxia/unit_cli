package com.unitTestGenerator.services;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.InstanceMethodCall;
import com.unitTestGenerator.pojos.ParametroMetodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Singleton
public class MethodService {

    public MethodService() {
    }


    public  List<InstanceMethodCall> findOperationPerformedInMethod(String contentMethod, String variable) {

        List<InstanceMethodCall> operationsInstanceMethodCall = null;

        if(contentMethod != null && !contentMethod.equals("") &&
                variable != null && !variable.equals("") &&
                contentMethod.contains(variable)) {

            List<ParametroMetodo> parameters = new ArrayList<>();
            operationsInstanceMethodCall = new ArrayList<>();
            Pattern patronOperaciones = Pattern.compile("\\b" + variable + "\\.(\\w+)\\((.*?)\\)");
            Matcher matcherOperaciones = patronOperaciones.matcher(contentMethod);

            while (matcherOperaciones.find()) {
                String nombreMetodo = matcherOperaciones.group(1);
                String parametros = matcherOperaciones.group(2);

                String operation = String.format("%s.%s(%s)",
                        variable != null ? variable : "",
                        nombreMetodo != null ? nombreMetodo : "",
                        parametros != null ? parametros : ""
                );

                List<String> parametrosList = new ArrayList<>(Arrays.asList(parametros.split("\\s*,\\s*")));
                for (String param : parametrosList) {
                    param = param.trim();
                    String regex = "(\\w+)\\s+" + Pattern.quote(param) + "\\s*=";
                    Pattern patterVariable = Pattern.compile(regex);
                    Matcher matcherVariable = patterVariable.matcher(contentMethod);
                    if (matcherVariable.find()) {
                        String typeVariable = matcherVariable.group(1);
                        parameters.add(ParametroMetodo.builder().nombre(param).tipo(typeVariable).build());
                    }
                }
                InstanceMethodCall call =  InstanceMethodCall.builder().variableInstace(variable).method(nombreMetodo)
                        .parametros(parameters).operation(operation).build();
                operationsInstanceMethodCall.add(call);
            }
        }
        return operationsInstanceMethodCall;
    }
}
