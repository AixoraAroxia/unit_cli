package com.unitTestGenerator.analyzers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.unitTestGenerator.analyzers.interfaces.IGradleAnalyzer;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.pojos.Dependency;
import com.unitTestGenerator.util.Dependencies;
import com.unitTestGenerator.builders.interfaces.IFileManager;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
@Component
public class GradleAnalyzer implements IGradleAnalyzer, IFileManager {

    private File archivoGradle;

    public GradleAnalyzer(){
    }

    public GradleAnalyzer(File archivoGradle) {
        this.archivoGradle = archivoGradle;
    }

    public void setArchivoGradle(File archivoGradle) {
        this.archivoGradle = archivoGradle;
    }

    private String getImportType(Integer importType) {
        return importType == 1 ? "implementation" : "testImplementation";
    }


    public void addLombokDependency(){
        try {
            String contenido = FileUtils.readFileToString(archivoGradle, "UTF-8");
            List<String> contenidoList = FileUtils.readLines(archivoGradle, "UTF-8");
            List<String> dependencesList1 =  this.addDependences(contenidoList,  contenido,0, Dependencies.LOMBOK_DEPENDENCY);
            this.analizarEstructura();
            String newContenido =  this.listStringStructureToColummString(dependencesList1);
            this.writefilesI( archivoGradle,  newContenido);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error between add new dependency: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void startedWithoutMockDatabaseH2(){
        try {
            String contenido = FileUtils.readFileToString(archivoGradle, "UTF-8");
            List<String> contenidoList = FileUtils.readLines(archivoGradle, "UTF-8");
            List<String> dependencesList1 =  this.addDependences(contenidoList,  contenido,0, Dependencies.H2_DEPENDENCY_TEST);
            this.analizarEstructura();
            String newContenido =  this.listStringStructureToColummString(dependencesList1);
            this.writefilesI( archivoGradle,  newContenido);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startedMokitoDependencys(){
        try {
            String contenido = FileUtils.readFileToString(archivoGradle, "UTF-8");
            List<String> contenidoList = FileUtils.readLines(archivoGradle, "UTF-8");

            List<String> dependencesList1 =  this.addDependences(contenidoList, contenido,1, Dependencies.JUNIT_DEPENDENCY);
            List<String> dependencesList3  =  this.addDependences(dependencesList1,  contenido,1, Dependencies.MOCK_DEPENDENCY);
            List<String> dependencesList4  =  this.addDependences(dependencesList3,contenido,1, Dependencies.MOCK_DEPENDENCY_core);
            this.analizarEstructura();
            String newContenido =  this.listStringStructureToColummString(dependencesList4);
            this.writefilesI( archivoGradle,  newContenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean dependenciaExiste(String contenido , Integer importType, Dependency dependency) {
        try {
            String dependencia = String.format("%s '%s:%s:%s'", this.getImportType(importType), dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
            Boolean isPresent = contenido.contains(dependencia);
            return isPresent;
        } catch (Exception e) {
            System.out.println("Error al leer contenido: " + e.getMessage());
            return false;
        }
    }


    public  List<String> addDependences(  List<String> contenidoList,  String contenido, Integer importType, Dependency dependency) {
        try {
            if(contenido != null && !contenido.equals("") && contenidoList != null &&
                    !contenidoList.isEmpty() && !dependenciaExiste( contenido, importType, dependency)){

                String newDependencie = String.format("%s '%s:%s:%s'", this.getImportType(importType),dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());

                Optional<Integer> indice = contenidoList.stream()
                        .map(String::toLowerCase)
                        .map(line -> line.contains("dependencies {") ? line : null)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .map(contenidoList::indexOf);

                String newDependencieQ = stringEnsamble(indentation(1), newDependencie);
                indice.ifPresent(i -> contenidoList.add(i + 1, newDependencieQ));
            }
        } catch (Exception e) {
            System.out.println("Error al agregar dependencia: " + e.getMessage());
        }
        return contenidoList;
    }



    public void analizarEstructura() {
        try {
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivoGradle);
            Element raiz = documento.getDocumentElement();
            NodeList dependencias = raiz.getElementsByTagName("dependency");
            System.out.println("Dependencias:");

            for (int i = 0; i < dependencias.getLength(); i++) {
                Element dependencia = (Element) dependencias.item(i);
                System.out.println(dependencia.getAttribute("groupId") + ":" +
                        dependencia.getAttribute("artifactId") + ":" +
                        dependencia.getAttribute("version"));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error al analizar estructura: " + e.getMessage());
        }
    }


    public void eliminarDependencia(String grupo, String artefacto) {
        try {
            String contenido = FileUtils.readFileToString(archivoGradle, "UTF-8");
            contenido = contenido.replaceAll("implementation '$grupo:$artefacto:[^']+'", "");
            FileWriter escritor = new FileWriter(archivoGradle);
            escritor.write(contenido);
            escritor.close();
            System.out.println("Dependencia eliminada con éxito");
        } catch (IOException e) {
            System.out.println("Error al eliminar dependencia: " + e.getMessage());
        }
    }

}
