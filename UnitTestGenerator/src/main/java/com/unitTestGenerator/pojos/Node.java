package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Node {
    private String name;
    private String className;
    private List<String> conextions = new ArrayList<>();
    private List<String> useFor = new ArrayList<>();

    public Node() {
    }

    public Node(String name, String className, List<String> conextions, List<String> useFor) {
        this.name = name;
        this.className = className;
        this.conextions = conextions;
        this.useFor = useFor;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getConextions() {
        return conextions;
    }

    public void setConextions(List<String> conextions) {
        this.conextions = conextions;
    }

    public List<String> getUseFor() {
        return useFor;
    }

    public void setUseFor(List<String> useFor) {
        this.useFor = useFor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) && Objects.equals(className, node.className) && Objects.equals(conextions, node.conextions) && Objects.equals(useFor, node.useFor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, className, conextions, useFor);
    }
}


