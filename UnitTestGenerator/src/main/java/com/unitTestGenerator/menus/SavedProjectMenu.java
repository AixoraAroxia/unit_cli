package com.unitTestGenerator.menus;

import com.unitTestGenerator.analyzers.services.AnalizerProjectService;
import com.unitTestGenerator.core.ProjectHolder;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.persistence.model.DataPojo;
import com.unitTestGenerator.persistence.repositories.DataDAOServicesImplement;
import com.unitTestGenerator.persistence.repositories.IDaoService;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.printers.interfaces.IPrintService;
import com.unitTestGenerator.util.ObjectTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class SavedProjectMenu implements IPrintService {

//    V1.1.0.0  In progress....

    private ObjectTransformer objectTransformer;
    public static IDaoService dao;
    private ProjectHolder projectHolder;
    private AnalizerProjectService analizerProjectService;


    public SavedProjectMenu(ObjectTransformer objectTransformer, ProjectHolder projectHolder, AnalizerProjectService analizerProjectService) {
        this.objectTransformer = objectTransformer;
        this.projectHolder = projectHolder;
        this.analizerProjectService = analizerProjectService;
        this.dao =  ContextIOC.getInstance().getClassInstance(DataDAOServicesImplement.class);
    }

    public Project started(Scanner scanner){
        Project project = null;
        this.service().print_YELLOW("List saved projects: ");
        this.printList();
        this.service().print_YELLOW("Enter the project id from the list:");
        Long id = scanner.nextLong();

        if(id != null){
            project = getProjectById(id);
        }

        if(project != null){
            boolean ask = this.askAnalyzeAgain(scanner);
            if(ask){
                Project project1 = analyzeAgain( project, scanner);
                this.updateData(project1);
                project = project1;
            }

        }else {
            this.service().print_RED("You entered an incorrect ID, or the project is corrupted in the database...");
            this.service().print_YELLOW("...");
            this.service().print_YELLOW("It is recommended to perform a new analysis. To do so, we will return to the previous menu...");
            this.service().print_YELLOW("...");
        }
        return project;
    }



    public Project analyzeAgain(Project project, Scanner scanner){
        Project projectAnalized  = analizerProjectService.analizerProjectSaveProject( true, project);
        this.projectHolder.setProject(projectAnalized);
        return  this.projectHolder.getProject();
    }


    private boolean askAnalyzeAgain(Scanner scanner) {
        this.service().print_YELLOW("Do you want to analyze again? (y/n)");
        String response = scanner.next().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Invalid response. Please enter 'y' for yes or 'n' for no.");
            response = scanner.next().toLowerCase();
        }
        return response.equals("y");
    }



    public Boolean saveData(Project project){
        try {
            this.dao.save(projectToDataPojo(project));
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public Boolean updateData(Project project){
        try {
            this.dao.update(projectToDataPojo(project));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public DataPojo projectToDataPojo(Project project){
        return new DataPojo(project.getName(), project.getDescription(), objectTransformer.objectToString(project));
    }


    public List<String> listProject(){
        List<String> listProject = new ArrayList<>();
        try {
            List<DataPojo> dataList = this.dao.findAll();
            dataList.forEach(dataPojo -> listProject.add(dataPojo.toString()));
            return listProject;
        }catch (Exception e){
            e.printStackTrace();
            return listProject;
        }
    }

    public void printList(){
        List<String> list = this.listProject();
        if(list != null && !list.isEmpty()){
            int i = 1;
            for (String element: list){
                this.service().print_DARKGREEN(i+") "+element);
                i++;
            }
        }else {
            this.service().print_RED("");
        }
    }

    public Project getProjectById(Long id){
        Project project = null;
        try {
            DataPojo data = this.dao.findById(id);
            if(data != null && data.getDatos() != null && !data.getDatos().equals("")){
                project =  this.objectTransformer.stringToObject(data.getDatos());
            }
            return project;
        }catch (Exception e){
            e.printStackTrace();
            return project;
        }
    }




}
