package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Objects;

@Component
public class Variable {

    private String tipo;
    private String nombre;
    private Boolean isMock;
    private String anotation;
    private String accessModifier;


    public Variable() {
        this.isMock = true;
    }

    public Boolean getMock() {
        return isMock;
    }

    public void setMock(Boolean mock) {
        isMock = mock;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Override
    public String toString() {
        String anota ="";
        if(anotation != null && !anotation.equals("")) {
            anota =   String.format("\t%s\n",anotation); //"\t" + anotation + "\n";
        }
        return String.format("%s\t%s %s %s;", anota, accessModifier, tipo, nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(tipo, variable.tipo) &&
                Objects.equals(nombre, variable.nombre) &&
                Objects.equals(anotation, variable.anotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, nombre, anotation);
    }
}
