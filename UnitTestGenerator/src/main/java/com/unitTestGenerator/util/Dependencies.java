package com.unitTestGenerator.util;

import com.unitTestGenerator.pojos.Dependency;

public class Dependencies {


    public static Dependency JUNIT_DEPENDENCY = Dependency.builder().groupId("org.junit.jupiter").artifactId("junit-jupiter").version("5.8.2").scope("test").build();
    public static Dependency MOCK_DEPENDENCY = Dependency.builder().groupId("org.mockito").artifactId("mockito-junit-jupiter").version("3.12.4").scope("test").build();
    public static Dependency MOCK_DEPENDENCY_core = Dependency.builder().groupId("org.mockito").artifactId("mockito-core").version("3.12.4").scope("test").build();
    public static Dependency H2_DEPENDENCY_TEST = Dependency.builder().groupId("com.h2database").artifactId("h2").version("2.1.210").scope("test").build();
    public static Dependency LOMBOK_DEPENDENCY = Dependency.builder().groupId("org.projectlombok").artifactId("lombok").version("1.18.34").scope("provided").build();



}

