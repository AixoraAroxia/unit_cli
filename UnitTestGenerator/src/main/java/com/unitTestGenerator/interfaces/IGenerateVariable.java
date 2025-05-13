package com.unitTestGenerator.interfaces;

import com.unitTestGenerator.util.interfaces.IBaseModel;

public interface IGenerateVariable extends IBaseModel {

    default  String generateVariable(String type, String name, boolean isClass, boolean useMock) {
        StringBuilder content = new StringBuilder();
        if(type != null && name != null) {
            String notation = useMock ? (isClass ? "@InjectMocks" : "@Mock") : (isClass ? "@Autowired" : "");
            String nameVariable = stringEnsamble(name.substring(0, 1).toLowerCase(), name.substring(1));
            String variableDeclaration = String.format("private %s %s;", type, nameVariable);
            if (!notation.isEmpty()) {
                content.append("\t").append(notation).append("\n");
            }
            content.append("\t").append(variableDeclaration).append("\n");
        }
        return content.toString();
    }
}
