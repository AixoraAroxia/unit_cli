package com.unitTestGenerator.analyzers.dependency.parser;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.util.interfaces.DataTIme;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

@Component
public class DependencyAnalyzerParserUml implements DataTIme {
    private final Map<String, Set<String>> dependencyMap = new HashMap<>();
    private final Map<String, Set<String>> reverseDependencyMap = new HashMap<>();
    private final Map<String, List<String>> fieldsMap = new HashMap<>();
    private final Map<String, List<String>> methodsMap = new HashMap<>();




    public void analyze(List<String> classSources) {
        JavaParser parser = new JavaParser();


        for (String source : classSources) {

            parser.parse(source).ifSuccessful(cu -> {
                cu.findFirst(ClassOrInterfaceDeclaration.class).ifPresent(classDecl -> {
                    String className = classDecl.getNameAsString();
                    dependencyMap.putIfAbsent(className, new HashSet<>());
                    fieldsMap.putIfAbsent(className, new ArrayList<>());
                    methodsMap.putIfAbsent(className, new ArrayList<>());

                    classDecl.getExtendedTypes().forEach(t -> addDependency(className, t.getNameAsString()));
                    classDecl.getImplementedTypes().forEach(t -> addDependency(className, t.getNameAsString()));


                    for (FieldDeclaration field : classDecl.getFields()) {
                        String type = field.getElementType().asString();
                        for (VariableDeclarator var : field.getVariables()) {
                            String name = var.getNameAsString();
                            fieldsMap.get(className).add(type + " " + name);
                            addDependency(className, type);
                        }
                    }


                    for (MethodDeclaration method : classDecl.getMethods()) {
                        String sig = method.getType().asString() + " "
                                + method.getNameAsString()
                                + "("
                                + method.getParameters().stream()
                                .map(p -> p.getType() + " " + p.getName())
                                .collect(Collectors.joining(", "))
                                + ")";
                        methodsMap.get(className).add(sig);


                        method.findAll(ObjectCreationExpr.class)
                                .forEach(e -> addDependency(className, e.getType().getNameAsString()));
                        method.findAll(MethodCallExpr.class).forEach(expr ->
                                expr.getScope().ifPresent(s -> addDependency(className, s.toString()))
                        );
                    }
                });
            });
        }
    }

    private void addDependency(String from, String to) {
        if (!from.equals(to)) {
            dependencyMap.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            reverseDependencyMap.computeIfAbsent(to, k -> new HashSet<>()).add(from);
        }
    }

    public void generateDotAndPng(String outputDotPath, String outputPngPath) throws IOException {
        Graph g = graph("dependencies").directed();
        Map<String, Node> nodes = new HashMap<>();

        for (String className : dependencyMap.keySet()) {
            Node node = node(className).with(Label.html("<b>" + className + "</b>"));
            nodes.put(className, node);
        }

        Graph finalGraph = g;
        for (Map.Entry<String, Set<String>> entry : dependencyMap.entrySet()) {
            String from = entry.getKey();
            for (String to : entry.getValue()) {
                Node fromNode = nodes.get(from);
                Node toNode = nodes.computeIfAbsent(to, n -> node(n).with(Label.html("<b>" + n + "</b>")));
                finalGraph = finalGraph.with(fromNode.link(toNode));
            }
        }

        for (Map.Entry<String, Set<String>> entry : reverseDependencyMap.entrySet()) {
            String to = entry.getKey();
            for (String from : entry.getValue()) {
                Node toNode = nodes.get(to);
                Node fromNode = nodes.get(from);
                if (toNode != null && fromNode != null) {
                    finalGraph = finalGraph.with(toNode.link(fromNode));
                }
            }
        }

        File dotFile = new File(outputDotPath);
        try (FileWriter writer = new FileWriter(dotFile)) {
            writer.write(finalGraph.toString());
        }

        Graphviz.fromGraph(finalGraph).render(Format.PNG).toFile(new File(outputPngPath));
    }



//    private static String escapeDotField(String s) {
//        return s
//                .replace("\\", "\\\\") // doble barra
//                .replace("\"", "'")    // cambia comillas dobles por simples
//                .replace("{", "")
//                .replace("}", "")
//                .replace("<", "")
//                .replace(">", "")
//                .replace("|", "")
//                .replace("\n", " ")
//                .replace("\r", " ")
//                .replace("\t", "    "); // opcional: convierte tabulador a espacios
//    }



//    private static String escapeForDot(String text) {
//
////        return text.replace("\n","");
////        return text.replace("<", "&lt;")
////                .replace(">", "&gt;").replace(".", "_").replace("\n","");
//
//        return text.replaceAll("\\\\", "").replace("<", "&lt;")
//                .replace(">", "&gt;").replace(".", "_")
//                .replace("{", "\\{").replace("\n","")
//                .replace("}", "\\}").replace("\"","'");
//    }

//    private static String escapeForDot(String text) {
//        return text.replace("\\", "\\\\")  // Escapa barras invertidas
//                .replace("{", "\\{")
//                .replace("}", "\\}")
//                .replace("<", "\\<")
//                .replace(">", "\\>")
//                .replace("\"", "\\\"")
//                .replace("|", "\\|")
//                .replace(".", "\\.")  // Escapa puntos
//                .replace("\n", " ");
//    }

