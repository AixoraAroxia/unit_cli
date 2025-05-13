package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ImportAnalizePojo {

    private List<String> externalImports = new ArrayList<>();
    private List<String> projectImports = new ArrayList<>();
    private String projectImportsMap;


    public ImportAnalizePojo() {
    }

    public ImportAnalizePojo(List<String> externalImports, List<String> projectImports) {
        if(externalImports != null && !externalImports.equals("")){
            this.externalImports = externalImports;
        }
        if(projectImports != null && !projectImports.equals("")) {
            this.projectImports = projectImports;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportAnalizePojo that = (ImportAnalizePojo) o;
        return Objects.equals(externalImports, that.externalImports) && Objects.equals(projectImports, that.projectImports) && Objects.equals(projectImportsMap, that.projectImportsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalImports, projectImports, projectImportsMap);
    }

    public List<String> getExternalImports() {
        return externalImports;
    }

    public void setExternalImports(List<String> externalImports) {
        this.externalImports = externalImports;
    }

    public List<String> getProjectImports() {
        return projectImports;
    }

    public void setProjectImports(List<String> projectImports) {
        this.projectImports = projectImports;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getProjectImportsMap() {
        return projectImportsMap;
    }

    public void setProjectImportsMap(String projectImportsMap) {
        this.projectImportsMap = projectImportsMap;
    }

    public interface ImportAnalizePojoBuilder {
        public Builder externalImports(List<String> externalImports);
        public Builder projectImports(List<String> projectImports);
        public Builder projectImportsMap(String imports);
        public ImportAnalizePojo build();
    }

    public static class Builder implements ImportAnalizePojoBuilder {
        private List<String> externalImports;
        private List<String> projectImports;
        private String projectImportsMap;

        @Override
        public Builder externalImports(List<String> externalImports) {
            this.externalImports = externalImports;
            return this;
        }

        @Override
        public Builder projectImports(List<String> projectImports) {
            this.projectImports = projectImports;
            return this;
        }

        @Override
        public Builder projectImportsMap(String imports) {
            this.projectImportsMap = imports;
            return this;
        }

        @Override
        public ImportAnalizePojo build() {
            ImportAnalizePojo importanalizepojo = new ImportAnalizePojo();
            importanalizepojo.setExternalImports(this.externalImports);
            importanalizepojo.setProjectImports(this.projectImports);
            importanalizepojo.setProjectImportsMap(this.projectImportsMap);
            return importanalizepojo;
        }
    }

}
