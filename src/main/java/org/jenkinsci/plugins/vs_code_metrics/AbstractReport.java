package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;
import hudson.model.ModelObject;

import java.io.Serializable;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public abstract class AbstractReport implements Serializable, ModelObject {

    private static final long serialVersionUID = 1L;

    private AbstractBuild<?,?> build;
    private String name;
    private boolean depthOfInheritance = true;

    public void setBuild(AbstractBuild<?, ?> build) {
        this.build = build;
    }

    public AbstractBuild<?, ?> getBuild() {
        return build;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

    public boolean isDepthOfInheritance() {
        return depthOfInheritance;
    }

    public void setDepthOfInheritance(boolean depthOfInheritance) {
        this.depthOfInheritance = depthOfInheritance;
    }

    public Object getDynamic(String token, StaplerRequest req, StaplerResponse rsp) {
        return getReport(token);
    }

    public abstract Object getReport(String token);

    public abstract Object getChildren();

}
