package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrinterProject {

    private String projectDirectoryTree;
    private String projectClassTree;
    private  String projectUml;
    private List<String> claseList = new ArrayList<>();

    public PrinterProject() {
    }

    public String getProjectDirectoryTree() {
        return projectDirectoryTree;
    }

    public void setProjectDirectoryTree(String projectDirectoryTree) {
        this.projectDirectoryTree = projectDirectoryTree;
    }

    public String getProjectClassTree() {
        return projectClassTree;
    }

    public void setProjectClassTree(String projectClassTree) {
        this.projectClassTree = projectClassTree;
    }

    public String getProjectUml() {
        return projectUml;
    }

    public void setProjectUml(String projectUml) {
        this.projectUml = projectUml;
    }

    public List<String> getClaseList() {
        return claseList;
    }

    public void setClaseList(List<String> claseList) {
        this.claseList = claseList;
    }

    public void addToClaseList(String className) {
        this.claseList.add(className);
    }

    public void addToClaseList(List<String> classNamesList) {
        this.claseList.addAll(classNamesList);
    }


}
