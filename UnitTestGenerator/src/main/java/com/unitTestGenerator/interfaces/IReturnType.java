package com.unitTestGenerator.interfaces;


import com.unitTestGenerator.ioc.ContextIOC;
import com.unitTestGenerator.util.random.servicesRandom.IRandomService;

import com.unitTestGenerator.util.random.sevicesrandomimplement.IRandomServiceImplemet;


public interface IReturnType extends IValueExtractor{

   public

    default String getDefaultValueName(){
        return "\""+ ContextIOC.getInstance().getClassInstance(IRandomServiceImplemet.class).generateRandomName()+"\"";
    }

    default String getDefaultValueCode(){
        return "\""+ ContextIOC.getInstance().getClassInstance(IRandomServiceImplemet.class).generateCode()+"\"";
    }

    default String getDefaultValueDescription(Integer number){
        return  "\""+ ContextIOC.getInstance().getClassInstance(IRandomServiceImplemet.class).generateRandomRealWordText(number)+"\"";
    }

    default String getDefaultValue(String tipoRetorno) {
        IRandomService iRandomService = ContextIOC.getInstance().getClassInstance(IRandomServiceImplemet.class);
        switch (tipoRetorno) {
            case "int":
                return this.toString(iRandomService.generateRandomNumber(2, 10) , " ");
            case "long":
                return this.toString(iRandomService.generatePositiveRandomLong(1l, 10l) , "L");
            case "float":
                return this.toString(iRandomService.getRandomNumeroDouble(0.0, 99.9),"f");
            case "AtomicBoolean":
            case "boolean":
            case "Boolean":
                return this.toString(iRandomService.generateRamdonBoolean() , "");
            case "String":
                return "\""+ this.toString(iRandomService.generateRandomText(10), null)+"\"";
            case "Integer":
                return this.toString(iRandomService.generateRandomNumber(8) , "");
            case "Long":
                return this.toString(iRandomService.generatePositiveRandomLong(1l, 20l), "L");
            case "double":
            case "Double":
                return this.toString(iRandomService.getRandomNumeroDouble(0.0, 20.9) ,"");
            case "Float":
                return this.toString(iRandomService.getRandomNumeroDouble(0.0, 99.9) ,"F");
            case "Date":
                return this.toString(iRandomService.generateRandomDate(),"");
            default:
                return "null";
        }
    }






    default boolean isValidTypeReturn(String typeReturn) {
        switch (typeReturn) {
            case "int":
            case "long":
            case "double":
            case "float":
            case "boolean":
            case "AtomicBoolean":
            case "String":
            case "Integer":
            case "Long":
            case "Double":
            case "Float":
            case "Boolean":
            case "Date":
                return true;
            default:
                return false;
        }
    }


    default String getAssertType(String tipoRetorno, String valorDeRetorno, String result) {

        switch (tipoRetorno) {
            case "int":
            case "long":
            case "double":
            case "float":
            case "Integer":
            case "Long":
            case "Double":
            case "Float":
            case "String":
                return "Assertions.assertThat(" + valorDeRetorno + ").isEqualTo("+result+");";
            case "boolean":
                if (valorDeRetorno.equals("true")) {
                    return "Assertions.assertThat("+result+").isTrue();";
                } else {
                    return "Assertions.assertThat("+result+").isFalse();";
                }
            default:
                if (valorDeRetorno == null) {
                    return "Assertions.assertThat("+result+").isNull();";
                } else {
                    return "Assertions.assertThat("+result+").isNotNull();";
                }
        }
    }


    default String getAssertTypeNull(String valorDeRetorno, String result) {
                if (valorDeRetorno == null) {
                    return "Assertions.assertThat("+result+").isNull();";
                } else {
                    return "Assertions.assertThat("+result+").isNotNull();";
                }
    }

    default <T> String toString(T object, String type) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(object == null? "null": buffer.append(object));
        buffer.append(type != null && !type.equals("")?type:"");
        return buffer.toString();
    }

}
/*
	Assertions.assertThat(resultado).isFalse();
	Assertions.assertThat(resultado).isEqualTo(resultado);
	Assertions.assertThat(resultado).isNotEqualTo(resultado);
	Assertions.assertThat(resultado).isNotNull();
	Assertions.assertThat(resultado).isNull();
* */
