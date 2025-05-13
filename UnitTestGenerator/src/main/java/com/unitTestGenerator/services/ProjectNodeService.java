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


    //Complexity: O(N + C) where C = total connections on all nodes.
     private Project fillUserForNodeList(Project project){

        Map<String, List<Clase>> nombreClaseMultimap = new HashMap<>();

        // Step 1: Build the map of names -> list of Classes (handles duplicates),
        for (Clase clase : project.getClaseList()) {
            nombreClaseMultimap
                    .computeIfAbsent(clase.getNombre(), k -> new ArrayList<>())
                    .add(clase);
        }

        // Step 2: Process connections using the unique key of the Node, // Condition equivalent to the original: avoid self-references by className,
        for (Clase classs : project.getClaseList()) {
            Node currentClassNode = classs.getClassNode();
            String currentClassName = currentClassNode.getClassName();
            List<String> currentClassNodeConnections = currentClassNode.getConextions();

            for (String connectedNombre : currentClassNodeConnections) {
            // Condition equivalent to the original: avoid self-references by className
                if (!currentClassName.equals(connectedNombre)) {
                    List<Clase> connectedClases = nombreClaseMultimap.get(connectedNombre);
                    if (connectedClases != null) {
                        for (Clase connectedClass : connectedClases) {
                            // Direct access using the unique key of the current Node
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

    //TODO: DEVELOP THE LOGIC FOR CONECT TO GRAFO VIEW FOR THAT YOU NEED:
    // LIKE  Map<String, List<String>> grafo = new HashMap<>();
    // EXAMPLE: grafo.put("A", new HashSet<>(Arrays.asList("B", "C")));


}
