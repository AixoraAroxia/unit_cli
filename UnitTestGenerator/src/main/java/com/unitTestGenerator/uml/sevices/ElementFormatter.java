package com.unitTestGenerator.uml.sevices;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.uml.pojos.Conect;
import com.unitTestGenerator.uml.pojos.Element;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ElementFormatter {

    public List<String> formatElements(List<Element> elementos) {
        return elementos.stream()
                .map(this::formatElemento)
                .collect(Collectors.toList());
    }

    private String formatElemento(Element element) {
        return new StringBuilder()
                .append("{ name: \"")
                .append(escapeNull(element.getName()))
                .append("\", valor: \"")
                .append(escapeNull(element.getValor()))
                .append("\", variables: ")
                .append(formatList(element.getVariables()))
                .append(", metodos: ")
                .append(formatList(element.getMetodos()))
                .append(", relations: ")
                .append(formatList(element.getRelations()))
                .append(", conste: ")
                .append(formatConections(element.getConste()))
                .append(" }")
                .toString();
    }

    private String formatList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        return list.stream()
                .map(s -> "\"" + escapeNull(s) + "\"")
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatConections(List<Conect> conexiones) {
        if (conexiones == null || conexiones.isEmpty()) {
            return "[]";
        }
        return conexiones.stream()
                .map(c -> "{ conexion: \"" + escapeNull(c.getConexion())
                        + "\", move: " + c.isMove() + " }")
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String escapeNull(String value) {
        return value != null ? value : "";
    }

}
