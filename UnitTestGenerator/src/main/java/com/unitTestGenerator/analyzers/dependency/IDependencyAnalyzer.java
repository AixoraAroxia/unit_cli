package com.unitTestGenerator.analyzers.dependency;



import com.unitTestGenerator.analyzers.dependency.interfaceanalyzer.ClassNode;
import com.unitTestGenerator.analyzers.dependency.interfaceanalyzer.ClassRelationAnalyzer;

import com.unitTestGenerator.ioc.ContextIOC;


import java.util.List;

public interface IDependencyAnalyzer {


    default String interfaceRelations(List<String> classSources, String targetClassName) {
        ClassRelationAnalyzer analyzer = ContextIOC.getInstance().getClassInstance(ClassRelationAnalyzer.class);
        analyzer.analyzeClasses(classSources);
        ClassNode tree = analyzer.buildTree(targetClassName);
        String treeAsString = analyzer.buildTreeString(tree);
        return treeAsString;
    }

}
