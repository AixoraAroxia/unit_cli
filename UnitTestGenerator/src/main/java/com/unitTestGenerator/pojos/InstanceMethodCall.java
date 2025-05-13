package com.unitTestGenerator.pojos;


import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstanceMethodCall {

    private String method;
    private String variableInstace;
    private List<ParametroMetodo> parametros = new ArrayList();
    private String operation;

    public InstanceMethodCall() {
    }

    public InstanceMethodCall(String method, String variableInstace, List<ParametroMetodo> parametros, String operation) {
        super();
        this.method = method;
        this.variableInstace = variableInstace;
        this.parametros = parametros;
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVariableInstace() {
        return variableInstace;
    }

    public void setVariableInstace(String variableInstace) {
        this.variableInstace = variableInstace;
    }

    public List<ParametroMetodo> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroMetodo> parametros) {
        this.parametros = parametros;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    public static Builder builder() {
        return new Builder();
    }

    public interface InstanceMethodCallBuilder {
        public Builder method(String method);
        public Builder variableInstace(String variableInstace);
        public Builder parametros(List<ParametroMetodo> parametros);
        public Builder operation(String operation);

        public InstanceMethodCall build();
    }

    public static class Builder implements InstanceMethodCallBuilder {
        private String method;
        private String variableInstace;
        private List<ParametroMetodo> parametros;
        private String operation;

        @Override
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        @Override
        public Builder variableInstace(String variableInstace) {
            this.variableInstace = variableInstace;
            return this;
        }

        @Override
        public Builder parametros(List<ParametroMetodo> parametros) {
            if(parametros != null){
                this.parametros = parametros;
            }else {
                parametros = new ArrayList<>();
            }
            return this;
        }

        @Override
        public Builder operation(String operation) {
            this.operation = operation;
            return this;
        }

        @Override
        public InstanceMethodCall build() {
            InstanceMethodCall instancemethodcall = new InstanceMethodCall();
            instancemethodcall.setMethod(this.method);
            instancemethodcall.setVariableInstace(this.variableInstace);
            instancemethodcall.setParametros(this.parametros);
            instancemethodcall.setOperation(this.operation);
            return instancemethodcall;
        }
    }

}


