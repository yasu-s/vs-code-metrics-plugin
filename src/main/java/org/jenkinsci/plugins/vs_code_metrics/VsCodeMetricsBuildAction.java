package org.jenkinsci.plugins.vs_code_metrics;

import java.io.IOException;

import org.jenkinsci.plugins.vs_code_metrics.bean.*;
import org.jenkinsci.plugins.vs_code_metrics.util.CodeMetricsUtil;
import org.jenkinsci.plugins.vs_code_metrics.util.Constants;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Result;

/**
 * @author Yasuyuki Saito
 */
public class VsCodeMetricsBuildAction implements Action, StaplerProxy {

    private final AbstractBuild<?,?> build;

    public VsCodeMetricsBuildAction(AbstractBuild<?, ?> build) {
        this.build = build;
    }

    public String getIconFileName() {
        return Constants.ACTION_ICON;
    }

    public String getDisplayName() {
        return Messages.VsCodeMetricsBuildAction_DisplayName();
    }

    public String getUrlName() {
        return Constants.ACTION_URL;
    }

    public Object getTarget() {
        return getReport();
    }

    public AbstractBuild<?,?> getBuild() {
        return build;
    }

    private CodeMetricsReport getReport() {
        try {
            CodeMetrics result = CodeMetricsUtil.getCodeMetrics(build);
            return new CodeMetricsReport(build, result);
        } catch (InterruptedException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public VsCodeMetricsBuildAction getPreviousResult() {
        AbstractBuild<?,?> b = build;
        while(true) {
            b = b.getPreviousBuild();
            if(b==null)
                return null;
            if(b.getResult()== Result.FAILURE)
                continue;
            VsCodeMetricsBuildAction r = b.getAction(VsCodeMetricsBuildAction.class);
            if(r!=null)
                return r;
        }
    }

    public void doGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
        // TODO: Create Graph
    }
}
