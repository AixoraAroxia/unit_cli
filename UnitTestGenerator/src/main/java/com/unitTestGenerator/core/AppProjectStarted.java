package com.unitTestGenerator.core;


import com.unitTestGenerator.analyzers.services.AnalizerProjectService;
import com.unitTestGenerator.analyzers.services.interfaces.IAnalizerProjectServiceManager;

import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.menus.AnalizerMenu;
import com.unitTestGenerator.menus.interfaces.MainMenue;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.util.interfaces.ICleanConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Singleton
public class AppProjectStarted implements MainMenue, IAnalizerProjectServiceManager {

    private AnalizerProjectService analizerProjectService;
    private GeneradorPruebasUnitarias generadorPruebasUnitarias;
    private ProjectHolder projectHolder;
    private Project project;

    public AppProjectStarted( GeneradorPruebasUnitarias generadorPruebasUnitarias, ProjectHolder projectHolder) {
        this.projectHolder = projectHolder;
        this.generadorPruebasUnitarias = generadorPruebasUnitarias;
        this.project = this.projectHolder.getProject();
        if(!this.project.getClaseList().isEmpty() && this.project.getClaseList().get(0) != null && this.project.getClaseList().get(0).getNombre() == null){
            this.project.getClaseList().remove(0);
        }
        this.analizerProjectService = getInstanceAPSI();
    }


    private void checkProyect(){
        Project temp = this.projectHolder.getProject();
        if(temp != null && temp.getPathProject() != null && !temp.getPathProject().equals("") && !temp.getClaseList().isEmpty()){
            this.project = temp;
        }
    }

    public void start() {
        ICleanConsole.clearConsoleOs();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        this.checkProyect();
        while (continuar) {
            this.welcomeMenu();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    this.projectHolderLogic( scanner, true);
//                    continuar = this.askContinue(scanner);
                        continuar = false;
                    break;
                case 2:
                    generateUnitsTest(scanner);
                    continuar = this.askContinue(scanner);
                    break;
                case 3:
                    System.out.println("Good bye");
                    continuar = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    continuar = this.askContinue(scanner);
            }
        }
    }

    private void projectHolderLogic(Scanner scanner, Boolean isAnalisis){

        Project projectAnalized = null;
        if(isAnalisis){
            AnalizerMenu analizerMenu =  ContextIOC.getInstance().getClassInstance(AnalizerMenu.class);
            analizerMenu.analizerMenuStarted(this.project, new Scanner(System.in));
            projectAnalized = this.projectHolder.getProject();
            this.project = projectAnalized;
        }else {
            projectAnalized = analizerProjectService.analizerProject(scanner, isAnalisis, this.project);
            this.projectHolder.setProject(projectAnalized);
            this.project = projectAnalized;
        }

    }

    private void generateUnitsTest(Scanner scanner) {
        if (this.project.getClaseList().isEmpty()) {
            this.projectHolderLogic( scanner, false);
        }

        System.out.println("Enter the class name to test to generate the unit tests:");
        String nombreClase = scanner.next();
        String method = this.questionOfMethodToTest(scanner);
        if (method.equals("CANCEL")) {
            this.askContinue(scanner);
        }
        Boolean useMock = this.questionAboutUseMock(scanner);
        this.generateUnitTest(nombreClase, method, useMock);
    }

    private void generateUnitTest(String nombreClase, String method, Boolean useMock) {
        List<Clase> tempClass = new ArrayList<>();
        Clase claseEncontrada = null;

        if (this.project.getClaseList() != null && !this.project.getClaseList().isEmpty()) {
            this.project.getClaseList().stream().forEach(clase -> {
                if (clase.getNombre() != null && clase.getNombre().equals(nombreClase)) {
                    tempClass.add(clase);
                }
            });

            try {
                GeneradorPruebasUnitarias generate = this.generadorPruebasUnitarias;
                generate.setProject(this.project);
                claseEncontrada = tempClass.get(0);
                claseEncontrada.setUseMock(useMock);
                claseEncontrada.setTestMethod(method);
                generate.generateTest(claseEncontrada, this.project.getPathProject());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Class not found");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }else {
            System.out.println("No class in project for generate the unit test");
            this.askContinue( new Scanner(System.in));
        }
    }


    public List<Clase> getClases() {
        return this.project.getClaseList();
    }

    public String getRutaProyecto() {
        return this.project.getPathProject();
    }

}
