package com.unitTestGenerator.printers.interfaces;

import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface IPrintAnalizeImports  {

    default Project generateImportsMap(Project project) {
        Optional.ofNullable(project)
                .map(Project::getClaseList)
                .ifPresent(clases -> clases.forEach(clase -> {
                    if (importsExits(clase)) {
                        StringBuilder buffer = new StringBuilder();
                        buffer.append("- ").append(clase.getNombre()).append("\n");
                        String treeString = this.extendsLoop(project, clase);
                        if (!treeString.isEmpty()) {
                            String indentedTree = treeString.replaceAll("(?m)^", "   ");
                            buffer.append(indentedTree);
                        }
                        clase.getImports().setProjectImportsMap(buffer.toString());
                    }
                }));
        return project;
    }

    default Boolean importsExits(Clase loop) {
        return loop != null && loop.getImports() != null && loop.getImports().getProjectImports() != null && !loop.getImports().getProjectImports().isEmpty();
    }





    default String extendsLoop(Project project, Clase classFather) {
        List<String> tree = new ArrayList<>();

        if (importsExits(classFather)) {
            classFather.getImports().getProjectImports().forEach(importOfClass -> {
                StringBuilder buffer = new StringBuilder();
                StringBuilder bufferClass = new StringBuilder();

                buffer.append(importOfClass).append("\n");
                String classImport = getClassOfImport(importOfClass).trim();
                bufferClass.append("|__ ").append(classImport).append(" ");
                String replaceBuffer = stringSpace(importOfClass.length());
                String indentedTree = bufferClass.toString().replaceAll("(?m)^", replaceBuffer);
                buffer.append(indentedTree).append("\n");

                Optional.ofNullable(project.getClass(classImport))
                        .filter(this::importsExits)
                        .ifPresent(importTree -> {
                            String structure = this.getTreeString(importTree.getImports().getProjectImports());
                            int space = importOfClass.length() + classImport.length();
                            String replaceBuffer2 = stringSpace(space);
                            String structureTree = structure.replaceAll("(?m)^", replaceBuffer2);
                            buffer.append(structureTree).append("\n");
                            tree.add(buffer.toString());
                        });
            });
        }
        return this.getTreeString(tree);
    }

    default String getTreeString(List<String> tree) {
        return tree.stream()
                .map(leaf -> "|__ " + leaf + "\n")
                .collect(Collectors.joining());
    }

    default String stringSpace(int number) {
        StringBuilder bufferSpace = new StringBuilder();
        for (int i = 0; i < number; i++) {
            bufferSpace.append(" ");
        }
        return bufferSpace.toString();
    }

    default String getClassOfImport(String importText) {
        int lastDotIndex = importText.lastIndexOf('.');
        return importText.substring(lastDotIndex + 1);
    }
}



