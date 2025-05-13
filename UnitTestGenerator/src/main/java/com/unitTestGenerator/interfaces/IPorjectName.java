package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.core.ProjectHolder;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.util.interfaces.IConstantModel;


public interface IPorjectName {

    default String getArtifatOrFileName(){
        ProjectHolder projectHolder = ContextIOC.getInstance().getClassInstance(ProjectHolder.class);
            String[] projectName = projectHolder.getProject().getPathProject().split(IConstantModel.Separator);
            return projectName[projectName.length - 1];
    }


    default String getArtifatOrFileName(String pathProject){
        String[] projectName = pathProject.split(IConstantModel.Separator);
        return projectName[projectName.length - 1];
    }

}
