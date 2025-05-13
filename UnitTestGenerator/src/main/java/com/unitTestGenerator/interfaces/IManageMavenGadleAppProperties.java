package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.analyzers.GradleAnalyzer;
import com.unitTestGenerator.analyzers.PomAnalyzer;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.pojos.Project;

import java.io.File;

public interface IManageMavenGadleAppProperties extends IApplicationTestProperties {


    default void gradleAnalyzer(String pathProject, Integer importType){
        File fileGradle = new File( this.stringEnsamble(pathProject , Separator ,"build.gradle"));
        GradleAnalyzer analyzer = ContextIOC.getInstance().getClassInstance(GradleAnalyzer.class);
        analyzer.setArchivoGradle(fileGradle);

        if(importType == 1){
            analyzer.startedMokitoDependencys();
        }
        if (importType == 0){
            analyzer.startedWithoutMockDatabaseH2();
        }
        if (importType == 2){
            analyzer.addLombokDependency();
        }
    }

    default void projectTypeDependencesAnalizer(String pathProject, Project project){
        if(project.getMaven()) {
          ContextIOC.getInstance().getClassInstance(PomAnalyzer.class).addDependencys(pathProject,1);
        } else if(project.getGradle()){
            this.gradleAnalyzer( pathProject, 1);
        }
    }

    default void addLombokDependency(Project project){
        if(project.getMaven()) {
            ContextIOC.getInstance().getClassInstance(PomAnalyzer.class).addDependencys(project.getPathProject(),2);
        } else if(project.getGradle()){
            this.gradleAnalyzer( project.getPathProject(), 2);
        }
    }


    default void applicationTestPropertiesExist(Project project){
        String basePath = stringPaths(true,true,project.getPathProject(),"src","test","resources","application-test.properties");

        if(!filePathExists(basePath)){
            if(project.getMaven()) {
                ContextIOC.getInstance().getClassInstance(PomAnalyzer.class).addDependencys(project.getPathProject(),0);
            } else if(project.getGradle()){
                this.gradleAnalyzer( project.getPathProject(), 0);
            }
            File applicationTestPropertiesFile = new File(basePath);

            String fileContent ="";
            String oldContent = this.fileExist(applicationTestPropertiesFile);

            fileContent = oldContent != null && !oldContent.equals("") && !oldContent.contains(this.contentApplicationTestPropertiesFile()) ?
                            stringEnsamble(oldContent, "\n", this.contentApplicationTestPropertiesFile())
                            : this.contentApplicationTestPropertiesFile();
            this.writefilesI(applicationTestPropertiesFile, fileContent);
        }
    }



}
