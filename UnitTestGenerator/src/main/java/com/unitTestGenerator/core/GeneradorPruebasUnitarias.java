package com.unitTestGenerator.core;


import com.unitTestGenerator.builders.BuildTestFile;
import com.unitTestGenerator.interfaces.IManageMavenGadleAppProperties;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.pojos.TestFileContent;
import com.unitTestGenerator.pojos.Variable;
import com.unitTestGenerator.services.MethodParameterObject;
import com.unitTestGenerator.util.interfaces.IConstantModel;


@Component
public class GeneradorPruebasUnitarias implements IManageMavenGadleAppProperties, IConstantModel {

    private Project project;

    @Inyect
    private DependenceManager dependenceManager;

    @Inyect
    private MethodParameterObject methodParameterObject;

    @Inyect
    private BuildTestFile buildTestFile;

    public GeneradorPruebasUnitarias() {
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public void generateTest(Clase clase, String pathProject) {
       this.projectTypeDependencesAnalizer(pathProject, this.project);
        TestFileContent fileContent = generateTestFileBody(clase);
        String pathOfTest = this.getPathOfTest(clase,pathProject);
        buildTestFile.createTestFile(pathOfTest, fileContent);
    }

    private  TestFileContent generateTestFileBody(Clase clase) {

        TestFileContent fileContent = TestFileContent.builder()
                .testsClassImport(this.generateImport(clase))
                .testsClassSingne(this.classSingne(clase))
                .testsClassVariables(dependenceManager.getGeneratedVariableService().generateVariable(clase))
                .testsClassMethods("")
                .build();
        if(!clase.getUseMock()) {
            this.applicationTestPropertiesExist(this.project);
        }
        TestFileContent fileContentGenerate =  dependenceManager.getGenerateMethodService().generateMethods(clase,this.project, fileContent);
        return fileContentGenerate;
    }

    private String classSingne(Clase clase){
        StringBuilder classSingne = new StringBuilder();

        if(clase.getUseMock()){
            classSingne.append("\n").append("@ExtendWith(MockitoExtension.class)").append("\n");
        }else{
            //TODO VERIFICAR DE LAS A NOTACIONES ES MEJOR PARA TESTING EL REPOSITORIO
            classSingne.append("\n");
           if( clase.getNombre().contains("JpaRepository") || clase.getNombre().contains("Repository") || clase.getNombre().contains("CrudRepository")){
               classSingne.append("\n").append("@DataJpaTest").append("\n");
               classSingne.append("@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)").append("\n");
           } else {
               classSingne.append("\n").append("\t").append("@ExtendWith(SpringExtension.class)").append("\n");

               String mainClass = this.project.getMainClassName();
               classSingne.append("\t").append("@SpringBootTest(classes =").append(mainClass).append(".class)").append("\n");
//               classSingne.append("\t").append("@SpringBootTest").append("\n");
               classSingne.append("\t").append("@ActiveProfiles(\"test\")").append("\n");
           }
        }
        classSingne.append("\t").append("public class " + clase.getNombre() + "Test {\n");
        return classSingne.toString();
    }

    private String generateImport(Clase clase){
        StringBuilder contex = new StringBuilder();
        contex.append(this.getBaseImport(clase)).append("\n");

        if(!clase.getUseMock()){
            contex.append(this.generateApplicationTestPropertiesImport()).append("\n");
        }else {
            contex.append(this.getMockImport()).append("\n").append("\n");
        }

        contex.append( this.stringEnsamble("import ", clase.getPaquete(), ".",clase.getNombre())).append(";").append("\n");
        contex.append(methodParameterObject.getImportMethodParameterObject(clase,project));

        for(Variable variable : clase.getVariables()){
            this.project.getClaseList().stream().forEach(projectClass -> {
                if (projectClass.getNombre() != null && projectClass.getNombre().equals(variable.getTipo())) {
                    contex.append(
                            this.stringEnsamble(
                                    "import ", projectClass.getPaquete(), ".",projectClass.getNombre())
                    ).append(";").append("\n");
                }
            });
        }
        return contex.toString();
    }



}