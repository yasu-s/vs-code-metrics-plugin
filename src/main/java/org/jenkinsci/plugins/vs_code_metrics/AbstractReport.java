package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;
import hudson.model.ModelObject;

import java.io.Serializable;
import java.util.Map;

import org.jenkinsci.plugins.vs_code_metrics.bean.AbstractBean;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public abstract class AbstractReport implements Serializable, ModelObject {

    private static final long serialVersionUID = 1L;

    private AbstractBuild<?,?> build;
    private String name;
    private AbstractBean<?> result;
    private boolean depthOfInheritance = true;
    private boolean childUrlLink = true;

    /**
     *
     * @param build
     * @param name
     * @param result
     */
    protected AbstractReport(AbstractBuild<?,?> build, String name, AbstractBean<?> result) {
        this.build = build;
        this.name = name;
        this.result = result;
    }

    public AbstractBuild<?, ?> getBuild() {
        return build;
    }

    public String getName() {
        return name;
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
    public boolean isChildUrlLink() {
        return childUrlLink;
    }

    public void setChildUrlLink(boolean childUrlLink) {
        this.childUrlLink = childUrlLink;
    }

    public Object getDynamic(String token, StaplerRequest req, StaplerResponse rsp) {
        return getReport(token);
    }



    public Object getResult() {
        return result;
    }

    public boolean hasChildren() {
        return ((result != null) && (result.getChildren().size() > 0));
    }

    public Map<String, ?> getChildren() {
        return result.getChildren();
    }

    public abstract Object getReport(String token);

}
