package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import java.util.Calendar;

import org.jenkinsci.plugins.vs_code_metrics.bean.AbstractBean;

public final class CyclomaticComplexityGraph extends AbstractGraph {

    public CyclomaticComplexityGraph(AbstractBuild<?, ?> build, String[] buildTokens, Calendar timestamp, int defaultW, int defaultH) {
        super(build, buildTokens, timestamp, defaultW, defaultH);
        valueKey = Messages.ChartLabel_CyclomaticComplexity();
    }

    @Override
    protected int getValue(AbstractBean<?> bean) {
        return bean.getCyclomaticComplexity();
    }

}
