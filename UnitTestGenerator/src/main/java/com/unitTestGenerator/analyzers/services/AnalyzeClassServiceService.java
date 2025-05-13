package com.unitTestGenerator.analyzers.services;

import com.unitTestGenerator.analyzers.services.interfaces.IAnalyzeCassMethodService;
import com.unitTestGenerator.analyzers.services.interfaces.IAnalyzeClassRelationsService;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.*;
import com.unitTestGenerator.services.MethodService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Singleton
public class AnalyzeClassServiceService implements IAnalyzeCassMethodService, IAnalyzeClassRelationsService {

    @Inyect
    private MethodService methodService;

    public AnalyzeClassServiceService() {
    }

    public Clase getAnalisisOfVariables(String content) {
        Clase clase = ContextIOC.getInstance().getClassInstance(Clase.class);
        try {
            clase = analyzeClaseContentString(content);
        } catch (Exception e) {
            System.out.println("Error  analized the content of file...");
        }
        return clase;
    }

    public Clase getAnalisisOfContenidoMetodo(String content) {
        Clase clase = ContextIOC.getInstance().getClassInstance(Clase.class);
        analyzeMethods(content, clase);
        return clase;
    }

    public Clase analyzeClase(File archivo) {
        try {
            String content = FileUtils.readFileToString(archivo, "UTF-8");
            String path = archivo.getAbsolutePath();
            Clase classs = analyzeClaseContentString(content);
            if (archivo != null && classs != null) {
                classs.setClassPath(path);
            }
            return classs;
        } catch (Exception e) {
            System.out.println("Error al analizar archivo: " + archivo.getName());
        }
        return null;
    }


    private Clase analyzeClaseContentString(String content) throws Exception {
        Clase clase = ContextIOC.getInstance().getClassInstance(Clase.class);
        clase.setRawClass(content);
        analyzePackage(content, clase);
        analyzeNamesAndTypeOfClass(content, clase);
        analyzeConstructors(content, clase);
        analyzeMethods(content, clase);
        analyzeVariables(content, clase);
        analyzeControlStructure(content, clase);
        containPatterBuild(content, clase);
        containLomboxAnotacionBuild(content, clase);
        this.getRelationsIOCInClass(clase);

        return postClassMethodAnalysis(clase);
    }

    private void analyzePackage(String contenido, Clase clase) {
        Pattern patronPaquete = Pattern.compile("package (.*?);");
        Matcher matcherPaquete = patronPaquete.matcher(contenido);

        while (matcherPaquete.find()) {
            clase.setPaquete(matcherPaquete.group(1));
        }
    }

    private void analyzeNamesAndTypeOfClass(String contenido, Clase clase) {
        Pattern patronClase = Pattern.compile("public class (\\w+)");
        Matcher matcherClase = patronClase.matcher(contenido);

        if (matcherClase.find()) {
            clase.setNombre(matcherClase.group(1));
            clase.setTypeClass("class");
            clase.setIndexFirmaClass(matcherClase.start(1));
        } else {
            Pattern patronInterface = Pattern.compile("public interface (\\w+)");
            Matcher matcherInterface = patronInterface.matcher(contenido);
            if (matcherInterface.find()) {
                clase.setNombre(matcherInterface.group(1));
                clase.setTypeClass("interface");
                clase.setIndexFirmaClass(matcherInterface.start(1));
            }
        }
        this.getClassSignatureLine(contenido, clase);
        this.getClassRelationsInClassSignatureLine(clase);
        this.getClassSignatureLineAnotations(contenido, clase);
    }


    private void getSignatureConstructors(Constructor constructor) {
//        Pattern patronConstructor = Pattern.compile("public\\s+(\\w+)\\(.*?\\)\\s*(?=\\{)", Pattern.DOTALL);
        Pattern patronConstructor = Pattern.compile("(public|private|protected)\\s+(\\w+)\\(.*?\\)\\s*(?=\\{)", Pattern.DOTALL);
        Matcher matcherConstructor = patronConstructor.matcher(constructor.getRawConstructor());

        while (matcherConstructor.find()) {
            constructor.setCostructorSignature(matcherConstructor.group(0).trim());
        }
    }


