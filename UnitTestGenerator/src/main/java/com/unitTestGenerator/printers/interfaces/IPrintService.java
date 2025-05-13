package com.unitTestGenerator.printers.interfaces;

import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.printers.PrintService;

public interface IPrintService {

    default PrintService service(){
       return ContextIOC.getInstance().getClassInstance(PrintService.class);
    }

}
