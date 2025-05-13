package com.unitTestGenerator.menus;

import com.unitTestGenerator.analyzers.dependency.IDependencyAnalyzer;
import com.unitTestGenerator.analyzers.services.AnalizerProjectService;
import com.unitTestGenerator.analyzers.services.interfaces.IAnalizerProjectServiceManager;
import com.unitTestGenerator.builders.PDFGenerator;
import com.unitTestGenerator.core.AppProjectStarted;
import com.unitTestGenerator.core.ProjectHolder;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.printers.interfaces.IPrintService;
import com.unitTestGenerator.printers.PrintClassAnalyzers;
import com.unitTestGenerator.printers.interfaces.PrintProjectAnalyzers;
import com.unitTestGenerator.util.interfaces.IBaseModel;
import com.unitTestGenerator.util.interfaces.IConstantModel;

import java.io.IOException;
import java.util.Scanner;

//V1.1.0.0  In progress....

@Component
@Singleton
public class AnalizerMenu implements IAnalizerProjectServiceManager, IBaseModel, PrintProjectAnalyzers, IPrintService, IDependencyAnalyzer {

    private ProjectHolder projectHolder;
    private AnalizerProjectService analizerProjectService;
    private PrintClassAnalyzers printClassAnalyzers;
    private PDFGenerator pdfGenerator;

    public AnalizerMenu(ProjectHolder projectHolder, AnalizerProjectService analizerProjectService, PrintClassAnalyzers printClassAnalyzers, PDFGenerator pdfGenerator) {
        this.projectHolder = projectHolder;
        this.analizerProjectService = analizerProjectService;
        this.printClassAnalyzers = printClassAnalyzers;
        this.pdfGenerator = pdfGenerator;
    }

    public void analizerMenu(){
        this.printColummStringY("Analyze project Module",
                "Choose an option:",
                "1. Analyze project",
                "2. Load a Previous Saved Project",
                "3. Return to the previous menu"
        );
    }

    public void analizerMenuStarted(Project project, Scanner scanner) {
        this.analizerMenu();
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                Project projectAnalized  = analizerProjectService.analizerProject(scanner, true, project);
                this.projectHolder.setProject(projectAnalized);
                this.analizerMenuInitial(projectAnalized);
                break;
            case 2:
                Project projectsave = ContextIOC.getInstance().getClassInstance(SavedProjectMenu.class).started(scanner);
                if(projectsave == null){
                    this.analizerMenuStarted( project,  scanner);
                } else {
                    this.projectHolder.setProject(projectsave);
                    this.analizerMenuInitial(projectsave);
                }
                break;
            case 3:
                this.goToMainMenu();
                break;
            default:
                this.service().print_RED("Invalid option");
                this.analizerMenuStarted(project, scanner);
        }
    }

    public void AnalysisOptionsMenu(){
       this.printColummStringY("Analyzer Options Menu:",
                "Choose an option:",
                "1. Print the list of classes",
                "2. Print Methods of one class",
                "3. Print a Class with Details",
                "4. Print the project class tree",
                "5. Print the project file tree",
                "6. Generate Interface Relations",
                "7. Generate File",
                "8. Return to the previous menu",
                "9. Return to the main menu");
    }



    public void analizerMenuInitial(Project project){
        Scanner scanner = new Scanner(System.in);
        this.AnalysisOptionsMenu();
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 2:
                methodsOfclass(scanner, project);
                subMenu2( project,  scanner);
                break;
            case 3:
                this.classDetail(scanner, project);
                subMenu2( project,  scanner);
                break;
            case 4:
                this.printProjectClassTree(project);
                subMenu2( project,  scanner);
                break;
            case 5:
                this.printProjectFileTree(project);
                subMenu2( project,  scanner);
                break;
            case 6:
                this.interfaceRelations( project, scanner,true);
                subMenu2( project,  scanner);
                break;
            case 7:
               this.generateFileMenu(project, scanner);
                break;
            case 8:
                this.analizerMenuStarted( project,scanner);
                break;
            case 9:
                this.goToMainMenu();
                break;
            default:
                this.printClassList(project);
                this.subMenu1(project,scanner);
        }
    }

    public void subMenu2Txt(){
        this.printColummStringY(
                "Choose an option:",
                "1. Exit",
                "2. Return to the previous menu",
                "3. Return to the main menu");
    }

    public void subMenu2(Project project, Scanner scanner) {
        this.subMenu2Txt();
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Good bye");
                break;
            case 2:
                analizerMenuInitial(project);
                break;
            case 3:
                this.goToMainMenu();
                break;
            default:
                System.out.println("Invalid option");
                analizerMenuInitial(project);
        }
    }

    public void subMenu1Txt(){
        this.printColummStringY(
                "Choose an option:",
                "1. Print  methods of one class",
                "2. Return to the previous menu",
                "3. Return to the main menu");
    }

    public void subMenu1(Project project, Scanner scanner) {
        this.subMenu1Txt();
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                methodsOfclass(scanner, project);
                subMenu2( project,  scanner);
                break;
            case 2:
                analizerMenuInitial(project);
                break;
            case 3:
                this.goToMainMenu();
                break;
            default:
                System.out.println("Invalid option");
                analizerMenuInitial(project);
        }
    }


    private boolean askContinue(Scanner scanner) {
        System.out.println("Return to main menu? (y/n)");
        String response = scanner.next().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Invalid response. Please enter 'y' for yes or 'n' for no.");
            response = scanner.next().toLowerCase();
        }
        return response.equals("y");
    }

    private void goToMainMenu(){
        ContextIOC.getInstance().getClassInstance(AppProjectStarted.class).start();
    }


    private void methodsOfclass(Scanner scanner, Project project) {
        System.out.println("Enter the name of the Class");
        String response = scanner.next().toLowerCase();
        if(response != null && !response.equals("")){
            this.printMethodsOfClass(project, response);
        }
    }

    private void classDetail(Scanner scanner, Project project) {
        System.out.println("Enter the name of the Class");
        String response = scanner.next().toLowerCase();
        if(response != null && !response.equals("")){
            Clase classs = project.getClass(response);
            if(classs != null && classs.getClassDetail() != null && !classs.getClassDetail().isEmpty()){
                this.printClassAnalyzers.printClassDetail(classs);
            }
        }
    }


    public void generateFileMenuTxt(){
        this.printColummStringY(
                "Generate File Menu: ",
                "Choose an option:",
                "1. Class Report",
                "2. Generate Protect Report",
                "4. Generate Interface Relations",
                "5. Return to the previous menu"
        );
    }

