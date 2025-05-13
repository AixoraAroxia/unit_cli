package com.unitTestGenerator.analyzers.dependency.interfaceanalyzer;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassNode {
    String className;
    ClassNode(String className) {
        this.className = className;
    }

    List<Relation> children = new ArrayList<>();

}


