package org.jenkinsci.plugins.vs_code_metrics;

import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentSpecific;
import hudson.model.Hudson;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;

/**
* @author Yasuyuki Saito
*/
public final class VsCodeMetricsInstallation extends ToolInstallation implements NodeSpecific<VsCodeMetricsInstallation>, EnvironmentSpecific<VsCodeMetricsInstallation> {

    /** */
    private transient String pathToCodeMetricsPowerTool;

    @DataBoundConstructor
    public VsCodeMetricsInstallation(String name, String home) {
        super(name, home, null);
    }

    public VsCodeMetricsInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
        return new VsCodeMetricsInstallation(getName(), translateFor(node, log));
    }

    public VsCodeMetricsInstallation forEnvironment(EnvVars environment) {
        return new VsCodeMetricsInstallation(getName(), environment.expand(getHome()));
    }

    protected Object readResolve() {
        if (this.pathToCodeMetricsPowerTool != null) {
            return new VsCodeMetricsInstallation(this.getName(), this.pathToCodeMetricsPowerTool);
        }
        return this;
    }

    /**
     * @author Yasuyuki Saito
     */
    @Extension
    public static class DescriptorImpl extends ToolDescriptor<VsCodeMetricsInstallation> {

        public String getDisplayName() {
            return Messages.VsCodeMetricsInstallation_DisplayName();
        }

        @Override
        public VsCodeMetricsInstallation[] getInstallations() {
            return Hudson.getInstance().getDescriptorByType(VsCodeMetricsBuilder.DescriptorImpl.class).getInstallations();
        }

        @Override
        public void setInstallations(VsCodeMetricsInstallation... installations) {
            Hudson.getInstance().getDescriptorByType(VsCodeMetricsBuilder.DescriptorImpl.class).setInstallations(installations);
        }

    }
}
