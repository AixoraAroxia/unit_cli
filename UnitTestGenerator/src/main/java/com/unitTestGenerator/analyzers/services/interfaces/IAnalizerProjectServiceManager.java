package com.unitTestGenerator.analyzers.services.interfaces;

import com.unitTestGenerator.analyzers.services.AnalizerProjectService;
import com.unitTestGenerator.ioc.ContextIOC;

public interface IAnalizerProjectServiceManager {

    default AnalizerProjectService getInstanceAPSI(){
        return ContextIOC.getInstance().getClassInstance(AnalizerProjectService.class);
    }

}