    private void analyzeConstructors(String contenido, Clase clase) {
        Pattern patronConstructor = Pattern.compile("public (\\w+)\\((.*?)\\)\\s*\\{(.*?)\\}", Pattern.DOTALL);
        Matcher matcherConstructor = patronConstructor.matcher(contenido);

        while (matcherConstructor.find()) {
            Constructor constructor = ContextIOC.getInstance().getClassInstance(Constructor.class);
            constructor.setRawConstructor(matcherConstructor.group(0));
            this.getSignatureConstructors(constructor);

            String[] parametros = matcherConstructor.group(2).split(",");
            List<ParametroMetodo> parametroMetodos = new ArrayList<>();
            for (String parametro : parametros) {
                if (!parametro.trim().isEmpty()) {
                    String[] partes = parametro.trim().split("\\s+");
                    ParametroMetodo parametroMetodo = ParametroMetodo.builder().nombre(partes[1]).tipo(partes[0]).build();
                    parametroMetodos.add(parametroMetodo);
                }
            }
            constructor.setParametros(parametroMetodos);
            constructor.setIsNoneParam(parametroMetodos.isEmpty());

            String contenidoEntreLlaves = matcherConstructor.group(3).trim();
            if (contenidoEntreLlaves.isEmpty()) {
                constructor.setCostructorContent("{}");
            } else {
                constructor.setCostructorContent(contenidoEntreLlaves);
            }
            clase.addConstructor(constructor);
        }
    }

    private void matcherMetodoMain(String contenido, Clase clase){
        Pattern patronMain = Pattern.compile("(?:@\\w+\\s*)*\\bpublic\\s+static\\s+void\\s+main\\s*\\(\\s*String\\s*\\[?\\s*\\]?\\s+\\w+\\s*\\)", Pattern.DOTALL);
        Matcher matcherMetodoMain = patronMain.matcher(contenido);

        if (matcherMetodoMain.find()) {
            Metodo metodo = ContextIOC.getInstance().getClassInstance(Metodo.class);
            String main = matcherMetodoMain.group(0).trim();
            if(main != null){
                metodo.setMain(main.toLowerCase().trim().contains("main"));
                metodo.setNombre("main");
                metodo.setTipoRetorno("void");
                metodo.setAccessModifier("public");
                metodo.setMethodSignature(main);
                clase.addMetodo(metodo);
                clase.setMainClass(metodo.getMain());
            }
        }
    }

