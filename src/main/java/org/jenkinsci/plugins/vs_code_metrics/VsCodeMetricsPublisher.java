package org.jenkinsci.plugins.vs_code_metrics;

import java.io.IOException;
import java.io.PrintStream;

import org.jenkinsci.plugins.vs_code_metrics.util.CodeMetricsUtil;
import org.jenkinsci.plugins.vs_code_metrics.util.Constants;
import org.jenkinsci.plugins.vs_code_metrics.util.StringUtil;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

/**
 * @author Yasuyuki Saito
 */
public class VsCodeMetricsPublisher extends Recorder {

    private final String reportFiles;
    private final VsCodeMetricsThresholds thresholds;
    private final boolean failBuild;

    @DataBoundConstructor
    public VsCodeMetricsPublisher(String reportFiles, int minMaintainabilityIndex, int maxMaintainabilityIndex, boolean failBuild) {
        this.reportFiles = reportFiles;
        this.failBuild   = failBuild;

        if (minMaintainabilityIndex >= maxMaintainabilityIndex)
            this.thresholds = new VsCodeMetricsThresholds(Constants.MIN_MAINTAINABILITY_INDEX, Constants.MAX_MAINTAINABILITY_INDEX);
        else
            this.thresholds = new VsCodeMetricsThresholds(minMaintainabilityIndex, maxMaintainabilityIndex);
    }

    public String getReportFiles() {
        return reportFiles;
    }

    public int getMinMaintainabilityIndex() {
        return thresholds.getMinMaintainabilityIndex();
    }

    public int getMaxMaintainabilityIndex() {
        return thresholds.getMaxMaintainabilityIndex();
    }


    public boolean isFailBuild() {
        return failBuild;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        if (StringUtil.isNullOrSpace(reportFiles)) return false;

        final PrintStream logger = listener.getLogger();
        EnvVars env = build.getEnvironment(listener);
        env.overrideAll(build.getBuildVariables());
        String includes = env.expand(reportFiles);

        logger.println("Code Metrics Report path: " + includes);
        FilePath[] reports = CodeMetricsUtil.locateReports(build.getWorkspace(), includes);

        if (reports.length == 0) {
            if (build.getResult().isWorseThan(Result.UNSTABLE)) {
                return true;
            }

            logger.println("Code Metrics Report Not Found.");
            build.setResult((failBuild) ? Result.FAILURE : Result.UNSTABLE);
            return true;
        }

        FilePath metricsFolder = new FilePath(CodeMetricsUtil.getReportDir(build));
        if (!CodeMetricsUtil.saveReports(metricsFolder, reports)) {
            logger.println("Code Metrics Report Convert Error.");
            build.setResult((failBuild) ? Result.FAILURE : Result.UNSTABLE);
            return true;
        }

        VsCodeMetricsBuildAction action = new VsCodeMetricsBuildAction(build, thresholds);
        build.getActions().add(action);

        return true;
    }

    public Action getProjectAction(AbstractProject<?, ?> project) {
        return new VsCodeMetricsProjectAction(project);
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public BuildStepDescriptor<Publisher> getDescriptor() {
         return DESCRIPTOR;
    }

    /**
     * Descriptor should be singleton.
     */
    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        public DescriptorImpl() {
            super(VsCodeMetricsPublisher.class);
        }

        @Override
        public String getDisplayName() {
            return Messages.VsCodeMetricsPublisher_DisplayName();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }
    }
}
