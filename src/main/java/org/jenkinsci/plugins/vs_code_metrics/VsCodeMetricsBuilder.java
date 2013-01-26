package org.jenkinsci.plugins.vs_code_metrics;

import hudson.CopyOnWrite;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.tasks.Builder;
import hudson.tools.ToolInstallation;

public class VsCodeMetricsBuilder extends Builder {

    @Override
    public Descriptor<Builder> getDescriptor() {
         return DESCRIPTOR;
    }

    /**
     * Descriptor should be singleton.
     */
    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    /**
     * @author Yasuyuki Saito
     */
    public static final class DescriptorImpl extends Descriptor<Builder> {

        @CopyOnWrite
        private volatile VsCodeMetricsInstallation[] installations = new VsCodeMetricsInstallation[0];

        DescriptorImpl() {
            super(VsCodeMetricsBuilder.class);
            load();
        }

        public String getDisplayName() {
            return Messages.VsCodeMetricsBuilder_DisplayName();
        }

        public VsCodeMetricsInstallation[] getInstallations() {
            return installations;
        }

        public void setInstallations(VsCodeMetricsInstallation... antInstallations) {
            this.installations = antInstallations;
            save();
        }

        /**
         * Obtains the {@link VsTestInstallation.DescriptorImpl} instance.
         */
        public VsCodeMetricsInstallation.DescriptorImpl getToolDescriptor() {
            return ToolInstallation.all().get(VsCodeMetricsInstallation.DescriptorImpl.class);
        }
    }
}
