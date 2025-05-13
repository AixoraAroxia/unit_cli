package com.unitTestGenerator.ioc.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)  // Hace que la anotación esté disponible en tiempo de ejecución
public @interface ClassName {
    String value();  // El nombre completo de la clase
}
