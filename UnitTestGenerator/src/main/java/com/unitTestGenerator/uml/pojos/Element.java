package com.unitTestGenerator.uml.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Element {

    private String name;
    private String valor;
    private List<String> variables;
    private List<String> metodos;
    private List<String> relations;
    private List<Conect> conste;

    // Constructor
    public Element() {
        this.variables = new ArrayList<>();
        this.metodos = new ArrayList<>();
        this.relations = new ArrayList<>();
        this.conste = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public List<String> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<String> metodos) {
        this.metodos = metodos;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public List<Conect> getConste() {
        return conste;
    }

    public void setConste(List<Conect> conste) {
        this.conste = conste;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(name, element.name) && Objects.equals(valor, element.valor) && Objects.equals(variables, element.variables) && Objects.equals(metodos, element.metodos) && Objects.equals(relations, element.relations) && Objects.equals(conste, element.conste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, valor, variables, metodos, relations, conste);
    }

    @Override
    public String toString() {
        return "{ }";
    }
}

//  { name: "", valor: "", variables: [""], metodos: [""], relations: [""], conste: [ {conexion: "", move: false } ] }