//    public void generateFileMenuTxt(){
//        this.printColummStringY(
//                "Generate File Menu: ",
//                "Choose an option:",
//                "1. Class Report",
//                "2. Generate Protect Report",
//                "3. Generate .png with project Dependency",
//                "4. Generate UML Class Diagram",
//                "5. Generate Interface Relations",
//                "6. Return to the previous menu"
//        );
//    }


    public void generateFileMenu(Project project, Scanner scanner) {
        this.generateFileMenuTxt();
        int opcion = scanner.nextInt();
        String pathBase = project.getPathProject() + IConstantModel.Separator;
        switch (opcion) {
            case 1:
                this.reportClass(scanner, project);
                subMenu2( project,  scanner);
                break;
            case 2:
                this.pdfGenerator.converterProjectOrClasInpdf(project,null);
                subMenu2( project,  scanner);
                break;
//            case 3:
//                String outputPath = pathBase + project.getName() + "_Dependency";
//                try {
//                    this.generateDependencyDotPng(outputPath, project.getRawClassList());
//                    this.service().print_GREEN("Successfully generated the File");
//                } catch (IOException e) {
//                    this.service().print_RED("ERROR IN GENERATION OF PNG DEPENDENCY");
//                    this.service().print_RED("ERROR: "+ e.getMessage());
//                    throw new RuntimeException(e);
//                }
//                subMenu2( project,  scanner);
//                break;
//            case 4:
//                String outputPathUml = pathBase + project.getName() + "_UmlDiagram";
//
//                try {
//                    this.generateUmlDiagram(outputPathUml, project.getRawClassList());
//                    this.service().print_GREEN("Successfully generated the File");
//                } catch (IOException e) {
//                    this.service().print_RED("ERROR IN GENERATION OF PNG UmlDiagram");
//                    this.service().print_RED("ERROR: "+ e.getMessage());
//                    throw new RuntimeException(e);
//                }
//                subMenu2( project,  scanner);
//                break;
            case 4:
                this.interfaceRelations( project, scanner,false);
                subMenu2( project,  scanner);
                break;
            case 5:
                this.analizerMenuInitial(project);
                break;
            default:
                System.out.println("Invalid option");
                analizerMenuInitial(project);
        }
    }


    private void reportClass(Scanner scanner, Project project) {
       this.service().print_BLUE("Enter the name of the Class");
        String response = scanner.next().toLowerCase();
        if(response != null && !response.equals("")){
            Clase classs = project.getClass(response);
            if(classs != null && classs.getClassDetail() != null && !classs.getClassDetail().isEmpty()){
                this.pdfGenerator.converterProjectOrClasInpdf(null,classs);
            }
        }

    }


    private void interfaceRelations(Project project, Scanner scanner, Boolean printText){

        this.service().print_DARKGREEN("Enter the interface Name for analyzed");
        String targetClassName = scanner.next();

        if(targetClassName != null && !targetClassName.equals("")){
            try {
                this.service().print_BLUE("Please wait while between analyzed the Project...");
                String nameFile = targetClassName + "_Interface_Relations"+IConstantModel.PDF_Extention;
                String interfaceRelations = this.interfaceRelations(project.getRawClassList(), targetClassName);

                if(!printText){
                    this.service().print_BLUE("Please wait while generating the report...");
                    ContextIOC.getInstance().getClassInstance(PDFGenerator.class).createOnepdf( project, interfaceRelations, "Interface Relations", nameFile);
                    this.service().print_GREEN("Successfully generated the File");
                }else {
                    this.service().print_DARKGREEN(interfaceRelations);
                }

                this.subMenu2(project, scanner);

            } catch (Exception e) {
                this.service().print_RED("ERROR IN GENERATION OF PNG UmlDiagram");
                this.service().print_RED("ERROR: "+ e.getMessage());
               e.printStackTrace();
            }
        }else {
            this.subMenu2(project, scanner);
        }
    }

}
