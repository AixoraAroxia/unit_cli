package com.unitTestGenerator.analyzers;


import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Singleton
public class AnalizeExtendsInInterfaces {

    public   AnalizeExtendsInInterfaces() {
    }

    public Project analizeImplements( Project project, Character key){
        if(project != null &&  key != null && project.getClaseList() != null && !project.getClaseList().isEmpty()){
            project.getClaseList().forEach(clase -> {
                if(clase.getClassRelations() != null && !clase.getClassRelations().getImplementsList().isEmpty()){
                    StringBuffer buffer = new StringBuffer();
                    clase.getClassRelations().getImplementsList().forEach(Implementation -> {
                        buffer.append("|__ ").append(Implementation.trim()).append("\n");
                        Clase implementClass = project.getClass(Implementation.trim());
                        String treeString = this.extendsLoop(project, implementClass);
                        if (!treeString.isEmpty()) {
                            String indentedTree = treeString.replaceAll("(?m)^", "   ");
                            buffer.append(indentedTree);
                        }
                    });
                    String implementTree = clase.getNombre() +""+"\n"+buffer.toString().replaceAll("(?m)^", "   ");
                    clase.setStructureInterface(implementTree);
                }
            });

        }
        return project;
    }

    public Project analizeExtens( Project project){
        if(project != null && project.getClaseList() != null && !project.getClaseList().isEmpty()){
            project.getClaseList().forEach(clase -> {
                if(clase.getClassRelations() != null && isExtents(clase)){
                    StringBuffer buffer = new StringBuffer();
                    String nameClass = clase.getClassRelations().getClassExtends().trim();
                        buffer.append("|__ ").append(nameClass).append("\n");
                        Clase implementClass = project.getClass(nameClass);
                        String treeString = this.extendsLoop(project, implementClass);
                        if (!treeString.isEmpty()) {
                            String indentedTree = treeString.replaceAll("(?m)^", "   ");
                            buffer.append(indentedTree);
                        }
                    clase.setStructureExtends(buffer.toString());
                }
            });
        }
        return project;
    }


    private String extendsLoop(Project project, Clase classRelation) {
        List<String> tree = new ArrayList<>();

        if(isExtents(classRelation)){
            Clase loop = classRelation;

            while (isExtents(loop)) {
                String extTemp = loop.getClassRelations().getClassExtends().trim();
                tree.add(extTemp);
               Optional<Clase> c = project.getClaseList().stream().filter(clase -> clase.getNombre().equals(extTemp)).findFirst();

                loop = project.getClass(extTemp);
            }
        }
        String structure = this.getTreeString(tree);
        return structure;
    }


    private String getTreeString(List<String> tree) {
        StringBuilder sb = new StringBuilder();
        if(!tree.isEmpty()){
            for(String leaf : tree){
                sb.append("|__ ").append(leaf).append("\n");
            }
            return sb.toString();
        }
        return "";
    }


    private Boolean isExtents(Clase loop){
        return loop != null && loop.getClassRelations() != null && loop.getClassRelations().getClassExtends() != null && !loop.getClassRelations().getClassExtends().equals("");
    }

}



