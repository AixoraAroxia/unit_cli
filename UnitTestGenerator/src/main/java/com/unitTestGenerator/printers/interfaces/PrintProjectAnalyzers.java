package com.unitTestGenerator.printers.interfaces;

import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;

public interface PrintProjectAnalyzers extends IPrintService {


    default void printProjectAnalizeResult(Project projectP) {
        this.service().print_BLUE(projectP.getPrinterProject().getProjectUml());
    }


    default void printClassList(Project projectP ) {
        this.service().print_RED(""+projectP.getPrinterProject().getClaseList().size());
        projectP.getPrinterProject().getClaseList().forEach(className -> this.service().print_YELLOW(className));
    }

    default void printMethodsOfClass(Project projectP, String className ) {
        Clase classs = projectP.getClass(className);
        if(classs != null && classs.getMetodos() != null && !classs.getMetodos().isEmpty()){
            classs.getMetodos().forEach(metodo -> {
                this.service().print_GREEN(metodo.getMethodSignature());
            });
        }
    }

    default void printProjectClassTree(Project projectP ) {
        this.service().print_YELLOW(projectP.getPrinterProject().getProjectClassTree());
    }


    default void printProjectFileTree(Project projectP ) {
        this.service().print_GREEN(projectP.getPrinterProject().getProjectDirectoryTree());
    }

}
