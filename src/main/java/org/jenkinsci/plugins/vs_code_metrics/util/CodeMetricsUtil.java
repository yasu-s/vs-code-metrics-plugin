package org.jenkinsci.plugins.vs_code_metrics.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXB;

import hudson.FilePath;
import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.*;

public abstract class  CodeMetricsUtil {

    private CodeMetricsUtil() {}

    /**
     *
     * @param  path
     * @return
     * @throws IOException
     */
    public static CodeMetricsReport createCodeMetricsReport(FilePath path) {
        InputStream stream = null;
        try {
            stream = path.read();
            return JAXB.unmarshal(stream, CodeMetricsReport.class);
        } catch (IOException e) {
            return null;
        } finally {
            try { if (stream != null) stream.close(); } catch (Exception e) {}
        }
    }

    /**
     *
     * @param  build
     * @return
     */
    public static File getReportDir(AbstractBuild<?, ?> build) {
        return new File(build.getRootDir(), Constants.REPORT_DIR);
    }

    public static FilePath[] getReports(File file) throws IOException, InterruptedException {
        FilePath path = new FilePath(file);
        if (path.isDirectory()) {
            return path.list("*xml");
        } else {
            FilePath report = new FilePath(new File(path.getName() + ".xml"));
            return report.exists() ? new FilePath[]{report} : new FilePath[0];
        }
    }

    /**
     *
     * @param  build
     * @return
     */
    public static CodeMetricsReport getCodeMetricsReport(AbstractBuild<?, ?> build) throws IOException, InterruptedException {
        File reportFolder = getReportDir(build);
        try {
            FilePath[] reports = getReports(reportFolder);

            CodeMetricsReport total = null;

            for (FilePath report : reports) {
                CodeMetricsReport bean = createCodeMetricsReport(report);
                if (bean == null) continue;

                if (total != null) {
                    for (Target target : bean.getTargets().getTarget()) {
                        total.getTargets().getTarget().add(target);
                    }
                } else {
                    total = bean;
                }
            }

            return total;
        } catch (InterruptedException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @param  metrics
     * @return
     */
    public static String getMaintainabilityIndex(List<Metric> metrics) {
        return getMetricValue(Constants.MAINTAINABILITY_INDEX, metrics);
    }

    /**
     *
     * @param  metrics
     * @return
     */
    public static String getCyclomaticComplexity(List<Metric> metrics) {
        return getMetricValue(Constants.CYCLOMATIC_COMPLEXITY, metrics);
    }

    /**
     *
     * @param  metrics
     * @return
     */
    public static String getClassCoupling(List<Metric> metrics) {
        return getMetricValue(Constants.CLASS_COUPLING, metrics);
    }

    /**
     *
     * @param metrics
     * @return
     */
    public static String getDepthOfInheritance(List<Metric> metrics) {
        return getMetricValue(Constants.DEPTH_OF_INHERITANCE, metrics);
    }

    /**
     *
     * @param metrics
     * @return
     */
    public static String getLinesOfCode(List<Metric> metrics) {
        return getMetricValue(Constants.LINES_OF_CODE, metrics);
    }

    /**
     *
     * @param  name
     * @param  metrics
     * @return
     */
    public static String getMetricValue(String name, List<Metric> metrics) {
        for (Metric metric : metrics) {
            if (metric.getName().equals(name)) {
                return metric.getValue();
            }
        }
        return "";
    }
}
