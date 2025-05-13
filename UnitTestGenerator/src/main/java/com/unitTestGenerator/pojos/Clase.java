package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Clase {

    private String nombre;
    private String paquete;
    private String typeClass;
    private String classSignatureLine;
    private String classPath;
    private String classUml;
    private List<Metodo> metodos;
    private List<Variable> variables;
    private List<EstructuraControl> estructuras;
    private Boolean useMock;
    private String testMethod;
    private List<Constructor> constructores;
    private String rawClass;
    private Boolean useLomboxBuild;
    private Boolean applyBuildMethod;
    private Integer indexFirmaClass;
    private ClassRelations classRelations = new ClassRelations();
    private String todoNoteInClass;
    private ImportAnalizePojo imports;
    private String structureInterface;
    private String structureExtends;
    private String classAnotations;
    private String classDetail;
    private String classTemplate;
    private Boolean isMainClass;
    private Node classNode;


    public Clase() {
        this.metodos = new ArrayList<>();
        this.variables = new ArrayList<>();
        this.estructuras = new ArrayList<>();
        this.classNode = new Node();
        this.useMock = false;
        this.constructores = new ArrayList<>();
        this.useLomboxBuild = false;
        this.applyBuildMethod = false;
        this.isMainClass = false;
    }

    public Node getClassNode() {
        return classNode;
    }

    public void setClassNode(Node classNode) {
        this.classNode = classNode;
    }

    public Boolean getMainClass() {
        return isMainClass;
    }

    public void setMainClass(Boolean mainClass) {
        isMainClass = mainClass;
    }

    public String getStructureExtends() {
        return structureExtends;
    }

    public void setStructureExtends(String structureExtends) {
        this.structureExtends = structureExtends;
    }

    public ImportAnalizePojo getImports() {
        return imports;
    }

    public String getClassTemplate() {
        return classTemplate;
    }

    public void setClassTemplate(String classTemplate) {
        this.classTemplate = classTemplate;
    }

    public void setImports(ImportAnalizePojo imports) {
        this.imports = imports;
    }

    public String getStructureInterface() {
        return structureInterface;
    }

    public void setStructureInterface(String structureInterface) {
        this.structureInterface = structureInterface;
    }

    public Boolean getApplyBuildMethod() {
        return applyBuildMethod;
    }

    public void setApplyBuildMethod(Boolean applyBuildMethod) {
        this.applyBuildMethod = applyBuildMethod;
    }

    public Boolean getUseLomboxBuild() {
        return useLomboxBuild;
    }

    public void setUseLomboxBuild(Boolean useLomboxBuild) {
        this.useLomboxBuild = useLomboxBuild;
    }

    public ClassRelations getClassRelations() {
        return classRelations;
    }

    public void setClassRelations(ClassRelations classRelations) {
        this.classRelations = classRelations;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getTodoNoteInClass() {
        return todoNoteInClass;
    }

    public void setTodoNoteInClass(String todoNoteInClass) {
        this.todoNoteInClass = todoNoteInClass;
    }

    public String getRawClass() {
        return rawClass;
    }

    public void setRawClass(String rawClass) {
        this.rawClass = rawClass;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public List<Constructor> getConstructores() {
        return constructores;
    }

    public void setConstructores(List<Constructor> constructores) {
        this.constructores = constructores;
    }

    public void addConstructor(Constructor constructor) {
        this.constructores.add(constructor);
    }

    public Boolean getUseMock() {
        return useMock;
    }

    public void setUseMock(Boolean useMock) {
        this.useMock = useMock;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Metodo> getMetodos() {
        return metodos;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public List<EstructuraControl> getEstructuras() {
        return estructuras;
    }

    public void addEstructura(EstructuraControl estructura) {
        this.estructuras.add(estructura);
    }

    public void addMetodo(Metodo metodo) {
        this.metodos.add(metodo);
    }

    public void addMetodos(List<Metodo> metodos) {
        this.metodos.addAll(metodos);
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public void addVariableS(List<Variable> variable) {
        this.variables.addAll(variable);
    }

    public Integer getIndexFirmaClass() {
        return indexFirmaClass;
    }

    public void setIndexFirmaClass(Integer indexFirmaClass) {
        this.indexFirmaClass = indexFirmaClass;
    }

    public String getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(String classDetail) {
        this.classDetail = classDetail;
    }

    public String getClassUml() {
        return classUml;
    }

    public void setClassUml(String classUml) {
        this.classUml = classUml;
    }

    public String getClassAnotations() {
        return classAnotations;
    }

    public void setClassAnotations(String classAnotations) {
        this.classAnotations = classAnotations;
    }

    public void addClassAnotation(String anotation){
        String newRawClass = this.addTxtBeforeClassSignature(this.rawClass, this.indexFirmaClass, anotation);
        this.setRawClass(newRawClass);
    }

    private String addTxtBeforeClassSignature(String content, Integer indexManeClass, String text) {
        int indiceFinLinea = content.lastIndexOf("\n", indexManeClass);
        if (indiceFinLinea != -1) {
            String contenidoAntesDeNombreClase = content.substring(0, indiceFinLinea + 1);
            String contenidoDespuesDeNombreClase = content.substring(indiceFinLinea + 1);
            return contenidoAntesDeNombreClase + text + "\n" + contenidoDespuesDeNombreClase;
        }
        return content;
    }

//    public Variable getVariable(String type){
//        Variable var = null;
//        try {
//            var = this.variables.stream().filter( variable -> variable.getTipo().equals(type)).findFirst().get();
//        }catch (Exception e){
//            e.printStackTrace();
//            return var;
//        }
//        return var;
//    }

    public Variable getVariable(String type) {

        if (this.variables == null || this.variables.isEmpty()) {
            return null;
        }

        for (Variable v : this.variables) {
            if (v != null && Objects.equals(v.getTipo(), type)) {
                return v;
            }
        }
        return null;
    }



    public boolean checkAnotation(List<String> iocAnotations) {
        if (this.classAnotations == null || this.classAnotations.isEmpty()) {
            return false;
        }
        Set<String> anotations = new HashSet<>(Arrays.asList(this.classAnotations.trim().split(",")));
        for (String iocAnotation : iocAnotations) {
            if (anotations.contains(iocAnotation)) {
                return true;
            }
        }
        return false;
    }

    public void updateNode(String nodeName) {
        this.classNode.setClassName(this.nombre);
        if (nodeName != null) {
            this.classNode.setName(nodeName);
        }
        if (this.imports != null && this.imports.getProjectImports() != null && !this.imports.getProjectImports().isEmpty()) {
            this.classNode.setConextions(this.imports.getProjectImports());
        }
    }

    public Node updateNodeAndGet(String nodeName) {
        this.updateNode(nodeName);
        return this.classNode;
    }


    public String getClassSignatureLine() {
        return classSignatureLine;
    }

    public void setClassSignatureLine(String classSignatureLine) {
        this.classSignatureLine = classSignatureLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase clase = (Clase) o;
        return Objects.equals(nombre, clase.nombre) && Objects.equals(paquete, clase.paquete) && Objects.equals(metodos, clase.metodos) && Objects.equals(variables, clase.variables) && Objects.equals(estructuras, clase.estructuras) && Objects.equals(useMock, clase.useMock) && Objects.equals(testMethod, clase.testMethod) && Objects.equals(constructores, clase.constructores) && Objects.equals(typeClass, clase.typeClass) && Objects.equals(rawClass, clase.rawClass) && Objects.equals(useLomboxBuild, clase.useLomboxBuild) && Objects.equals(applyBuildMethod, clase.applyBuildMethod) && Objects.equals(indexFirmaClass, clase.indexFirmaClass) && Objects.equals(classSignatureLine, clase.classSignatureLine) && Objects.equals(classRelations, clase.classRelations) && Objects.equals(classPath, clase.classPath) && Objects.equals(todoNoteInClass, clase.todoNoteInClass) && Objects.equals(imports, clase.imports) && Objects.equals(structureInterface, clase.structureInterface) && Objects.equals(structureExtends, clase.structureExtends) && Objects.equals(classAnotations, clase.classAnotations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, paquete, metodos, variables, estructuras, useMock, testMethod, constructores, typeClass, rawClass, useLomboxBuild, applyBuildMethod, indexFirmaClass, classSignatureLine, classRelations, classPath, todoNoteInClass, imports, structureInterface, structureExtends, classAnotations);
    }

    @Override
    public String toString() {
        return "Clase{" +
                "nombre='" + nombre + '\'' +
                ", paquete='" + paquete + '\'' +
                ", metodos=" + metodos +
                ", variables=" + variables +
                ", estructuras=" + estructuras +
                ", useMock=" + useMock +
                ", testMethod='" + testMethod + '\'' +
                ", constructores=" + constructores +
                ", typeClass='" + typeClass + '\'' +
                ", rawClass='" + rawClass + '\'' +
                ", useLomboxBuild=" + useLomboxBuild +
                ", applyBuildMethod=" + applyBuildMethod +
                ", indexFirmaClass=" + indexFirmaClass +
                ", classSignatureLine='" + classSignatureLine + '\'' +
                ", classRelations=" + classRelations +
                ", classPath='" + classPath + '\'' +
                ", todoNoteInClass='" + todoNoteInClass + '\'' +
                ", imports=" + imports +
                ", structureInterface='" + structureInterface + '\'' +
                ", structureExtends='" + structureExtends + '\'' +
                ", classAnotations='" + classAnotations + '\'' +
                ", classUml='" + classUml + '\'' +
                '}';



    }
}