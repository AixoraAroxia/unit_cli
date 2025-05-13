package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

@Component
public class ParametroMetodo {
    private String nombre;
    private String tipo;

    public ParametroMetodo() {
    }

    public ParametroMetodo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static Builder builder() {
        return new Builder();
    }


    public interface ParametroMetodoBuilder {
        public Builder nombre(String nombre);
        public Builder tipo(String tipo);
        public ParametroMetodo build();
    }

    public static class Builder implements ParametroMetodoBuilder {
        private String nombre;
        private String tipo;

        @Override
        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        @Override
        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        @Override
        public ParametroMetodo build() {
            ParametroMetodo parametrometodo = new ParametroMetodo();
            parametrometodo.setNombre(this.nombre);
            parametrometodo.setTipo(this.tipo);
            return parametrometodo;
        }
    }

    @Override
    public String toString() {
        return tipo + " " + nombre;
    }
}

