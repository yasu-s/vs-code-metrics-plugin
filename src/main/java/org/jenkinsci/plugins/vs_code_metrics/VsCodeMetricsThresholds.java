package org.jenkinsci.plugins.vs_code_metrics;

import java.io.Serializable;

public class VsCodeMetricsThresholds implements Serializable {

    private int minMaintainabilityIndex = 0;
    private int maxMaintainabilityIndex = 0;

    public VsCodeMetricsThresholds() {

    }

    public VsCodeMetricsThresholds(int minMaintainabilityIndex, int maxMaintainabilityIndex) {
        this.minMaintainabilityIndex = minMaintainabilityIndex;
        this.maxMaintainabilityIndex = maxMaintainabilityIndex;
    }

    public int getMinMaintainabilityIndex() {
        return minMaintainabilityIndex;
    }

    public void setMinMaintainabilityIndex(int minMaintainabilityIndex) {
        this.minMaintainabilityIndex = minMaintainabilityIndex;
    }

    public int getMaxMaintainabilityIndex() {
        return maxMaintainabilityIndex;
    }

    public void setMaxMaintainabilityIndex(int maxMaintainabilityIndex) {
        this.maxMaintainabilityIndex = maxMaintainabilityIndex;
    }

}
