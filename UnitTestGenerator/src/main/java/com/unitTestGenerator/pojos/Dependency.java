package com.unitTestGenerator.pojos;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Objects;

@Component
public class Dependency {

    private String groupId;
    private String artifactId;
    private String version;
    private String scope;

    public Dependency() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependency that = (Dependency) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(artifactId, that.artifactId) && Objects.equals(version, that.version) && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version, scope);
    }


    public static Builder builder() {
        return new Builder();
    }

    public interface DependencyBuilder {
        public Builder groupId(String groupId);
        public Builder artifactId(String artifactId);
        public Builder version(String version);
        public Builder scope(String scope);

        public Dependency build();
    }

    public static class Builder implements DependencyBuilder {
        private String groupId;
        private String artifactId;
        private String version;
        private String scope;

        @Override
        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        @Override
        public Builder artifactId(String artifactId) {
            this.artifactId = artifactId;
            return this;
        }

        @Override
        public Builder version(String version) {
            this.version = version;
            return this;
        }

        @Override
        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        @Override
        public Dependency build() {
            Dependency dependency = new Dependency();
            dependency.setGroupId(this.groupId);
            dependency.setArtifactId(this.artifactId);
            dependency.setVersion(this.version);
            dependency.setScope(this.scope);
            return dependency;
        }
    }

}
