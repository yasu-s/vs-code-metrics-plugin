package org.jenkinsci.plugins.vs_code_metrics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jenkinsci.plugins.vs_code_metrics.util.StringUtil;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

public class VsCodeMetricsPublisher extends Recorder {

    private final String reportFiles;

    @DataBoundConstructor
    public VsCodeMetricsPublisher(String reportFiles) {
        this.reportFiles = reportFiles;
    }

    public String getReportFiles() {
        return reportFiles;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        if (StringUtil.isNullOrSpace(reportFiles)) return false;

        EnvVars env = build.getEnvironment(listener);
        env.overrideAll(build.getBuildVariables());
        String includes = env.expand(reportFiles);

        FilePath[] reports = locateReports(build.getWorkspace(), includes);
        if (reports.length == 0) {
            if (build.getResult().isWorseThan(Result.UNSTABLE)) {
                return true;
            }
            build.setResult(Result.FAILURE);
            return true;
        }

        FilePath metricsFolder = new FilePath(getReportDir(build));
        saveReports(metricsFolder, reports);

        return true;
    }

    private File getReportDir(AbstractBuild<?, ?> build) {
        return new File(build.getRootDir(), "vs_code_metrics");
    }

    private FilePath[] locateReports(FilePath workspace, String includes) throws IOException, InterruptedException {

        try {
            FilePath[] ret = workspace.list(includes);
            if (ret.length > 0) {
                return ret;
            }
        } catch (Exception e) {
        }

        ArrayList<FilePath> files = new ArrayList<FilePath>();
        String parts[] = includes.split("\\s*[;:,]+\\s*");
        for (String path : parts) {
            FilePath src = workspace.child(path);
            if (src.exists()) {
                if (src.isDirectory()) {
                    files.addAll(Arrays.asList(src.list("**/metrics*.xml")));
                } else {
                    files.add(src);
                }
            }
        }
        return files.toArray(new FilePath[files.size()]);
    }

    private void saveReports(FilePath folder, FilePath[] files) throws IOException, InterruptedException {
        folder.mkdirs();
        for (int i = 0; i < files.length; i++) {
            String name = "metrics_" + (i > 0 ? i : "") + ".xml";
            FilePath src = files[i];
            FilePath dst = folder.child(name);
            src.copyTo(dst);
        }
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
            return Messages.VsCodeMetricsInstallation_DisplayName();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }
    }
}