    private void analyzeMethods(String contenido, Clase clase) {
        if (clase != null) {
            if ("class".equals(clase.getTypeClass())) {
                Pattern patronMetodo = Pattern.compile("(?:@[^\\n]*)*(public|protected|private|void) (\\w+) (\\w+)\\((.*?)\\)", Pattern.DOTALL);
                Matcher matcherMetodo = patronMetodo.matcher(contenido);
                this.matcherMetodoMain( contenido, clase);

                while (matcherMetodo.find()) {
                    Metodo metodo = ContextIOC.getInstance().getClassInstance(Metodo.class);
                    this.analyzeMethodBasic(metodo, matcherMetodo);
                    this.analyzeMethodParameters(metodo, matcherMetodo);
                    this.analyzeMethodContent(contenido, metodo, matcherMetodo);
                    metodo.setMethodSignature(matcherMetodo.group(0).trim());
                    this.analyzeMethodAnotations(contenido, metodo, metodo.getMethodSignature());
                    clase.addMetodo(metodo);
                    this.analyzeMethod(metodo.getContenido(), clase); // ***
                }
            }

            if ("interface".equals(clase.getTypeClass())) {

                Pattern patronMetodoInterface = Pattern.compile(
                        "(?:@[^\\n]*)*(public|protected|private|default)\\s+" + // Access modifier
                                "(\\w+(?:<.*?>)?)\\s+" + // Return type (including generics)
                                "(\\w+)\\s*\\((.*?)\\)\\s*;", // Method name and parameters (ends at semicolon)
                        Pattern.DOTALL
                );

                Matcher matcherMetodoInterface = patronMetodoInterface.matcher(contenido);


                while (matcherMetodoInterface.find()) {
                    Metodo metodo = ContextIOC.getInstance().getClassInstance(Metodo.class);

                    // Set method name, return type, and access modifier from the regex capture groups
                    metodo.setNombre(matcherMetodoInterface.group(3));
                    metodo.setTipoRetorno(matcherMetodoInterface.group(2));
                    metodo.setAccessModifier(matcherMetodoInterface.group(1));

                    // Process the method parameters (handle edge cases such as empty or malformed parameters)
                    try {
                        String[] parametersList = matcherMetodoInterface.group(4).split(",");
                        if (parametersList != null && parametersList.length > 0) {
                            for (String parametro : parametersList) {
                                if (!parametro.trim().isEmpty()) {
                                    String[] partes = parametro.trim().split("\\s+");
                                    if (partes.length >= 2) {
                                        ParametroMetodo parametroMetodo = ParametroMetodo.builder()
                                                .nombre(partes[1]) // Parameter name
                                                .tipo(partes[0])   // Parameter type
                                                .build();
                                        metodo.agregarParametro(parametroMetodo);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Set the method signature (we only want the method signature, not the method body)
                    String methodSignature = matcherMetodoInterface.group(0).trim();
                    String signatureWithoutBody = methodSignature.split("\\{")[0]; // Ensures we get only the header, not the body
                    metodo.setMethodSignature(signatureWithoutBody);

                    // Process annotations (if any)
                    String[] lineas = contenido.split("\n");
                    for (int i = 0; i < lineas.length - 1; i++) {
                        if (lineas[i].trim().startsWith("@") && lineas[i + 1].trim().startsWith(signatureWithoutBody)) {
                            metodo.setAnotation(lineas[i].trim());
                        }
                    }

                    // Add the method to the class
                    clase.addMetodo(metodo);
                }




//                while (matcherMetodoInterface.find()) {
//                    Metodo metodo = ContextIOC.getInstance().getClassInstance(Metodo.class);
//                    this.analyzeMethodBasic(metodo, matcherMetodoInterface);
//                    this.analyzeMethodParameters(metodo, matcherMetodoInterface);
//
//                    String[] signturePart = matcherMetodoInterface.group(0).trim().split("\\{");
//                    String va = signturePart[0];
//                    metodo.setMethodSignature(va);
//                    this.analyzeMethodAnotations(contenido, metodo, metodo.getMethodSignature());
//                    clase.addMetodo(metodo);
//                }
            }

        }
    }

    private void analyzeMethodBasic(Metodo metodo, Matcher matcherMetodo) {
        metodo.setNombre(matcherMetodo.group(3));
        metodo.setTipoRetorno(matcherMetodo.group(2));
        metodo.setAccessModifier(matcherMetodo.group(1));
    }

    private void analyzeMethodAnotations(String contenido, Metodo metodo, String methodSignature) {
        String[] lineas = contenido.split("\n");
        for (int i = 0; i < lineas.length - 1; i++) {
            if (lineas[i].trim().startsWith("@") && lineas[i + 1].trim().startsWith(methodSignature)) {
                metodo.setAnotation(lineas[i].trim());
            }
        }
    }

    private void analyzeMethodContent(String contenido, Metodo metodo, Matcher matcherMetodo) {
        String patronContenidoMetodo = matcherMetodo.group(1) + " " + metodo.getTipoRetorno() + " " + metodo.getNombre() + "\\((.*?)\\)\\s*\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(patronContenidoMetodo, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcherContenidoMetodo = pattern.matcher(contenido);
        if (matcherContenidoMetodo.find()) {
            String contenidoMetodo = matcherContenidoMetodo.group(2).trim();
            metodo.setContenido(contenidoMetodo);
        }
    }

    private void analyzeMethodParameters(Metodo metodo, Matcher matcherMetodo) {
        try {
            String[] parametersList = matcherMetodo.group(4).split(",");
            if (parametersList != null && parametersList.length > 0) {
                for (String parametro : parametersList) {
                    if (!parametro.trim().isEmpty()) {
                        String[] partes = parametro.trim().split("\\s+");
                        int index = 1;
                        if (index >= 0 && index < partes.length) {
                            if (partes[0] != null && partes[1] != null) {
                                ParametroMetodo parametroMetodo = ParametroMetodo.builder().nombre(partes[1]).tipo(partes[0]).build();
                                metodo.agregarParametro(parametroMetodo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void analyzeVariables(String content, Clase clase) {

        Pattern patronVariable = Pattern.compile("(?:@[^\\n]*)*\\s*(public|protected|private) (\\w+) (\\w+);");
        Matcher matcherVariable = patronVariable.matcher(content);
        while (matcherVariable.find()) {
            Variable variable = ContextIOC.getInstance().getClassInstance(Variable.class);
            variable.setTipo(matcherVariable.group(2));
            variable.setNombre(matcherVariable.group(3));
            variable.setAccessModifier(matcherVariable.group(1));
            Pattern patronAnotacion = Pattern.compile("@[^\\n]*");
            Matcher matcherAnotacion = patronAnotacion.matcher(matcherVariable.group(0));
            StringBuilder anotations = new StringBuilder();
            while (matcherAnotacion.find()) {
                anotations.append(matcherAnotacion.group()).append(" ");
            }
            variable.setAnotation(anotations.toString().trim());
            clase.addVariable(variable);
        }
    }

    private void analyzeControlStructure(String contenido, Clase clase) {
        Pattern patronEstructuras = Pattern.compile("if|else|for|while|try|catch");
        Matcher matcherEstructuras = patronEstructuras.matcher(contenido);

        while (matcherEstructuras.find()) {
            EstructuraControl estructura = ContextIOC.getInstance().getClassInstance(EstructuraControl.class);
            estructura.setTipo(matcherEstructuras.group());
            clase.addEstructura(estructura);
        }
    }

    private Clase postClassMethodAnalysis(Clase classIn) {
        if (classIn.getVariables() != null && !classIn.getVariables().isEmpty() && classIn.getMetodos() != null && !classIn.getMetodos().isEmpty()) {
            try {
                classIn.getVariables().stream().forEach(variable -> {
                    classIn.getMetodos().stream().forEach(method -> {
                        if (method != null && variable != null && variable.getNombre() != null && method.getContenido() != null) {
                            method.addInstanceMethodCallAll(
                                    methodService.findOperationPerformedInMethod(method.getContenido(), variable.getNombre())
                            );
                        }
                    });
                });
            } catch (Exception e) {
                System.err.println("Error... : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return classIn;
    }


    private void containLomboxAnotacionBuild(String contenido, Clase clase) {
        Pattern patronBuilder = Pattern.compile("@Builder");
        Matcher matcherBuilder = patronBuilder.matcher(contenido);
        if (matcherBuilder.find()) {
            clase.setUseLomboxBuild(true);
        }
    }


    private void containPatterBuild(String contenido, Clase clase) {
        Pattern patronMetodoBuilder = Pattern.compile("public static \\w+ builder\\(\\)");
        Pattern patronMetodoBuild = Pattern.compile("public \\w+ build\\(\\)");

        Matcher matcherMetodoBuilder = patronMetodoBuilder.matcher(contenido);
        Matcher matcherMetodoBuild = patronMetodoBuild.matcher(contenido);

        if (matcherMetodoBuilder.find() && matcherMetodoBuild.find()) {
            clase.setApplyBuildMethod(true);
        }

    }


}
