package org.jenkinsci.plugins.vs_code_metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.vs_code_metrics.bean.*;
import org.jenkinsci.plugins.vs_code_metrics.util.CodeMetricsUtil;
import org.jenkinsci.plugins.vs_code_metrics.util.Constants;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.ExportedBean;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Result;

/**
 * @author Yasuyuki Saito
 */
@ExportedBean
public class VsCodeMetricsBuildAction implements Action, StaplerProxy {

    private final AbstractBuild<?,?> build;
    private final CodeMetrics result;
    private final CodeMetricsReport report;

    public VsCodeMetricsBuildAction(AbstractBuild<?, ?> build, CodeMetrics result) {
        this.build = build;
        this.result = result;
        this.report = new CodeMetricsReport(build, result);
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
        return report;
    }

    public AbstractBuild<?,?> getBuild() {
        return build;
    }

    public CodeMetrics getResult() {
        return result;
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
