package com.unitTestGenerator.uml.sevices;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.pojos.*;
import com.unitTestGenerator.util.interfaces.IRepeatLogic;

import java.util.List;

@Component
public class PrintClassToUML implements IRepeatLogic {

    private String nombre;
    private List<Variable> atributos;
    private List<Metodo> metodos;
    private ClassRelations relations;

    public PrintClassToUML() {
    }

    public PrintClassToUML(String nombre, List<Variable> atributos, List<Metodo> metodos, ClassRelations relaciones) {
        this.nombre = nombre;
        this.atributos = atributos;
        this.metodos = metodos;
        this.relations = relaciones;
    }


    public String getAttributes(Clase classs){
        StringBuffer buffer = new StringBuffer();

        buffer.append("Attributes: ").append("\n");
        if (classs.getVariables() != null && !classs.getVariables().isEmpty()) {
            for (Variable variable : classs.getVariables()) {
                buffer.append(uMLTemplate(variable.getAccessModifier(), variable.getNombre(), variable.getTipo())).append("\n");
            }
        }
        return buffer.toString();
    }

    public String getMethods(Clase classs) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Methods: ").append(nombre).append("\n");
        if (classs.getMetodos() != null && !classs.getMetodos().isEmpty()) {
            for (Metodo metodo : classs.getMetodos()) {
                String signature = metodo.getMethodSignature().replace(";","");
                buffer.append(uMLTemplate(metodo.getAccessModifier(), signature, metodo.getTipoRetorno())).append("\n");
            }
        }
        return buffer.toString();
    }

    public String getRelations(Clase classs) {
        StringBuffer buffer = new StringBuffer();

        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {
            buffer.append("Relations: ").append(nombre).append("\n");
            if (classs.getClassRelations().getImplementsList() != null && !classs.getClassRelations().getImplementsList().isEmpty()) {
                buffer.append("\t").append("Implement: ").append(nombre).append("\n");
                for (String impl : classs.getClassRelations().getImplementsList()) {
                    buffer.append("\t").append(" -").append(impl).append("\n");
                }
            }
            if (classs.getClassRelations().getClassExtends() != null && !classs.getClassRelations().getClassExtends().isEmpty()) {
                buffer.append("\t").append("Extends: ").append(nombre).append("\n");
                buffer.append("\t").append(" -").append(classs.getClassRelations().getClassExtends()).append("\n");
            }

            if (classs.getClassRelations().getDependencyInjectionIoC() != null && !classs.getClassRelations().getDependencyInjectionIoC().isEmpty()) {
                buffer.append("\t").append("Ioc: ").append(nombre).append("\n");
                for (String ioc : classs.getClassRelations().getDependencyInjectionIoC()) {
                    buffer.append("\t").append(" -").append(ioc).append("\n");
                }
            }

            if (classs.getClassRelations().getStrongDependencyAssociation() != null && !classs.getClassRelations().getStrongDependencyAssociation().isEmpty()) {
                buffer.append("\t").append("Strong Association: ").append(nombre).append("\n");
                for (String classAssociated : classs.getClassRelations().getStrongDependencyAssociation()) {
                    buffer.append("\t").append(" -").append(classAssociated).append("\n");
                }

                if (classs.getClassRelations().getIdentifieresRelatedClasses() != null && !classs.getClassRelations().getIdentifieresRelatedClasses().isEmpty()) {
                    for (String classAssociated : classs.getClassRelations().getIdentifieresRelatedClasses()) {
                        buffer.append("\t").append("Association static or patter Build: ").append(nombre).append("\n");
                        buffer.append("\t").append(" -").append(classAssociated).append("\n");
                    }
                }

            }
        }
        return buffer.toString();
    }


    public String getRelationsImplement(Clase classs) {
        StringBuffer buffer = new StringBuffer();

        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {
//            buffer.append("Relations: ").append(nombre).append("\n");
            if (classs.getClassRelations().getImplementsList() != null && !classs.getClassRelations().getImplementsList().isEmpty()) {
//                buffer.append("\t").append("Implement: ").append(nombre).append("\n");
                for (String impl : classs.getClassRelations().getImplementsList()) {
                    buffer.append("\t").append(" -").append(impl).append("\n");
                }
            }
        }
        return buffer.toString();
    }

    public String getRelationsExtends(Clase classs) {
        StringBuffer buffer = new StringBuffer();
        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {
            if (classs.getClassRelations().getClassExtends() != null && !classs.getClassRelations().getClassExtends().isEmpty()) {
//                buffer.append("\t").append("Extends: ").append(nombre).append("\n");
                buffer.append("\t").append(" -").append(classs.getClassRelations().getClassExtends()).append("\n");
            }
        }
        return buffer.toString();
    }

    public String getRelationsIoc(Clase classs) {
        StringBuffer buffer = new StringBuffer();

        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {

            if (classs.getClassRelations().getDependencyInjectionIoC() != null && !classs.getClassRelations().getDependencyInjectionIoC().isEmpty()) {
//                buffer.append("\t").append("Ioc: ").append(nombre).append("\n");
                for (String ioc : classs.getClassRelations().getDependencyInjectionIoC()) {
                    buffer.append("\t").append(" -").append(ioc).append("\n");
                }
            }
        }
        return buffer.toString();
    }

    public String getRelationsStrongAssociation(Clase classs) {
        StringBuffer buffer = new StringBuffer();

            if (classs.getClassRelations().getStrongDependencyAssociation() != null && !classs.getClassRelations().getStrongDependencyAssociation().isEmpty()) {
//                buffer.append("\t").append("Strong Association: ").append(nombre).append("\n");
                for (String classAssociated : classs.getClassRelations().getStrongDependencyAssociation()) {
                    buffer.append("\t").append(" -").append(classAssociated).append("\n");
                }
            }
        return buffer.toString();
    }

    public String getRelationsAssociationStaticOrPatterBuild(Clase classs) {
        StringBuffer buffer = new StringBuffer();

        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {
            if (classs.getClassRelations().getIdentifieresRelatedClasses() != null && !classs.getClassRelations().getIdentifieresRelatedClasses().isEmpty()) {
                for (String classAssociated : classs.getClassRelations().getIdentifieresRelatedClasses()) {
//                    buffer.append("\t").append("Association static or patter Build: ").append(nombre).append("\n");
                    buffer.append("\t").append(" -").append(classAssociated).append("\n");
                }
            }
        }
        return buffer.toString();
    }



    public String generarDiagrama2(Clase classs) {

        StringBuffer buffer = new StringBuffer();
        if (classs != null) {
            int maxLength = calculateMaxLength(classs);
            String borderClass = this.repeat("*/*", 30 ) + "+\n";
            String border = this.repeat("-", (maxLength/2) + 2) ;


            buffer.append("\n");
            buffer.append(borderClass).append("\n");
            buffer.append(border).append("\n");
            buffer.append("Class Name: ").append(classs.getNombre()).append("\n");
            buffer.append(border).append("\n");

            buffer.append("Attributes: ").append("\n");
            if (classs.getVariables() != null && !classs.getVariables().isEmpty()) {
                for (Variable variable : classs.getVariables()) {
                    buffer.append(uMLTemplate(variable.getAccessModifier(), variable.getNombre(), variable.getTipo())).append("\n");
                }
            }
            buffer.append(border).append("\n");

            buffer.append("Methods: ").append(nombre).append("\n");
            if (classs.getMetodos() != null && !classs.getMetodos().isEmpty()) {
                for (Metodo metodo : classs.getMetodos()) {
                    String signature = metodo.getMethodSignature().replace(";","");
                    buffer.append(uMLTemplate(metodo.getAccessModifier(), signature, metodo.getTipoRetorno())).append("\n");
                }
            }
            buffer.append(border).append("\n");

            if (classs.getClassRelations() != null && classs.getClassRelations().check()) {
                buffer.append("Relations: ").append(nombre).append("\n");
                if (classs.getClassRelations().getImplementsList() != null && !classs.getClassRelations().getImplementsList().isEmpty()) {
                    buffer.append("\t").append("Implement: ").append(nombre).append("\n");
                    for (String impl : classs.getClassRelations().getImplementsList()) {
                        buffer.append("\t").append(" -").append(impl).append("\n");
                    }
                }
                if (classs.getClassRelations().getClassExtends() != null && !classs.getClassRelations().getClassExtends().isEmpty()) {
                    buffer.append("\t").append("Extends: ").append(nombre).append("\n");
                    buffer.append("\t").append(" -").append(classs.getClassRelations().getClassExtends()).append("\n");
                }

                if (classs.getClassRelations().getDependencyInjectionIoC() != null && !classs.getClassRelations().getDependencyInjectionIoC().isEmpty()) {
                    buffer.append("\t").append("Ioc: ").append(nombre).append("\n");
                    for (String ioc : classs.getClassRelations().getDependencyInjectionIoC()) {
                        buffer.append("\t").append(" -").append(ioc).append("\n");
                    }
                }

                if (classs.getClassRelations().getStrongDependencyAssociation() != null && !classs.getClassRelations().getStrongDependencyAssociation().isEmpty()) {
                    buffer.append("\t").append("Strong Association: ").append(nombre).append("\n");
                    for (String classAssociated : classs.getClassRelations().getStrongDependencyAssociation()) {
                        buffer.append("\t").append(" -").append(classAssociated).append("\n");
                    }

                    if (classs.getClassRelations().getIdentifieresRelatedClasses() != null && !classs.getClassRelations().getIdentifieresRelatedClasses().isEmpty()) {
                        for (String classAssociated : classs.getClassRelations().getIdentifieresRelatedClasses()) {
                            buffer.append("\t").append("Association static or patter Build: ").append(nombre).append("\n");
                            buffer.append("\t").append(" -").append(classAssociated).append("\n");
                        }
                    }

                }
            }
            buffer.append(border).append("\n");
            buffer.append(borderClass).append("\n");
            classs.setClassUml(buffer.toString());
        }

        return  buffer.toString();
    }

    private int calculateMaxLength(Clase classs) {
        int maxLength = nombre.length();
        for (Variable variable : classs.getVariables()) {
            maxLength = Math.max(maxLength, (" " + variable.getTipo() + " " + variable.getNombre()).length());
        }
        for (Metodo metodo : classs.getMetodos()) {
            maxLength = Math.max(maxLength, (metodo.getMethodSignature().length()));
        }
        maxLength = Math.max(maxLength, (" " + classs.getClassRelations().toString()).length());
        return maxLength;
    }



    private String uMLTemplate(String accessModifier, String name, String type) {
        StringBuilder bilder = new StringBuilder();
        bilder.append(umlAccessModifier(accessModifier)).append(" ").append(name).append(" : ").append(type).append("\n");
        return bilder.toString();
    }

    private String uMLTemplate2(String accessModifier, String name, String type) {
        StringBuilder bilder = new StringBuilder();
        bilder.append(umlAccessModifier(accessModifier)).append(" ").append(name).append(" : ").append(type);
        return bilder.toString();
    }

    private String umlAccessModifier(String accessModifier) {
        switch (accessModifier) {
            case "private":
                return "(-)";
            case "protected":
                return "(#)";
            case "public":
                return "(+)";
            default:
                return "(+)";
        }
    }


    public String projectToElement(Project project) {
        StringBuffer buffer = new StringBuffer();
        if (project != null) {
            for (int i = 0; i < project.getClaseList().size(); i++) {
                Clase classs = project.getClaseList().get(i);
                if (i != 0) {
                    buffer.append(",");
                }
                String element = this.classToElement(classs);
                buffer.append(element);
            }
        }
        return buffer.toString();
    }

    public String classToElement(Clase classs){

        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("name: ").append("\"").append(classs.getNombre()).append("\"").append(", ")
                .append("value:" ).append("\"").append("vf").append("\"").append(", ");
        buffer.append("variables: [");
        if (classs.getVariables() != null && !classs.getVariables().isEmpty()) {
            for(int i =0 ; i < classs.getVariables().size(); i++){
                Variable variable = classs.getVariables().get(i);
                if(i != 0){
                    buffer.append(",");
                }
                buffer.append("\"")
                        .append(uMLTemplate2(variable.getAccessModifier(), variable.getNombre(), variable.getTipo())).append("\"");
            }
        }
        buffer.append("], ");
        buffer.append("method: [");
        if (classs.getMetodos() != null && !classs.getMetodos().isEmpty()) {
            for(int i =0 ; i < classs.getMetodos().size(); i++){
                Metodo metodo = classs.getMetodos().get(i);
                if(i != 0){
                    buffer.append(",");
                }
                String methodSignature = metodo.getMethodSignature().replace(";","");
                buffer.append("\"")
                        .append(uMLTemplate2(metodo.getAccessModifier(), methodSignature, metodo.getTipoRetorno()))
                        .append("\"");
            }
        }
        buffer.append("], ");

        buffer.append("relations: [ ");
        StringBuffer consteObject = new StringBuffer();

        if (classs.getClassRelations() != null && classs.getClassRelations().check()) {

            if (classs.getClassRelations().getImplementsList() != null && !classs.getClassRelations().getImplementsList().isEmpty()) {
                buffer.append("\"").append("Implement: ").append("\"").append(",");
                for(int i =0 ; i < classs.getClassRelations().getImplementsList().size(); i++){
                    String impl = classs.getClassRelations().getImplementsList().get(i);
                    if(i != 0){
                        buffer.append(",");
                        consteObject.append(",");
                    }
                    buffer.append("\t").append("\"").append(impl).append("\"");

                    if (consteObject.length() > 0){
                        consteObject .append(",");
                    }
                    consteObject.append("{").append("connection:").append("\"").append(impl).append("\"")
                            .append(",").append("move: true }");
                }
            }

            if (classs.getClassRelations().getClassExtends() != null && !classs.getClassRelations().getClassExtends().isEmpty()) {
                buffer.append(",");
                buffer.append("\"").append("Extends: ").append("\"").append(",");
                buffer.append("\t").append("\"").append(classs.getClassRelations().getClassExtends()).append("\"").append(",");

                if (consteObject.length() > 0){
                    consteObject .append(",");
                }
                consteObject.append("{").append("connection:").append("\"")
                        .append(classs.getClassRelations().getClassExtends()).append("\"")
                        .append(",").append("move: false }");
            }

            if (classs.getClassRelations().getDependencyInjectionIoC() != null && !classs.getClassRelations().getDependencyInjectionIoC().isEmpty()) {
                buffer.append(",");
                buffer.append("\"").append("Ioc: ").append("\"").append(",");
                for(int i =0 ; i < classs.getClassRelations().getDependencyInjectionIoC().size(); i++){
                    String ioc = classs.getClassRelations().getDependencyInjectionIoC().get(i);
                    if(i != 0){
                        buffer.append(",");
                        consteObject.append(",");
                    }
                    buffer.append("\t").append("\"").append(ioc).append("\"");
                    if (consteObject.length() > 0){
                        consteObject .append(",");
                    }
                    consteObject.append("{").append("connection:").append("\"").append(ioc).append("\"")
                            .append(",").append("move: true }");
                }
            }

            if (classs.getClassRelations().getStrongDependencyAssociation() != null && !classs.getClassRelations().getStrongDependencyAssociation().isEmpty()) {
                buffer.append(",");
                buffer.append("\"").append("Strong Association: ").append("\"").append(",");
                for(int i =0 ; i < classs.getClassRelations().getStrongDependencyAssociation().size(); i++){
                    String classAssociated = classs.getClassRelations().getStrongDependencyAssociation().get(i);
                    if(i != 0){
                        buffer.append(",");
                        consteObject.append(",");
                    }
                    buffer.append("\t").append("\"").append(classAssociated).append("\"");
                    if (consteObject.length() > 0){
                        consteObject .append(",");
                    }
                    consteObject.append("{").append("connection:").append("\"").append(classAssociated).append("\"")
                            .append(",").append("move: true }");
                }
            }


            if (classs.getClassRelations().getIdentifieresRelatedClasses() != null && !classs.getClassRelations().getIdentifieresRelatedClasses().isEmpty()) {
                buffer.append(",");
                buffer.append("\"").append("Static and singleton Related: ").append("\"").append(",");
                for(int i =0 ; i < classs.getClassRelations().getIdentifieresRelatedClasses().size(); i++){
                    String classAssociated = classs.getClassRelations().getIdentifieresRelatedClasses().get(i);
                    if(i != 0){
                        buffer.append(",");
                        consteObject.append(",");
                    }
                    buffer.append("\t").append("\"").append(classAssociated).append("\"");
                    if (consteObject.length() > 0){
                        consteObject .append(",");
                    }
                    consteObject.append("{").append("connection:").append("\"").append(classAssociated).append("\"")
                            .append(",").append("move: true }");
                }
            }
        }
        buffer.append("], ");
        buffer.append("conste: [ ");
        buffer.append(consteObject.toString());
        buffer.append("]");
        buffer.append("}");
        return buffer.toString();
    }
    private String formatLine(String content, int width) {
        int padding = width - content.length();
        return "| " + content + repeat(" ", padding) + " |\n";
    }

    public String generarDiagrama() {
        StringBuilder diagrama = new StringBuilder();
        int maxLength = calcularAnchoMaximo();
        String border = "+" + repeat("-", maxLength + 2) + "+\n";

        // Encabezado
        diagrama.append(border);
        diagrama.append(formatLine(nombre, maxLength));
        diagrama.append(border);

        // Atributos
        for (Variable variable : atributos) {
            diagrama.append(formatLine("- " + variable.getTipo() + " " + variable.getNombre(), maxLength));
        }
        diagrama.append(border);

        // Métodos
        for (Metodo metodo : metodos) {
            diagrama.append(formatLine("+ " + metodo.getMethodSignature(), maxLength));
        }
        diagrama.append(border);

        // Relaciones
        if (relations != null) {
            diagrama.append("Relations:\n");
            diagrama.append(border);
            //TODO: HAY QUE COLOCAR EL LISTADO DE LAS RELACIONES PERO
            diagrama.append("" + this.relations.toString() + "\n");
            diagrama.append(border);
        }

        return diagrama.toString();
    }

    private int calcularAnchoMaximo() {
        int maxLength = nombre.length();

        for (Variable variable : atributos) {
            maxLength = Math.max(maxLength, ("- " + variable.getTipo() + " " + variable.getNombre()).length());
        }
        for (Metodo metodo : metodos) {
            maxLength = Math.max(maxLength, ("+ " + metodo.getMethodSignature()).length());
        }
        maxLength = Math.max(maxLength, ("+ " + this.relations.toString()).length());
        return maxLength;
    }


}
