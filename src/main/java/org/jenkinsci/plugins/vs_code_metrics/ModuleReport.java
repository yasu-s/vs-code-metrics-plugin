package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.Module;


public final class ModuleReport extends AbstractReport {

    private final Module result;

    /**
     *
     * @param build
     * @param result
     */
    public ModuleReport(AbstractBuild<?,?> build, Module result) {
        this.result = result;
        setBuild(build);
        setName(result.getName());
    }

    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public Object getReport(String token) {
        if ((result != null) && result.getChildren().containsKey(token))
            return null;
        else
            return this;
    }

    @Override
    public boolean hasChildren() {
        return ((result != null) && (result.getChildren().size() > 0));
    }

    @Override
    public Object getChildren() {
        return result.getChildren();
    }

}
