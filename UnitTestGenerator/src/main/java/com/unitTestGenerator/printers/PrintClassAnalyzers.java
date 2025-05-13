package com.unitTestGenerator.printers;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.*;
import com.unitTestGenerator.printers.interfaces.IPrintService;

@Component
@Singleton
public class PrintClassAnalyzers implements IPrintService {

    public  PrintClassAnalyzers(){}

    public void printClassDetail(Clase classs) {
        this.service().print_DARKGREEN(classs.getClassDetail());
    }


    public void printProjectAnalize(Project projectP, boolean isAnalisis){

        if(isAnalisis && projectP.getClaseList() != null) {
            System.out.println("Classes found:...");
            for (Clase clase : projectP.getClaseList()) {
                if(clase.getMetodos() != null && !clase.getMetodos().isEmpty()){
                    System.out.println(clase.getNombre() + "  package: " + clase.getPaquete());
                    for(Metodo metod : clase.getMetodos()){
                        if(metod.getInstanceMethodCalls() != null && !metod.getInstanceMethodCalls().isEmpty() ){
                            metod.getInstanceMethodCalls().forEach(instanceMethodCall -> {
                                System.out.println(  "CALL: -> Method: "+ metod.getNombre() + "| Call: "+instanceMethodCall.getOperation()+" |.");
                            });
                        }
                    }
                }
            }
        }

        if(isAnalisis && projectP.getClaseList() != null) {
            System.out.println("Classes found:");
            for (Clase clase : projectP.getClaseList()) {
                System.out.println(clase.getNombre() + "  package: " + clase.getPaquete());
            }
        }
    }

    private String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

}
