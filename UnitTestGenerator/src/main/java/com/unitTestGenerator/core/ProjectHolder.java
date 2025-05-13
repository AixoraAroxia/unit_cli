package com.unitTestGenerator.core;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Project;

@Component
@Singleton
public class ProjectHolder {

    @Inyect
    private Project project;

    public ProjectHolder() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void update(Project project) {
        this.project = project;
    }

}
