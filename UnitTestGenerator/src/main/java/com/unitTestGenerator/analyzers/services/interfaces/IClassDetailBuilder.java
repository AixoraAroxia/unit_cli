package com.unitTestGenerator.analyzers.services.interfaces;


import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Constructor;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.util.interfaces.IRepeatLogic;
import java.util.Optional;

public interface IClassDetailBuilder extends IRepeatLogic {


    default Project generateClassesDetail(Project project) {
        Optional.ofNullable(project)
                .map(Project::getClaseList)
                .ifPresent(clases -> clases.forEach(clase -> {
                    clase.setClassDetail(this.buildClassDetail(clase));
                }));
        return project;
    }

    default String buildClassDetail(Clase classs) {

        StringBuffer buffer = new StringBuffer();
        if (classs != null) {
            String borderClass = repeat("<*><*>", 40 ) + "+\n";
            String border = repeat("---", 40 + 2) ;

            buffer.append("\n");
            buffer.append(borderClass).append("\n");
            buffer.append(border).append("\n");

            buffer.append("Class Name: ").append("\n");
            buffer.append("\t").append(classs.getNombre()).append("\n");
            buffer.append(border).append("\n");


            buffer.append("Class Package: ").append("\n");
            buffer.append("\t").append(classs.getPaquete()).append("\n");
            buffer.append(border).append("\n");


            buffer.append("Type Class: ").append("\n");
            buffer.append("\t").append(classs.getTypeClass()).append("\n");
            buffer.append(border).append("\n");


            buffer.append("Class Signature Line: ").append("\n");
            String signature = classs.getClassSignatureLine().replace("{","");
            buffer.append("\t").append(signature).append("\n");
            buffer.append(border).append("\n");


            buffer.append("Class classPath: ").append("\n");
            buffer.append("\t").append(classs.getClassPath()).append("\n");


            buffer.append(border).append("\n");
            buffer.append("Class UML Data Representation : ").append("\n");
            buffer.append(classs.getClassUml()).append("\n");


            buffer.append(border).append("\n");

            if (classs.getConstructores() != null && !classs.getConstructores().isEmpty()) {
                buffer.append("Class Constructors: ").append("\n");
                for (Constructor constructor : classs.getConstructores()) {
                    buffer.append("\t").append(constructor.getCostructorSignature()).append("\n");
                }
            }

            buffer.append(border).append("\n");
            buffer.append("Class Imports: ").append("\n");

            if (classs.getImports() != null && !classs.getImports().getProjectImports().isEmpty()) {
                if (!classs.getImports().getProjectImports().isEmpty()) {
                    buffer.append(border).append("\n");
                    buffer.append("\t").append("Project Imports: ").append("\n");
                    for ( String imports : classs.getImports().getProjectImports()){
                        buffer.append("\t").append(imports).append("\n");
                    }
                }


                if (!classs.getImports().getExternalImports().isEmpty()) {
                    buffer.append(border).append("\n");
                    buffer.append("\t").append("External Imports: ").append("\n");

                    for ( String imports : classs.getImports().getExternalImports()){
                        buffer.append("\t").append(imports).append("\n");
                    }
                }
            }


            if(classs.getStructureInterface() != null && !classs.getStructureInterface().equals("")){
                buffer.append(border).append("\n");
                buffer.append("Interface Structure: ").append("\n");
                buffer.append("\t").append(classs.getStructureInterface()).append("\n");
            }

            if(classs.getStructureExtends() != null && !classs.getStructureExtends().equals("")) {
                buffer.append(border).append("\n");
                buffer.append("Interface Extends: ").append("\n");
                buffer.append("\t").append(classs.getStructureExtends()).append("\n");
            }

            if(classs.getClassAnotations() != null && !classs.getClassAnotations().equals("")){
                buffer.append(border).append("\n");
                buffer.append("Class Anotations: ").append("\n");
                buffer.append("\t").append(classs.getClassAnotations()).append("\n");
            }
            buffer.append(border).append("\n");
            buffer.append(borderClass).append("\n");
        }
        return buffer.toString();
    }







}
