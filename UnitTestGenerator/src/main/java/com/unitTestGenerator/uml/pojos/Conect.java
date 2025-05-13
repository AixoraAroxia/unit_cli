package com.unitTestGenerator.uml.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Objects;

@Component
public class Conect {

        private String conexion;
        private boolean move;


        public Conect() {}

    public String getConexion() {
        return conexion;
    }

    public void setConexion(String conexion) {
        this.conexion = conexion;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conect conect = (Conect) o;
        return move == conect.move && Objects.equals(conexion, conect.conexion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conexion, move);
    }

    @Override
    public String toString() {
        return "{ }";
    }

}

