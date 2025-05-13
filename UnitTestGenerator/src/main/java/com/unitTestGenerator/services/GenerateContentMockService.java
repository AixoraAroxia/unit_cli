package com.unitTestGenerator.services;

import com.unitTestGenerator.interfaces.IGenerateVariable;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.interfaces.IMethodServiceTools;
import com.unitTestGenerator.pojos.*;
import com.unitTestGenerator.util.interfaces.IConstantModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Component
@Singleton
public class GenerateContentMockService implements IMethodServiceTools, IGenerateVariable, IConstantModel {


    private Project project;
    private MockitoWhen mockitoWhen;

    public GenerateContentMockService(MockitoWhen mockitoWhen) {
        this.mockitoWhen = mockitoWhen;
    }


    public void setProject(Project project){
        this.project = project;
    }


    public String generateContentMock(Metodo method, Clase clase, TestFileContent fileContent) {
        StringBuilder content = new StringBuilder("\n");
        String classNameCamelCase = StringUtils.uncapitalize(clase.getNombre()); // Use StringUtils if available
        String parametersMethodTest = this.addStringParametes(method.getParametros());
        String testMethodCall = String.format("%s.%s(%s)", classNameCamelCase, method.getNombre(), parametersMethodTest);

        String mockCalls = processInstanceMethodCalls(method, fileContent);
        content.append(mockCalls);

        String resultValueName = method.getNombre() + "Result";
        String operation = String.format("%s %s = %s;", method.getTipoRetorno(), resultValueName, testMethodCall);
        content.append("\n\t").append(operation).append("\n");
        fileContent.addImport(this.getImportInObject(method.getTipoRetorno(), project));
        String verifyMock = extractVerifyMock(method);
        content.append("\n\t").append(verifyMock);

        content.append("\t").append( this.mockitoWhen.generateCallAssertType(method, this.project, resultValueName)).append("\n");
        return content.toString();
    }

    private String processInstanceMethodCalls(Metodo method, TestFileContent fileContent) {
        if (method.getInstanceMethodCalls() == null || method.getInstanceMethodCalls().isEmpty()) {
            return "";
        }

        StringBuffer mockCalls = new StringBuffer();
        for (InstanceMethodCall instanceMethodCall : method.getInstanceMethodCalls()) {
            String[] parts = instanceMethodCall.getOperation().split("\\.");
            String classNameInstanceMethodCall = parts[0];
            String methodCallWithParams = parts[1];

            int parenthesisIndex = methodCallWithParams.indexOf('(');
            String methodName = methodCallWithParams.substring(0, parenthesisIndex);
            String parameters = methodCallWithParams.substring(parenthesisIndex + 1, methodCallWithParams.length() - 1);

            if (methodIsVoid(methodName, classNameInstanceMethodCall, this.project)) {
                mockCalls.append("\t").append( this.mockitoWhen.generateCallMethodMockDoNothing(methodName, classNameInstanceMethodCall, parameters));
            } else {
                Clase calledClass = project.getClass(classNameInstanceMethodCall);
                if(instanceMethodCall.getParametros() !=null && !instanceMethodCall.getParametros().isEmpty()){
                    instanceMethodCall.getParametros().forEach(parametroMetodo ->
                            fileContent.addVariable(
                                    this.generateVariable(
                                            parametroMetodo.getTipo(),
                                            parametroMetodo.getNombre(),
                                            false, true))
                    );
                }

                if (COMMON_METHODS.contains(methodName)) {
                    mockCalls.append("\t").append( this.mockitoWhen.generateCallMethodMock(instanceMethodCall.getOperation(), method, this.project, null, fileContent,methodName));
                } else if (calledClass != null) { //Check if calledClass is not null
                    Optional<Metodo> methodInClass = calledClass.getMetodos().stream()
                            .filter(m -> m.getNombre().equalsIgnoreCase(methodName))
                            .findFirst();
                    if(methodInClass.isPresent()){
                        mockCalls.append("\t").append( this.mockitoWhen.generateCallMethodMock(instanceMethodCall.getOperation(), methodInClass.get(), this.project, null, fileContent, null));
                    } else{
                        System.err.println("Method " + methodName + " not found in class " + classNameInstanceMethodCall);
                    }
                }
            }
        }
        return mockCalls.toString();
    }

    private String extractVerifyMock(Metodo method) {
        if (method.getInstanceMethodCalls() == null || method.getInstanceMethodCalls().isEmpty()) {
            return "";
        }

        return method.getInstanceMethodCalls().stream()
                .filter(instanceMethodCall -> COMMON_METHODS.contains(instanceMethodCall.getMethod()))
                .map(instanceMethodCall -> {
                    String parametersParts = instanceMethodCall.getOperation().substring(instanceMethodCall.getOperation().indexOf('(') + 1, instanceMethodCall.getOperation().length() - 1);
                    List<ParametroMetodo> parametersList = instanceMethodCall.getParametros().isEmpty()
                            ? Arrays.stream(parametersParts.split(","))
                            .filter(s -> !s.isEmpty()) // filter out empty strings
                            .map(s -> ParametroMetodo.builder().nombre(s.trim()).build()) // trim white space
                            .collect(Collectors.toList())
                            : instanceMethodCall.getParametros();
                    return  this.mockitoWhen.verificarMock(instanceMethodCall.getVariableInstace(), instanceMethodCall.getMethod(), parametersList);
                })
                .collect(Collectors.joining());
    }

    public String getImportInMockObject(String input, Project project){

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
