package com.unitTestGenerator.analyzers.dependency.interfaceanalyzer;


import com.unitTestGenerator.ioc.anotations.Component;

@Component
public class Relation {
    RelationType type;
    ClassNode node;

    Relation(RelationType type, ClassNode node) {
        this.type = type;
        this.node = node;
    }
}
