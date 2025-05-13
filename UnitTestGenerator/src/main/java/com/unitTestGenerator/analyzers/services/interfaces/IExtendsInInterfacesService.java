package com.unitTestGenerator.analyzers.services.interfaces;

import com.unitTestGenerator.analyzers.AnalizeExtendsInInterfaces;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.pojos.Project;

public interface IExtendsInInterfacesService {

    default Project getInterfaceStructure(Project project){
      return ContextIOC.getInstance().getClassInstance(AnalizeExtendsInInterfaces.class).analizeImplements(project,'I');
    }

    default Project getExtendsStructure(Project project){
        return ContextIOC.getInstance().getClassInstance(AnalizeExtendsInInterfaces.class).analizeExtens(project);
    }
}
