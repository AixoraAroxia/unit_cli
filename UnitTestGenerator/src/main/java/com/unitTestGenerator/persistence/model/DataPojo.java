package com.unitTestGenerator.persistence.model;


import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Objects;

@Component
public class DataPojo {

    private Long id;
    private String name;
    private String descripcion;
    private String datos;

    public DataPojo() {
    }

    public DataPojo(String name, String descripcion, String datos) {
        this.name = name;
        this.descripcion = descripcion;
        this.datos = datos;
    }

    public DataPojo(Long id, String name, String descripcion, String datos) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.datos = datos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPojo)) return false;
        DataPojo dataPojo = (DataPojo) o;
        return Objects.equals(id, dataPojo.id) && Objects.equals(name, dataPojo.name) && Objects.equals(descripcion, dataPojo.descripcion) && Objects.equals(datos, dataPojo.datos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, descripcion, datos);
    }


    @Override
    public String toString() {
        return "" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + descripcion + '\'' +
                '}';
    }
}
