package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.analyzers.PomAnalyzer;
import com.unitTestGenerator.builders.interfaces.IFileManager;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;

import java.io.File;

public interface IApplicationTestProperties extends IFileManager {


    default String generateApplicationTestPropertiesImport() {
        StringBuilder contex = new StringBuilder();
        contex.append("import org.springframework.beans.factory.annotation.Autowired;").append("\n");
        contex.append("import org.springframework.test.context.ActiveProfiles;").append("\n");
        contex.append("import org.springframework.boot.test.context.SpringBootTest;").append("\n");
        contex.append("import org.springframework.test.context.junit.jupiter.SpringExtension;").append("\n");
        return contex.toString();
    }

    default String contentApplicationTestPropertiesFile(){
        StringBuilder contex = new StringBuilder("# src/test/resources/application-test.properties").append("\n");
        contex.append("\n").append("spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1").append("\n");
        contex.append("spring.datasource.driverClassName=org.h2.Driver").append("\n");
        contex.append("spring.datasource.username=sa").append("\n");
        contex.append("spring.datasource.password=").append("\n");
        contex.append("spring.jpa.database-platform=org.hibernate.dialect.H2Dialect").append("\n");
        return contex.toString();
    }
}
