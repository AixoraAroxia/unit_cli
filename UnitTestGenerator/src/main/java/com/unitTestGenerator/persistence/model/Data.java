package com.unitTestGenerator.persistence.model;


import com.unitTestGenerator.ioc.anotations.Component;

import javax.persistence.*;

@Entity
@Table(name = "data")
@Component
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "datos")
    private String datos;

    public Data() {}

    public Data(Long id, String name, String descripcion, String datos) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.datos = datos;
    }

    public Data(String name, String descripcion, String datos) {
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
    public String toString() {
        return "" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + descripcion + '\'' +
                '}';
    }
}

