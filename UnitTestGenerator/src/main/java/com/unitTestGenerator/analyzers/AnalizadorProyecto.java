package com.unitTestGenerator.analyzers;

import com.unitTestGenerator.analyzers.services.AnalyzeClassServiceService;
import com.unitTestGenerator.analyzers.services.ImportAnalizeService;
import com.unitTestGenerator.analyzers.services.interfaces.ITodoDetectorService;
import com.unitTestGenerator.builders.TemplateBuilder;
import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.pojos.Project;
import com.unitTestGenerator.printers.DirectoryTreeBuilder;
import com.unitTestGenerator.printers.interfaces.IPrintProjectStructure;

import java.io.File;
import java.util.*;

@Component
@Singleton
public class AnalizadorProyecto implements ITodoDetectorService, IPrintProjectStructure {

    private  final String[] IGNORAR = {"target", "node_modules", ".git"};

    @Inyect
    private DirectoryTreeBuilder treeBuilder;

    @Inyect
    private AnalyzeClassServiceService analyzeClassServiceService;

    @Inyect
    private ImportAnalizeService importAnalizeService;

    public AnalizadorProyecto() {
    }

    public List<Clase> analizarProyecto(String rutaProyecto, Project project) {

        List<Clase> clases = new ArrayList<>();
        Map<String, Clase> mapClass = new HashMap<>();
        treeBuilder.setProjetName(rutaProyecto);
        File carpetaProyecto = new File(rutaProyecto);
        this.analizarProyectoRecursivo(carpetaProyecto, clases, mapClass, project);
        project.setMapClass(mapClass);
        String classDirectoryTree = treeBuilder.getTreeString();
        project.getPrinterProject().setProjectClassTree(classDirectoryTree.replace(".java", " "));
        String projectDirectoryTree = getStructure(project.getPathProject());
        project.getPrinterProject().setProjectDirectoryTree(projectDirectoryTree);
        return clases;
    }



      private void analizarProyectoRecursivo(File dir, List<Clase> classList, Map<String, Clase> mapClass, Project project) {

        List<String> IGNORED_FOLDERS = Arrays.asList("target", ".idea", ".git");
          File[] files = dir.listFiles();
          if (files != null) {
              for (File file : files) {
                  if (IGNORED_FOLDERS.contains(file.getName())) {
                      continue;
                  }
                  if (file.isDirectory()) {
                      this.analizarProyectoRecursivo(file, classList, mapClass, project);
                  }else {
                      if(file.getName().trim().contains(".java")) {
                          Clase clase = this.analyzeClassServiceService.analyzeClase(file);
                          if (clase != null) {
                              clase.setTodoNoteInClass(this.getTodo(clase.getRawClass()));
                              this.treeBuilder.addPath(clase.getClassPath());
                              if(clase != null && (clase.getPaquete() !=null &&  !clase.getPaquete().equals(""))){
                                this.importAnalizeService.importAnalize(clase);
                              }
                              ContextIOC.getInstance().getClassInstance(TemplateBuilder.class).buildClassDetailHtml(clase);
                            if(clase.getMainClass()){
                                project.setMainClassName(clase.getNombre());
                            }
                          }
                          this.setContainers(clase, classList, mapClass, project);
                      }
                  }
              }
          }
    }


//    private  void analizarProyectoRecursivo2(File carpeta, List<Clase> classList, Map<String, Clase> mapClass, Project project) {
//
//        if (carpeta.isDirectory()) {
//            String nombreCarpeta = carpeta.getName();
//
//            if (!Arrays.asList(IGNORAR).contains(nombreCarpeta)) {
//                for (File archivo : carpeta.listFiles()) {
//                    if (archivo.isDirectory()) {
//                        this.analizarProyectoRecursivo(archivo, classList, mapClass, project);
//                    } else {
//                        if(archivo.getName().trim().endsWith(".java")) {
//                            Clase clase = analyzeClassServiceService.analyzeClase(archivo);
//                            if (clase != null) {
//                                clase.setTodoNoteInClass(this.getTodo(clase.getRawClass()));
//                                this.treeBuilder.addPath(clase.getClassPath());
//                                this.importAnalizeService.importAnalize(clase);
//                            }
//                            this.setContainers(clase, classList, mapClass);
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void setContainers(Clase clase, List<Clase> classList, Map<String, Clase> mapClass, Project project){

        if(clase !=null) {
            clase.updateNode(null);

            if (classList != null) {
                classList.add(clase);
                project.getPrinterProject().addToClaseList(clase.getNombre());
                project.getClaseListRaw().add(clase.getRawClass());
            }

            if (mapClass != null) {
                mapClass.put(clase.getNombre(), clase);
            }
        }
    }

}
