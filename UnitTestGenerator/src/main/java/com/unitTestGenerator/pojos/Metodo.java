package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Component
public class Metodo {

    private String nombre;
    private String tipoRetorno;
    private List<ParametroMetodo> parametros= new ArrayList<>();
    private String contenido;
    private List<InstanceMethodCall> instanceMethodCalls = new ArrayList<>();
    private String accessModifier;
    private String anotation;
    private String methodSignature;
    private Boolean isMain;


    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Metodo() {
        this.isMain = false;
    }


    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public void agregarParametro(ParametroMetodo parametro) {
        this.parametros.add(parametro);
    }

    public void setParametros(List<ParametroMetodo> parametros) {
        this.parametros = parametros;
    }

    public List<ParametroMetodo> getParametros() {
        return parametros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public List<InstanceMethodCall> getInstanceMethodCalls() {
        return instanceMethodCalls;
    }

    public void setInstanceMethodCalls(List<InstanceMethodCall> instanceMethodCalls) {
        if(instanceMethodCalls != null && !instanceMethodCalls.isEmpty()) {
            this.instanceMethodCalls = instanceMethodCalls;
        }
    }

    public void addInstanceMethodCall(InstanceMethodCall instanceMethodCall){
        if(instanceMethodCalls != null) {
            this.instanceMethodCalls.add(instanceMethodCall);
        }
    }

    public void addInstanceMethodCallAll(List<InstanceMethodCall> insCalls){
        if(insCalls != null && !insCalls.isEmpty()){
            this.instanceMethodCalls.addAll(insCalls);
        }

    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metodo metodo = (Metodo) o;
        return Objects.equals(nombre, metodo.nombre) &&
                Objects.equals(tipoRetorno, metodo.tipoRetorno) &&
                Objects.equals(parametros, metodo.parametros) &&
                Objects.equals(contenido, metodo.contenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipoRetorno, parametros, contenido);
    }


    @Override
    public String toString() {
            String anota ="";
            if(anotation != null && !anotation.equals("")) {
                anota =   String.format("\t%s\n",anotation); //"\t" + anotation + "\n";
            }

        if (!parametros.isEmpty()){
           return String.format("%s\t%s{ \n%s \n{", anota, methodSignature, contenido);
        }else {
            String[] partsOfSignatire = methodSignature.split("\\(");

            String parametrosString = parametros.stream()
                    .map(ParametroMetodo::toString)
                    .collect(Collectors.joining(", "));
            return String.format("%s\t%s(%s){\n \t%s\n}", anota, partsOfSignatire[0], parametrosString, contenido);
        }

    }
}
