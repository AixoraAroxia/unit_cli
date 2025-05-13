package com.unitTestGenerator.analyzers.interfaces;

import com.unitTestGenerator.util.interfaces.IConstantModel;
import com.unitTestGenerator.util.interfaces.IStringEnsambleService;

import java.util.Arrays;
import java.util.List;

public interface IGradleAnalyzer extends IStringEnsambleService {


    default String listStringStructureToColummString(List<String> parameters) {
        StringBuilder stringColumm = new StringBuilder(IConstantModel.BREAK_LINE);
        if (parameters != null && parameters.size() > 0) {
            for(int i = 0; i < parameters.size(); ++i) {
                stringColumm.append(this.stringEnsamble(Arrays.asList((String)parameters.get(i))));
                if (i < parameters.size()) {
                    stringColumm.append(IConstantModel.BREAK_LINE);
                }
            }
        }
        return stringColumm.toString();
    }

    default  String indentation(Integer indentationLevel){
        StringBuilder indentation = new StringBuilder();
        if(indentationLevel > 0){
            int index = 0;
            while(index < indentationLevel) {
                indentation.append("\t");
                index++;
            }
        }
        return indentation.toString();
    }


    }
