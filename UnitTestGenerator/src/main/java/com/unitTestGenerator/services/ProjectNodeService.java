package com.unitTestGenerator.services;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Node;
import com.unitTestGenerator.pojos.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectNodeService {

    public Project generateNodes(Project project){
        return this.fillnodeBase(project);
    }

    private Project fillnodeBase(Project project){
        for (int i = 0; i < project.getClaseList().size(); i++ ){
            Node classNode = project.getClaseList().get(i).getClassNode();
            classNode.setName(""+i);
            project.getNodeSources().put(classNode.getName(), project.getClaseList().get(i).getClassNode());
        }
            return this.fillUserForNodeList(project);
    }


     private Project fillUserForNodeList(Project project){

        Map<String, List<Clase>> nombreClaseMultimap = new HashMap<>();

        for (Clase clase : project.getClaseList()) {
            nombreClaseMultimap
                    .computeIfAbsent(clase.getNombre(), k -> new ArrayList<>())
                    .add(clase);
        }

        for (Clase classs : project.getClaseList()) {
            Node currentClassNode = classs.getClassNode();
            String currentClassName = currentClassNode.getClassName();
            List<String> currentClassNodeConnections = currentClassNode.getConextions();

            for (String connectedNombre : currentClassNodeConnections) {

                if (!currentClassName.equals(connectedNombre)) {
                    List<Clase> connectedClases = nombreClaseMultimap.get(connectedNombre);
                    if (connectedClases != null) {
                        for (Clase connectedClass : connectedClases) {
                            project.getNodeSources().get(currentClassNode.getName())
                                    .getUseFor()
                                    .add(connectedClass.getNombre());
                        }
                    }
                }
            }
        }
        return project;
    }


}
