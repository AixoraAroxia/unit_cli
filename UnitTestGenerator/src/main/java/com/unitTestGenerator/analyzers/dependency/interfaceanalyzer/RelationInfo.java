package com.unitTestGenerator.analyzers.dependency.interfaceanalyzer;


import com.unitTestGenerator.ioc.anotations.Component;

@Component
public class RelationInfo {
    String childClassName;
    RelationType relationType;

    RelationInfo(String childClassName, RelationType relationType) {
        this.childClassName = childClassName;
        this.relationType = relationType;
    }
}
