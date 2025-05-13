package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassRelations {

    private String className;
    private String classExtends;
    private String classType;
    private List<String> implementsList  = new ArrayList<>();
    private List<String> identifieresRelatedClasses = new ArrayList<>();
    private List<String> dependencyInjectionIoC = new ArrayList<>();
    private List<String> strongDependencyAssociation = new ArrayList<>();

    public ClassRelations() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getImplementsList() {
        return implementsList;
    }

    public void setImplementsList(List<String> implementsList) {
        if(implementsList != null && !implementsList.isEmpty()){
            this.implementsList = implementsList;
        }
    }

    public List<String> getDependencyInjectionIoC() {
        return dependencyInjectionIoC;
    }

    public void setDependencyInjectionIoC(List<String> dependencyInjectionIoC) {
        this.dependencyInjectionIoC = dependencyInjectionIoC;
    }

    public String getClassExtends() {
        return classExtends;
    }

    public void setClassExtends(String classExtends) {
        if(classExtends != null){
            this.classExtends = classExtends;
        }
    }

    public Boolean check(){

Boolean flat = false;
        if(this.implementsList != null && !this.implementsList.isEmpty()) {
            flat = true;
        }

        if(this.classExtends != null && !this.classExtends.isEmpty()) {
            flat = true;
        }

        if(this.identifieresRelatedClasses != null && !this.identifieresRelatedClasses.isEmpty()) {
            flat = true;
        }

        if(this.dependencyInjectionIoC != null && !this.dependencyInjectionIoC.isEmpty()) {
            flat = true;
        }

        if(this.strongDependencyAssociation != null && !this.strongDependencyAssociation.isEmpty()) {
            flat = true;
        }

//        if(
//                (this.implementsList != null && !this.implementsList.isEmpty()) ||
//                (this.classExtends != null && !this.classExtends.isEmpty())||
//                (this.identifieresRelatedClasses != null && !this.identifieresRelatedClasses.isEmpty()) ||
//                (this.dependencyInjectionIoC != null && !this.dependencyInjectionIoC.isEmpty()) ||
//                (this.strongDependencyAssociation != null && !this.strongDependencyAssociation.isEmpty())
//        ){
//            return true;
//        }else {
//            return false;
//        }

        return flat;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public List<String> getIdentifieresRelatedClasses() {
        return identifieresRelatedClasses;
    }

    public void setIdentifieresRelatedClasses(List<String> identifieresRelatedClasses) {
        this.identifieresRelatedClasses = identifieresRelatedClasses;
    }

    public List<String> getStrongDependencyAssociation() {
        return strongDependencyAssociation;
    }

    public void setStrongDependencyAssociation(List<String> strongDependencyAssociation) {
        this.strongDependencyAssociation = strongDependencyAssociation;
    }

    public void addIdentifieres(String identifiere) {
        this.identifieresRelatedClasses.add(identifiere);
    }
    public void addAllIdentifieres(List<String>  identifieres) {
        this.identifieresRelatedClasses.addAll(identifieres);
    }

    public void addDependencyInjectionIoC(String iDIoC ) {
        this.dependencyInjectionIoC.add(iDIoC);
    }
    public void addAllDependencyInjectionIoC(List<String>  iDIoCs ) {
        this.dependencyInjectionIoC.addAll(iDIoCs);
    }

    public void addStrongDependencyAssociation(String strongDependency) {
        this.strongDependencyAssociation.add(strongDependency);
    }

    public void addAllStrongDependencyAssociation(List<String> strongDependencys) {
        this.strongDependencyAssociation.addAll(strongDependencys);
    }

    public static Builder builder() {
        return new Builder();
    }

    public interface ClassRelationesBuilder {
        public Builder className(String className);
        public Builder implementsList(List<String> implementsList);
        public Builder classExtends(String classExtends);
        public Builder classType(String classType);

        public ClassRelations build();
    }

    public static class Builder implements ClassRelationesBuilder {
        private String className;
        private List<String> implementsList;
        private String classExtends;
        private String classType;

        @Override
        public Builder className(String className) {
            this.className = className;
            return this;
        }

        @Override
        public Builder implementsList(List<String> implementsList) {
            this.implementsList = implementsList;
            return this;
        }

        @Override
        public Builder classExtends(String classExtends) {
            this.classExtends = classExtends;
            return this;
        }

        @Override
        public Builder classType(String classType) {
            this.classType = classType;
            return this;
        }

        @Override
        public ClassRelations build() {
            ClassRelations classrelationes = new ClassRelations();
            classrelationes.setClassName(this.className);
            classrelationes.setImplementsList(this.implementsList);
            classrelationes.setClassExtends(this.classExtends);
            classrelationes.setClassType(this.classType);
            return classrelationes;
        }
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        if (this.classExtends != null && !this.classExtends.isEmpty()) {
            buffer.append("Extends: ").append(" \n");
            buffer.append("- " + this.classExtends + "\n");
        }

        if (this.implementsList != null && !this.implementsList.isEmpty()) {
            buffer.append("Implements: ").append(" \n");
            for (String classImplement : this.implementsList) {
                buffer.append("- " + classImplement + " \n");
            }
        }

        if (this.dependencyInjectionIoC != null && !this.dependencyInjectionIoC.isEmpty()) {
            buffer.append("Dependency Injection (IoC): ").append(" \n");
            for (String element : this.dependencyInjectionIoC) {
                buffer.append("- " + element + " \n");
            }
        }

        if (this.strongDependencyAssociation != null && !this.strongDependencyAssociation.isEmpty()) {
            buffer.append("Strong Dependency Association Class: ").append(" \n");
            for (String element : this.strongDependencyAssociation) {
                buffer.append("- " + element + "\n");
            }
        }

        if (this.strongDependencyAssociation != null && !this.strongDependencyAssociation.isEmpty()) {
            buffer.append("Strong Dependency Association Class (Composition): ").append(" \n");
            for (String element : this.strongDependencyAssociation) {
                buffer.append("- " + element + "\n");
            }
        }

        if (this.identifieresRelatedClasses != null && !this.identifieresRelatedClasses.isEmpty()) {
            buffer.append("Association Class (static or Sigleton): ").append(" \n");
            for (String element : this.identifieresRelatedClasses) {
                buffer.append("- " + element + "\n");
            }
        }
        return buffer.toString();
    }
}
