package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Objects;

@Component
public class TestFileContent {

    private String testsClassImport;
    private String testsClassSingne;
    private String testsClassVariables;
    private String testsClassMethods;



    public TestFileContent() {
    }


    public String getTestsClassImport() {
        return testsClassImport;
    }

    public void setTestsClassImport(String testsClassImport) {
        this.testsClassImport = testsClassImport;
    }

    public String getTestsClassSingne() {
        return testsClassSingne;
    }

    public void setTestsClassSingne(String testsClassSingne) {
        this.testsClassSingne = testsClassSingne;
    }

    public String getTestsClassVariables() {
        return testsClassVariables;
    }

    public void setTestsClassVariables(String testsClassVariables) {
        this.testsClassVariables = testsClassVariables;
    }

    public String getTestsClassMethods() {
        return testsClassMethods;
    }

    public void setTestsClassMethods(String testsClassMethods) {
        this.testsClassMethods = testsClassMethods;
    }

    public void addImport(String newImport) {
        StringBuffer newContent = new StringBuffer(this.testsClassImport);
        newContent.append("\n").append(newImport).append("\n");
        this.testsClassImport = newContent.toString();
    }

    public void addVariable(String newVariables) {
        StringBuffer newContent = new StringBuffer( this.testsClassVariables);
        newContent.append("\n").append(newVariables).append("\n");
        this.testsClassVariables = newContent.toString();
    }

    public void addMethod(String newMethods) {
        StringBuffer newContent = new StringBuffer( this.testsClassMethods);
        newContent.append("\n").append(newMethods).append("\n");
        this.testsClassMethods = newContent.toString();
    }

    @Override
    public String toString() {
        StringBuffer filetoString = new StringBuffer("\n");
        filetoString.append(this.getTestsClassImport()).append("\n").append("\n");
        filetoString.append(this.testsClassSingne).append("\n");
        filetoString.append(this.testsClassVariables).append("\n");
        filetoString.append(this.testsClassMethods).append("\n");
        filetoString.append("}\n");
        return filetoString.toString();
    }


    public String toStringTemplate() {
        StringBuffer filetoString = new StringBuffer("\n");
        filetoString.append(this.getTestsClassImport()).append("\n").append("\n");
        filetoString.append(this.testsClassSingne).append("\n");
        filetoString.append("@ClassVariables@").append("\n");
        filetoString.append("@ClassMethods@").append("\n");
        filetoString.append("}\n");
        return filetoString.toString();
    }

    public static TestFileContent getNewInstance( TestFileContent old){
        return TestFileContent.builder()
                .testsClassImport(old.getTestsClassImport())
                .testsClassSingne(old.getTestsClassSingne())
                .testsClassVariables(old.getTestsClassVariables())
                .testsClassMethods(old.getTestsClassMethods())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }


    public interface TestFileContentBuilder {
        public Builder testsClassImport(String testsClassImport);
        public Builder testsClassSingne(String testsClassSingne);
        public Builder testsClassVariables(String testsClassVariables);
        public Builder testsClassMethods(String testsClassMethods);

        public TestFileContent build();
    }

    public static class Builder implements TestFileContentBuilder {
        private String testsClassImport;
        private String testsClassSingne;
        private String testsClassVariables;
        private String testsClassMethods;

        @Override
        public Builder testsClassImport(String testsClassImport) {
            this.testsClassImport = testsClassImport;
            return this;
        }

        @Override
        public Builder testsClassSingne(String testsClassSingne) {
            this.testsClassSingne = testsClassSingne;
            return this;
        }

        @Override
        public Builder testsClassVariables(String testsClassVariables) {
            this.testsClassVariables = testsClassVariables;
            return this;
        }

        @Override
        public Builder testsClassMethods(String testsClassMethods) {
            this.testsClassMethods = testsClassMethods;
            return this;
        }

        @Override
        public TestFileContent build() {
            TestFileContent testfileContent = new TestFileContent();
            testfileContent.setTestsClassImport(this.testsClassImport);
            testfileContent.setTestsClassSingne(this.testsClassSingne);
            testfileContent.setTestsClassVariables(this.testsClassVariables);
            testfileContent.setTestsClassMethods(this.testsClassMethods);
            return testfileContent;
        }
    }



    public void update(Object o) {
        TestFileContent that = (TestFileContent) o;

        if(!Objects.equals(this.testsClassImport, that.testsClassImport) ){
            this.testsClassImport = that.testsClassImport;
        }
        if(!Objects.equals(this.testsClassSingne, that.testsClassSingne)){
            this.testsClassSingne= that.testsClassSingne;
        }
        if(!Objects.equals(this.testsClassVariables, that.testsClassVariables)){
            this.testsClassVariables = that.testsClassVariables;
        }
        if(!Objects.equals(this.testsClassMethods, that.testsClassMethods)){
            this.testsClassMethods = that.testsClassMethods;
        }
    }


}