    private static String escapeDotField(String s) {
        return s
                .replace("\\", "\\\\")   // Escapa barras invertidas
                .replace("\"", "'")       // Reemplaza comillas dobles por simples
                .replace("{", "\\{")      // Escapa llaves
                .replace("}", "\\}")
                .replace("<", "\\<")      // Escapa símbolos <
                .replace(">", "\\>")      // Escapa símbolos >
                .replace("|", "\\|")      // Escapa |
                .replace("\n", "\\l");    // Nueva línea en Graphviz
    }

    private static String escapeForDot(String text) {
        return text.replace("\"", "\\\"");  // Solo escapa comillas dobles
    }

    public String formatLabel(List<String> labels) {
        StringBuilder result = new StringBuilder();
        for (String method : labels) {
            String temp = escapeForDot(method);
            String escaped = DependencyAnalyzerParserUml.escapeDotField(temp);
            if (!escaped.trim().isEmpty()) {
                result.append(escaped).append("\\l");
            }
        }
        return result.toString();
    }

    public void generateUMLClassDiagram(String outputDotPath, String outputPngPath) throws IOException {


        String title = "UML_Class_diagram of " + "Project_Name" + this.getTime()+"\n\n";

        Graph g = graph("UML_Class_diagram").directed()
                .graphAttr().with(
                        attr("label", title),
                        attr("labelloc", "t"),
                        attr("fontname", "Helvetica,Arial,sans-serif")
                )
                .nodeAttr().with(
                        attr("fontname", "Helvetica,Arial,sans-serif"),
                        attr("shape", "record"),
                        attr("style", "filled"),
                        attr("fillcolor", "gray95")
                );

        Map<String, Node> nodes = new HashMap<>();

        for (String className : dependencyMap.keySet()) {

            List<String> fields = fieldsMap.getOrDefault(className, Collections.emptyList());
            List<String> methods = methodsMap.getOrDefault(className, Collections.emptyList());


            String fieldBlock = formatLabel(fields);
            if (!fieldBlock.isEmpty()) fieldBlock += "\\l";

            String methodBlock = formatLabel(methods);
            if (!methodBlock.isEmpty()) methodBlock += "\\l";



            // Dentro del for que itera dependencyMap.keySet():
            String escapedClassName = escapeForDot(className);
            String quotedNodeName = "\"" + escapedClassName + "\""; // Nodos entre comillas

            String label = "{" + escapedClassName + "|" + fieldBlock + "|" + methodBlock + "}";

// Crear el nodo con el nombre escapado y entrecomillado
            Node n = node(quotedNodeName).with(attr("label", label));
            nodes.put(className, n);


            // En generateUMLClassDiagram:
//            String escapedClassName = escapeForDot(className);
//            String label = "{" + escapedClassName + "|" + fieldBlock + "|" + methodBlock + "}";

            // Sintaxis record: {ClassName|fields|methods}
//            String label = "{" + className
//                    + "|" + fieldBlock
//                    + "|" + methodBlock
//                    + "}";

//            String quotedClassName = "\"" + className + "\"";
//            Node n = node(quotedClassName).with(attr("label", label));


//            String quotedName = className.contains(".") ? "\"" + className + "\"" : className;
//            Node n = node(quotedName).with(attr("label", label));
//            nodes.put(className, n);

//            Node n = node(className).with(attr("label", label));
//            nodes.put(className, n);
            g = g.with(n);
        }

        // Relaciones igual que antes…
//        for (Map.Entry<String, Set<String>> entry : dependencyMap.entrySet()) {
//            String from = entry.getKey();
//            for (String to : entry.getValue()) {
//                Node fromNode = nodes.get(from);

                //   Node toNode = nodes.computeIfAbsent(to, k -> {
//                    String lbl = "{" + k + "}";
//                    return node(k).with(attr("label", lbl));
//                });

//                String quotedTo = to.contains(".") ? "\"" + to + "\"" : to;
//                Node toNode = nodes.computeIfAbsent(quotedTo, k -> {
//                    String lbl = "{" + escapeForDot(to) + "}";
//                    return node(quotedTo).with(attr("label", lbl));
//                });
//                g = g.with(fromNode.link(toNode));

//                Node toNode = nodes.computeIfAbsent(to, k -> {
//                    String escapedK = escapeForDot(k);
//                    String lbl = "{" + escapedK + "}";
//                    return node("\"" + k + "\"").with(attr("label", lbl)); // Nodo entrecomillado
//                });
//                g = g.with(fromNode.link(toNode));
//            }
//        }


        for (Map.Entry<String, Set<String>> entry : dependencyMap.entrySet()) {
            String from = entry.getKey();
            for (String to : entry.getValue()) {
                // Escapar y quotar nombres
                String quotedFrom = "\"" + escapeForDot(from) + "\"";
                String quotedTo = "\"" + escapeForDot(to) + "\"";

                // Obtener o crear nodos
                Node fromNode = nodes.get(quotedFrom);
                Node toNode = nodes.computeIfAbsent(quotedTo, k -> {
                    String lbl = "{" + escapeForDot(to) + "}";
                    return node(quotedTo).with(attr("label", lbl));
                });

                if (fromNode != null) {
                    g = g.with(fromNode.link(toNode));
                } else {
                    System.err.println("Advertencia: Nodo '" + quotedFrom + "' no encontrado.");
                }
            }
        }

        // Exportar .dot y .png (igual que antes)
        try (FileWriter w = new FileWriter(outputDotPath)) {
            w.write(g.toString());
            System.out.println("DOT generado:\n\n" + g.toString()); // <-- para depurar
        }
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputPngPath));
    }

}


