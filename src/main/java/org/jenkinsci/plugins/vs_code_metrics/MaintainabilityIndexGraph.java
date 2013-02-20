package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import java.util.Calendar;

import org.jenkinsci.plugins.vs_code_metrics.bean.AbstractBean;

public final class MaintainabilityIndexGraph extends AbstractGraph {

    public MaintainabilityIndexGraph(AbstractBuild<?, ?> build, String[] buildTokens, Calendar timestamp, int defaultW, int defaultH) {
        super(build, buildTokens, timestamp, defaultW, defaultH);
        upperBound = 100;
        valueKey = Messages.ChartLabel_MaintainabilityIndex();
    }

    @Override
    protected int getValue(AbstractBean<?> bean) {
        return Integer.parseInt(bean.getMaintainabilityIndex());
    }

}
