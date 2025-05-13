package com.unitTestGenerator;

import com.unitTestGenerator.core.AppProjectStarted;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.EndebleIOC;


@EndebleIOC("com.unitTestGenerator")
public class App  {

    public static void main(String[] args) {
        ContextIOC.getInstance(App.class).getClassInstance(AppProjectStarted.class).start();
//        if (args.length > 0) {
//            System.out.println("Hola " + String.join(" ", args));
//        } else {
//            ContextIOC.getInstance(App.class).getClassInstance(AppProjectStarted.class).start();
//        }
    }

}





