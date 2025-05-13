package com.unitTestGenerator.ioc.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EndebleIOC {
    String value();
}
