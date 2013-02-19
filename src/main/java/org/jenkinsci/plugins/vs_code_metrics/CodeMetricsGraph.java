package org.jenkinsci.plugins.vs_code_metrics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Calendar;

import org.jenkinsci.plugins.vs_code_metrics.bean.AbstractBean;
import org.jenkinsci.plugins.vs_code_metrics.bean.CodeMetrics;
import org.jenkinsci.plugins.vs_code_metrics.util.CodeMetricsUtil;
import org.jenkinsci.plugins.vs_code_metrics.util.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleInsets;

import hudson.model.AbstractBuild;
import hudson.util.ColorPalette;
import hudson.util.DataSetBuilder;
import hudson.util.Graph;
import hudson.util.ShiftedCategoryAxis;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;

public class CodeMetricsGraph extends Graph {

    private final AbstractBuild<?, ?> build;
    private final String[] buildTokens;

    public CodeMetricsGraph(AbstractBuild<?, ?> build, String[] buildTokens, Calendar timestamp, int defaultW, int defaultH) {
        super(timestamp, defaultW, defaultH);
        this.build = build;
        this.buildTokens = buildTokens;
    }

    @Override
    protected JFreeChart createGraph() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart(null, null, Messages.ChartLabel_MaintainabilityIndex(), dataset, PlotOrientation.VERTICAL, false, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(null);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.black);

        CategoryAxis domainAxis = new ShiftedCategoryAxis(null);
        plot.setDomainAxis(domainAxis);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setCategoryMargin(0.0);

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperBound(100);
        rangeAxis.setLowerBound(0);

        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseStroke(new BasicStroke(2.0f));
        ColorPalette.apply(renderer);

        plot.setInsets(new RectangleInsets(5.0, 0, 0, 5.0));

        return chart;
    }

    private CategoryDataset createDataset() {
        DataSetBuilder<String, NumberOnlyBuildLabel> builder = new DataSetBuilder<String, NumberOnlyBuildLabel>();

        AbstractBuild<?, ?> lastBuild = build;
        while (lastBuild != null && (lastBuild.getAction(VsCodeMetricsBuildAction.class) != null)) {
            if (!lastBuild.isBuilding()) {
                VsCodeMetricsBuildAction action = lastBuild.getAction(VsCodeMetricsBuildAction.class);
                CodeMetrics metrics = action.getCodeMetrics();
                AbstractBean<?> bean = CodeMetricsUtil.searchBean(metrics, buildTokens);

                if (bean != null) {
                    NumberOnlyBuildLabel buildLabel = new NumberOnlyBuildLabel(lastBuild);
                    int mi = Integer.parseInt(bean.getMaintainabilityIndex());
                    builder.add(mi, Constants.MAINTAINABILITY_INDEX, buildLabel);
                }
            }
            lastBuild = lastBuild.getPreviousBuild();
        }

        return builder.build();
    }
}
