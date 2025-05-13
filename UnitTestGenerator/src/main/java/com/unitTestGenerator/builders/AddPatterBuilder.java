package com.unitTestGenerator.builders;

import com.unitTestGenerator.builders.interfaces.IFileManager;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Singleton
public class AddPatterBuilder implements IFileManager {

    public AddPatterBuilder() { }

    public void generateBuilderPatterFromClassFile(String filePath) throws IOException {
        File classfileOriginal = new File(filePath);
        String classContent = getFileContent(classfileOriginal, filePath);
        generateBuilderFromClass(classContent, classfileOriginal);
    }


    private String[] splitClassContent(String classContent) {

        if (classContent == null || classContent.isEmpty()) {
            return new String[]{"", ""};
        }
        int lastIndex = classContent.lastIndexOf('}');
        if (lastIndex == -1) {
            return new String[]{classContent, ""};
        }
        String before = classContent.substring(0, lastIndex).trim();
        String after = classContent.substring(lastIndex + 1).trim();
        return new String[]{before, after};
    }



    public void generateBuilderFromClass(String classContent,  File fileToWrite) throws IOException {

        String className = extractClassName(classContent);
        List<String> fields = extractFields(classContent, className);
        if (className == null || fields.isEmpty()) {
            System.out.println("Can't process class; it does not contain fields.");
            return;
        }
        String builderClassName = className + "Builder";
        String builderClass = "Builder";
        String[] correctedCode =this.splitClassContent(classContent);

        StringBuilder builderCode = new StringBuilder(correctedCode[0]);
        builderCode.append("\n").append("\n\n");
        builderCode.append(this.addBuilderStaticMethod(builderClass));
        builderCode.append(this.addBuilderInterfaces(builderClass, builderClassName, className,  fields));
        builderCode.append(this.addClassBuilder(builderClass,fields,builderClassName));
        builderCode.append(this.addSetterMethodsToClassBuilder(fields,builderClass));
        builderCode.append(this.addBuildMethod(fields, className));
        builderCode.append(correctedCode[1]);
        this.writefilesI(fileToWrite, builderCode.toString());
    }


    private String extractClassName(String classContent) {
        String className = null;
        String[] lines = classContent.split("\n");
        for (String line : lines) {
            if (line.startsWith("public class") || line.startsWith("private class") || line.startsWith("protected class")) {
                className = line.split(" ")[2].trim();
                break;
            }
        }
        return className;
    }

    private List<String> extractFields(String classContent, String className ) {
        List<String> fields = new ArrayList<>();
        String[] lines = classContent.split("\n");
        boolean insideClass = false;
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class") || line.startsWith("private class") || line.startsWith("protected class")) {
                insideClass = true;

            } else if (insideClass && (line.startsWith("private") || line.startsWith("public") || line.startsWith("protected"))) {
                String modifiedDeclaration = "";
                if(line.startsWith("private")) {
                    modifiedDeclaration = line.replace("private ", "").replace(";", "");
                }
                if (line.startsWith("public") && !line.contains(className) ) {
                    modifiedDeclaration = line.replace("public ", "").replace(";", "");
                }
                if (line.startsWith("protected")) {
                    modifiedDeclaration = line.replace("protected ", "").replace(";", "");
                }
                if( modifiedDeclaration != null && !modifiedDeclaration.equals("")) {
                    fields.add(modifiedDeclaration);
                }
            } else if (insideClass && line.startsWith("}")) {
                break;
            }
        }
        return fields;
    }


    private String addBuilderStaticMethod( String builderClass){
        StringBuilder addBuilderStaticMethod = new StringBuilder();
        // Agregar método estático builder()
        addBuilderStaticMethod.append("public static ")
                .append(builderClass)
                .append(" builder() {\n")
                .append("\treturn new ")
                .append(builderClass).append("();\n")
                .append("}\n\n");

        return addBuilderStaticMethod.toString();
    }

    private String addBuilderInterfaces(String builderClass,String builderClassName,String className,  List<String> fields){
        StringBuilder addBuilderInterfaces = new StringBuilder();

        addBuilderInterfaces.append("public interface "+builderClassName+" {\n");
        for (String field : fields) {
            String fieldType = field.split(" ")[0];
            String fieldName = field.split(" ")[1];
            addBuilderInterfaces.append("\tpublic ").append(builderClass).append(" ")
                    .append(fieldName).append("(").append(fieldType).append(" ")
                    .append(fieldName).append(");\n");
        }
        addBuilderInterfaces.append("\n\tpublic ").append(className).append(" build();\n");
        addBuilderInterfaces.append("}\n\n");
        return addBuilderInterfaces.toString();
    }

    private String addClassBuilder( String builderClass, List<String> fields, String builderClassName){
        StringBuilder addClassBuilder = new StringBuilder();

        addClassBuilder.append("public static class ").append(builderClass).append(" implements "+builderClassName+" {\n");
        for (String field : fields) {
            String fieldType = field.split(" ")[0];
            String fieldName = field.split(" ")[1];
            addClassBuilder.append("\tprivate ").append(fieldType).append(" ").append(fieldName).append(";\n");
        }
        return addClassBuilder.toString();
    }

    private String addSetterMethodsToClassBuilder( List<String> fields, String builderClassName){
        StringBuilder addSettersToBuilder = new StringBuilder();
        for (String field : fields) {
            String fieldType = field.split(" ")[0];
            String fieldName = field.split(" ")[1];

            addSettersToBuilder.append("\n\t@Override\n")
                    .append("\tpublic ").append(builderClassName).append(" ").append(fieldName).append("(")
                    .append(fieldType).append(" ").append(fieldName).append(") {\n")
                    .append("\t\tthis.").append(fieldName).append(" = ").append(fieldName).append(";\n")
                    .append("\t\treturn this;\n").append("\t}\n");
        }
        return addSettersToBuilder.toString();
    }

    private String addBuildMethod(List<String> fields, String className){
        StringBuilder addBuildMethod = new StringBuilder();

        addBuildMethod.append("\n\t@Override\n")
                .append("\tpublic ")
                .append(className)
                .append(" build() {\n")
                .append("\t\t")
                .append(className)
                .append(" "+className.toLowerCase()+" = new ")
                .append(className).append("();\n");

        for (String field : fields) {
            String fieldName = field.split(" ")[1];

            addBuildMethod.append("\t\t"+className.toLowerCase()+".set")
                    .append(capitalizetheFirstLetter(fieldName))
                    .append("(this.")
                    .append(fieldName).append(");\n");
        }

        addBuildMethod.append("\t\treturn "+className.toLowerCase()+";\n\t}\n");
        addBuildMethod.append("}\n");
        addBuildMethod.append("\n").append("}\n\n");

        return addBuildMethod.toString();
    }

    public String capitalizetheFirstLetter(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return fieldName;
        }
        return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

}


