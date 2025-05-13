package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.io.File;
import java.util.*;

@Component
public class Project {

    private List<Clase> claseList = new ArrayList<>();
    private Map<String, Clase> mapClass = new HashMap<>();
    private Boolean isMaven;
    private Boolean isGradle;
    private String pathProject;
    private  PrinterProject printerProject= new PrinterProject();
    private String name;
    private String description;
    private String mainClassName;
    private List<String> claseListRaw = new ArrayList<>();
    private Map<String, Node> nodeSources = new HashMap<>();


    public Project() {
    }

    public Project(List<Clase> claseList) {
        this.claseList = claseList;
    }

    public Project(String pathProject) {
        this.pathProject = pathProject;
        this.projectAnalyzerType(pathProject);
    }

//    public Project(Map<String, Clase> mapClass, String pathProject) {
//        this.mapClass = mapClass;
//        this.pathProject = pathProject;
//        this.projectAnalyzerType(pathProject);
//    }

    public Project(List<Clase> claseList, String pathProject) {
        this.claseList = claseList;
        this.pathProject = pathProject;
        this.projectAnalyzerType(pathProject);
    }

    public Project(List<Clase> claseList, Boolean isMaven, Boolean isGradle) {
        this.claseList = claseList;
        this.isMaven = isMaven;
        this.isGradle = isGradle;
    }

    public List<String> getClaseListRaw() {
        return claseListRaw;
    }

    public void setClaseListRaw(List<String> claseListRaw) {
        this.claseListRaw = claseListRaw;
    }

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public List<Clase> getClaseList() {
        return claseList;
    }

    public void setClaseList(List<Clase> claseList) {
        this.claseList = claseList;
    }


    public Clase getClass(String className){
        return getClassI(className);
    }


    public Map<String, Node> getNodeSources() {
        return nodeSources;
    }

    public void setNodeSources(Map<String, Node> nodeSources) {
        this.nodeSources = nodeSources;
    }

    private Clase getClassI(String className) {
        Clase foundClass = null;
        if (this.claseList != null && !this.claseList.isEmpty() && className != null && !className.equals("")) {
            for (Clase classs : this.claseList) {
                if (classs.getNombre().trim().toLowerCase().equals(className.trim().toLowerCase())) {
                    return classs;
                }
            }
        }
        return foundClass;
    }

    public List<String> getRawClassList(){
        if(this.claseListRaw.isEmpty()){
            for(Clase classs : this.claseList){
                if(classs.getRawClass() != null && !classs.getRawClass().isEmpty() && !classs.getRawClass().equals("")){
                    this.claseListRaw.add(classs.getRawClass());
                }
            }
        }
        return this.claseListRaw;
    }


    public void fillnodeBase(){
        for (int i = 0; i < this.claseList.size(); i++ ){
            String key = ""+i;
            Node classNode = this.claseList.get(i).getClassNode();
            classNode.setName(key);
            this.nodeSources.put(key, this.claseList.get(i).getClassNode());
        }


    }



//Complejidad: O(N + C) donde C = total de conexiones en todos los nodos.
    public  void fillUserForNodeList(){

        Map<String, List<Clase>> nombreClaseMultimap = new HashMap<>(); // Mapa para manejar nombres duplicados

// Paso 1: Construir el mapa de nombres -> lista de Clases (maneja duplicados)
        for (Clase clase : this.claseList) {
            nombreClaseMultimap
                    .computeIfAbsent(clase.getNombre(), k -> new ArrayList<>())
                    .add(clase);
        }

// Paso 2: Procesar conexiones usando la clave única del Node
        for (Clase classs : this.claseList) {
            Node currentClassNode = classs.getClassNode();
            String currentClassName = currentClassNode.getClassName();
            List<String> currentClassNodeConnections = currentClassNode.getConextions();

            for (String connectedNombre : currentClassNodeConnections) {
                // Condición equivalente a la original: evitar auto-referencias por className
                if (!currentClassName.equals(connectedNombre)) {
                    List<Clase> connectedClases = nombreClaseMultimap.get(connectedNombre);
                    if (connectedClases != null) {
                        for (Clase connectedClass : connectedClases) {
                            // Acceso directo usando la clave única del Node actual
                            this.nodeSources.get(currentClassNode.getName())
                                    .getUseFor()
                                    .add(connectedClass.getNombre());
                        }
                    }
                }
            }
        }
    }


    public Map<String, Clase> getMapClass() {
        return mapClass;
    }

    public void setMapClass(Map<String, Clase> mapClass) {
        this.mapClass = mapClass;
    }

    public String getPathProject() {
        return pathProject;
    }

    public void setPathProject(String pathProject) {
        this.pathProject = pathProject;
    }

    public Boolean getMaven() {
        return isMaven;
    }

    public void setMaven(Boolean maven) {
        isMaven = maven;
    }

    public Boolean getGradle() {
        return isGradle;
    }

    public void setGradle(Boolean gradle) {
        isGradle = gradle;
    }

    public PrinterProject getPrinterProject() {
        return printerProject;
    }

    public void setPrinterProject(PrinterProject printerProject) {
        this.printerProject = printerProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void projectAnalyzerType(String pathProject) {
        File proyecto = new File(pathProject);
        boolean isMaven = new File(proyecto, "pom.xml").exists();
        this.isGradle = new File(proyecto, "build.gradle").exists() || new File(proyecto, "build.gradle.kts").exists();
        this.isMaven = isMaven;
    }


}
