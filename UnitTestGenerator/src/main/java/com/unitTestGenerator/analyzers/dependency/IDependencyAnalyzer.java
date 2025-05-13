package com.unitTestGenerator.analyzers.dependency;



import com.unitTestGenerator.App;
import com.unitTestGenerator.analyzers.dependency.console.DependencyAnalyzer;
import com.unitTestGenerator.analyzers.dependency.interfaceanalyzer.ClassNode;
import com.unitTestGenerator.analyzers.dependency.interfaceanalyzer.ClassRelationAnalyzer;
import com.unitTestGenerator.analyzers.dependency.parser.DependencyAnalyzerParser;
import com.unitTestGenerator.analyzers.dependency.parser.DependencyAnalyzerParserUml;
import com.unitTestGenerator.ioc.ContextIOC;

import java.io.IOException;
import java.util.List;

public interface IDependencyAnalyzer {


    default void generateUmlDiagram(String outputPath, List<String> classSources) throws IOException {
        DependencyAnalyzerParserUml parser = ContextIOC.getInstance().getClassInstance(DependencyAnalyzerParserUml.class);
        parser.analyze(classSources);
        String outputDotPath = outputPath + ".dot";
        String outputPngPath = outputPath + ".png";
        parser.generateUMLClassDiagram(outputDotPath, outputPngPath);
        System.out.println(outputDotPath);
        System.out.println(outputPngPath);
    }


    default void generateDependencyDotPng(String outputPath, List<String> classSources) throws IOException {
        DependencyAnalyzerParser analyzer = ContextIOC.getInstance().getClassInstance(DependencyAnalyzerParser.class);
        analyzer.analyze(classSources);
        String outputDotPath = outputPath + ".dot";
        String outputPngPath = outputPath + ".png";
        // Generar archivos de salida
        analyzer.generateDotAndPng(outputDotPath, outputPngPath);
        System.out.println(outputDotPath);
        System.out.println(outputPngPath);
    }


    default void generateDependencyConsole(List<String> classSources, String targetClassName) {
        DependencyAnalyzer analyzer = ContextIOC.getInstance().getClassInstance(DependencyAnalyzer.class);
        analyzer.analyzeDependencies(classSources, targetClassName);
        analyzer.printDependencyTree();
    }

    default String interfaceRelations(List<String> classSources, String targetClassName) {
        ClassRelationAnalyzer analyzer = ContextIOC.getInstance().getClassInstance(ClassRelationAnalyzer.class);
        analyzer.analyzeClasses(classSources);
        ClassNode tree = analyzer.buildTree(targetClassName);
        String treeAsString = analyzer.buildTreeString(tree);
        return treeAsString;
    }


}
