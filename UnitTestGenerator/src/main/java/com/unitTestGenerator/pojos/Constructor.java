package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Constructor {

    private List<ParametroMetodo> parametros = new ArrayList<>();
    private Boolean isNoneParam;
    private String costructorSignature;
    private String costructorContent;
    private String rawConstructor;



    public Constructor() {
    }

    public String getCostructorContent() {
        return costructorContent;
    }

    public void setCostructorContent(String costructorContent) {
        this.costructorContent = costructorContent;
    }

    public String getCostructorSignature() {
        return costructorSignature;
    }

    public void setCostructorSignature(String costructorSignature) {
        this.costructorSignature = costructorSignature;
    }


    public List<ParametroMetodo> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroMetodo> parametros) {
        this.parametros = parametros;
    }

    public Boolean isNoneParam() {
        return isNoneParam;
    }

    public void setIsNoneParam(Boolean isNoneParam) {
        this.isNoneParam = isNoneParam;
    }

    public Boolean isEmptyParameters(){
        return this.parametros.isEmpty();
    }

    public String getRawConstructor() {
        return rawConstructor;
    }

    public void setRawConstructor(String rawConstructor) {
        this.rawConstructor = rawConstructor;
    }
}
