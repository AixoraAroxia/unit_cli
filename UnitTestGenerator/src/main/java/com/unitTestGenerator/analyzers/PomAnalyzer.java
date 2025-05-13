package com.unitTestGenerator.analyzers;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Dependency;
import com.unitTestGenerator.util.Dependencies;
import com.unitTestGenerator.util.interfaces.IConstantModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@Component
@Singleton
public class PomAnalyzer implements IConstantModel {


    public PomAnalyzer(){
    }


    private Boolean addTestDependency(Document documento) {
        Boolean existDependency = false;
        try {
            if (!existeDependencia(documento, this.JUNIT_DEPENDENCY)) {
                agregarDependencia(documento, Dependencies.JUNIT_DEPENDENCY);
                existDependency = true;
            }
            if (!existeDependencia(documento, this.MOCK_DEPENDENCY)) {
                existDependency = true;
                agregarDependencia(documento, Dependencies.MOCK_DEPENDENCY);
            }
            if (!existeDependencia(documento, this.MOCK_DEPENDENCY_core)) {
                existDependency = true;
                agregarDependencia(documento, Dependencies.MOCK_DEPENDENCY_core);
            }
            return existDependency;
        } catch (Exception e) {
            System.out.println("Error add Test Dependency pom.xml: " + e.getMessage());
            return false;
        }
    }

    private Boolean addTestingDatabaseDependency(Document documento) {
        Boolean existDependency = false;
        try {
            if (!existeDependencia(documento, this.H2_DEPENDENCY_TEST)) {
                agregarDependencia(documento, Dependencies.H2_DEPENDENCY_TEST);
                existDependency = true;
            }
            return existDependency;
        } catch (Exception e) {
            System.out.println("Error add testing database Dependency pom.xml: " + e.getMessage());
            return false;
        }
    }

    private Boolean addLombokDependency(Document documento) {
        Boolean existDependency = false;
        try {
            if (!existeDependencia(documento, this.LOMBOK_DEPENDENCY)) {
                agregarDependencia(documento, Dependencies.LOMBOK_DEPENDENCY);
                existDependency = true;
            }
            return existDependency;
        } catch (Exception e) {
            System.out.println("Error add testing database Dependency pom.xml: " + e.getMessage());
            return false;
        }
    }


    public void addDependencys(String rutaProyecto, Integer typeDependency) {

        boolean existeDependency = false;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(rutaProyecto + "/pom.xml"));

            Element raiz = documento.getDocumentElement();
            NodeList dependencies = raiz.getElementsByTagName("dependencies");

            if (dependencies.getLength() == 0) {
                Element dependenciesElement = documento.createElement("dependencies");
                raiz.appendChild(dependenciesElement);
            }

            existeDependency = typeDependency == 1? addTestDependency(documento): typeDependency == 0? addTestingDatabaseDependency(documento):addLombokDependency(documento) ;

            if (existeDependency) {
                saveChanges(documento, rutaProyecto);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error al analizar el archivo pom.xml: " + e.getMessage());
        }
    }

    public Boolean existeDependencia(Document documento, String dependencia) {
        NodeList dependencies = documento.getElementsByTagName("dependency");

        for (int i = 0; i < dependencies.getLength(); i++) {
            Element dependency = (Element) dependencies.item(i);

            if (dependency != null) {
                NodeList groupIdElements = dependency.getElementsByTagName("groupId");
                NodeList artifactIdElements = dependency.getElementsByTagName("artifactId");

                // groupId y artifactId existen
                if (groupIdElements.getLength() > 0 && artifactIdElements.getLength() > 0) {
                    String groupId = groupIdElements.item(0).getTextContent();
                    String artifactId = artifactIdElements.item(0).getTextContent();

                    if (groupId != null && artifactId != null) {
                        if (groupId.equals(dependencia.split("<groupId>")[1].split("</groupId>")[0]) &&
                                artifactId.equals(dependencia.split("<artifactId>")[1].split("</artifactId>")[0])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private  void agregarDependencia(Document documento, Dependency dependency) {

        Element dependencies = (Element) documento.getElementsByTagName("dependencies").item(0);
        Element nuevaDependencia = documento.createElement("dependency");

        Element grupoId = documento.createElement("groupId");
        grupoId.setTextContent(dependency.getGroupId());
        nuevaDependencia.appendChild(grupoId);

        Element artifactIdElement = documento.createElement("artifactId");
        artifactIdElement.setTextContent(dependency.getArtifactId());
        nuevaDependencia.appendChild(artifactIdElement);

        Element versionElement = documento.createElement("version");
        versionElement.setTextContent(dependency.getVersion());
        nuevaDependencia.appendChild(versionElement);

        Element scopeElement = documento.createElement("scope");
        scopeElement.setTextContent(dependency.getScope());
        nuevaDependencia.appendChild(scopeElement);

        dependencies.appendChild(nuevaDependencia);
    }



    private  void saveChanges(Document documento, String projectPath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File(projectPath + "/pom.xml"));
            transformer.transform(source, result);
            System.out.println("Dependencias agregadas correctamente.");
        } catch (TransformerException e) {
            System.out.println("Error al guardar cambios en el archivo pom.xml: " + e.getMessage());
        }
    }
}
