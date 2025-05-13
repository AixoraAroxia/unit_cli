package com.unitTestGenerator.analyzers.dependency.interfaceanalyzer;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClassRelationAnalyzer {

    private Map<String, List<RelationInfo>> relations = new HashMap<>();

    public void analyzeClasses(List<String> javaSources) {
        for (String source : javaSources) {
            String className = extractClassName(source);
            List<RelationInfo> parents = extractParents(source, className);

            for (RelationInfo parent : parents) {
                relations.computeIfAbsent(parent.childClassName, k -> new ArrayList<>())
                        .add(new RelationInfo(className, parent.relationType));
            }
        }
    }


    private String extractClassName(String source) {
        Pattern pattern = Pattern.compile("\\b(class|interface)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(2);
        }
        throw new IllegalArgumentException("No se encontró nombre de clase o interfaz en el código.");
    }


    private List<RelationInfo> extractParents(String source, String childClassName) {
        List<RelationInfo> parents = new ArrayList<>();
        Pattern pattern = Pattern.compile("(extends|implements)\\s+([\\w\\s,]+)");
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            RelationType type = matcher.group(1).equals("extends") ? RelationType.EXTENDS : RelationType.IMPLEMENTS;
            String[] splitParents = matcher.group(2).split(",");
            for (String parent : splitParents) {
                parents.add(new RelationInfo(parent.trim(), type));
            }
        }
        return parents;
    }


    public ClassNode buildTree(String rootClass) {
        ClassNode root = new ClassNode(rootClass);
        buildTreeRecursive(root);
        return root;
    }

    private void buildTreeRecursive(ClassNode node) {
        List<RelationInfo> children = relations.get(node.className);
        if (children != null) {
            for (RelationInfo relationInfo : children) {
                ClassNode childNode = new ClassNode(relationInfo.childClassName);
                node.children.add(new Relation(relationInfo.relationType, childNode));
                buildTreeRecursive(childNode);
            }
        }
    }


    public void printTree(ClassNode node, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.className);
        for (int i = 0; i < node.children.size(); i++) {
            Relation relation = node.children.get(i);
            boolean last = (i == node.children.size() - 1);
            System.out.println(prefix + (isTail ? "    " : "│   ") +
                    (last ? "└── " : "├── ") +
                    "(" + relation.type.name().toLowerCase() + ") " + relation.node.className);
            printTree(relation.node, prefix + (isTail ? "    " : "│   ") + (last ? "    " : "│   "), true);
        }
    }



    public String buildTreeString(ClassNode node) {
        StringBuilder sb = new StringBuilder();
        buildTreeStringRecursive(node, "", true, sb);
        return sb.toString();
    }

    private void buildTreeStringRecursive(ClassNode node, String prefix, boolean isTail, StringBuilder sb) {
        sb.append(prefix).append(isTail ? "└── " : "├── ").append(node.className).append("\n");
        for (int i = 0; i < node.children.size(); i++) {
            Relation relation = node.children.get(i);
            boolean last = (i == node.children.size() - 1);
            sb.append(prefix)
                    .append(isTail ? "    " : "│   ")
                    .append(last ? "└── " : "├── ")
                    .append("(")
                    .append(relation.type.name().toLowerCase())
                    .append(") ")
                    .append(relation.node.className)
                    .append("\n");
            buildTreeStringRecursive(relation.node, prefix + (isTail ? "    " : "│   ") + (last ? "    " : "│   "), true, sb);
        }
    }



}

