package com.unitTestGenerator.services;

import com.unitTestGenerator.builders.AddPatterBuilder;
import com.unitTestGenerator.interfaces.IClassObject;
import com.unitTestGenerator.interfaces.IManageMavenGadleAppProperties;
import com.unitTestGenerator.interfaces.IMethodServiceTools;
import com.unitTestGenerator.interfaces.IReturnType;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@Singleton
public class GenerateContentWithoutMockService implements IReturnType, IMethodServiceTools, IManageMavenGadleAppProperties, IClassObject {

    private Project project;

    @Inyect
    private AddPatterBuilder addPatterBuilder;

    public GenerateContentWithoutMockService() {
    }

    public void setProject(Project project){
        this.project = project;
    }

    
    public String generarContenidoSinMock(Metodo metodo, Clase clase, TestFileContent fileContent) {

        StringBuilder content = new StringBuilder();

        if(metodo != null  && metodo.getNombre() != null){
            List<Clase> parametersClassList = new ArrayList<>();


            if( metodo.getParametros() !=null && !metodo.getParametros().isEmpty()){
                metodo.getParametros().forEach(parametroMetodo -> parametersClassList.add(this.project.getClass(parametroMetodo.getTipo())));
            }
            content.append(this.generateParameterObjects(metodo,parametersClassList));
            content.append("\n");
            String responseVariableName = "response";
            content.append(this.generateClassMethodCall(metodo,clase, responseVariableName));
            content.append("\n")
                    .append("\t")
                    .append(this.getAssertType(metodo.getTipoRetorno(), "true", responseVariableName ));

        }
        return content.toString();
    }


    private String generateClassMethodCall(Metodo metodo, Clase clase, String responseVariableName){
        StringBuffer buffer = new StringBuffer();
        String methodName = metodo.getNombre();
        String classNameCamelCase = stringEnsamble(clase.getNombre().substring(0, 1).toLowerCase(), clase.getNombre().substring(1));
        buffer.append("\t")
                .append(metodo.getTipoRetorno())
                .append(" ")
                .append(responseVariableName).append(" = ")
                .append(classNameCamelCase)
                .append(".")
                .append(generateCallMethod(methodName, metodo.getParametros()));
        return buffer.toString();
    }

    private void askForAddBuildPatterInClass(Clase clase1){
        Scanner scanner = new Scanner(System.in);
            System.out.println("You want to implement the build pattern in class "+clase1.getNombre()+" for object creation?");
            System.out.println("Choose an option:");
            System.out.println("1. yes");
            System.out.println("2. no");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                     this.buildObjectTypeBuild(clase1);
                default:
                    System.out.println("ok");
            }
    }

    private void buildObjectTypeBuild(Clase clase1){
        Scanner scanner = new Scanner(System.in);
            System.out.println("You want to implement lombok o patter build in class "+clase1.getNombre()+"?");
            System.out.println("Choose an option:");
            System.out.println("1.Lombok");
            System.out.println("2.Patter build in class");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    this.addLombokDependency(project);
                    clase1.addClassAnotation("@Builder");
                    clase1.setUseLomboxBuild(true);
                    clase1.setApplyBuildMethod(false);
                    break;
                case 2:
                    String filePath =  stringPaths(false, false,
                            this.project.getPathProject(),
                            "src","main","java",
                            packageToPaths(clase1.getPaquete()),
                            stringEnsamble( clase1.getNombre(),".java")
                    );
                    try {
                        addPatterBuilder.generateBuilderPatterFromClassFile(filePath);
                        clase1.setApplyBuildMethod(true);
                        clase1.setUseLomboxBuild(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Invalid option");
                    System.out.println("No changes will be made to the class");
            }
    }

    private String generateCallMethod(String methodName, List<ParametroMetodo> parametros) {
        StringBuilder contenido = new StringBuilder();
        contenido.append(methodName).append("(");

        for (int i = 0; i < parametros.size(); i++) {
            ParametroMetodo parametro = parametros.get(i);
            contenido.append(parametro.getNombre());
            if (i < parametros.size() - 1) {
                contenido.append(", ");
            }
        }
        contenido.append(");");
        return contenido.toString();
    }

    private String generateParameterObjects(Metodo metodo, List<Clase> parametersClassList){
        StringBuilder content = new StringBuilder();

        parametersClassList.forEach(clase1 -> {
            Optional<ParametroMetodo> parameter = metodo.getParametros().stream().filter(parametroMetodo -> parametroMetodo.getTipo().equals(clase1.getNombre())).findFirst();
            String parameterNameType = stringEnsamble(parameter.get().getTipo(), " ", parameter.get().getNombre());

            if (!clase1.getUseLomboxBuild() && !clase1.getApplyBuildMethod()) {
                askForAddBuildPatterInClass(clase1);
            }
            if (clase1.getUseLomboxBuild() || clase1.getApplyBuildMethod()) {
                String newParameterObjectClass = stringEnsamble("\t",parameterNameType, " = ", this.buildObject(clase1,this.project));
                content.append(newParameterObjectClass).append("\n");
            } else {
                content.append(generateNewObject(parameterNameType, parameter.get().getNombre(), clase1) );
            }
        });

        return content.toString();
    }

    private String generateNewObject(String parameterNameType, String parameterName, Clase clase1){
        StringBuilder content = new StringBuilder();
        String newParameterObjectClass = stringEnsamble(parameterNameType, " = ", this. generateNewClassObjectConstructoresIsEmpty(clase1), ";\n");
        content.append(newParameterObjectClass).append("\n");
        content.append("\t").append(stringEnsamble("//TODO: Place the content the object: ",parameterName," Manually...")).append("\n");
        return content.toString();
    }

}
