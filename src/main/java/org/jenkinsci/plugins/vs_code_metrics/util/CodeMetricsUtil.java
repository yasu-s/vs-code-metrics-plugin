package org.jenkinsci.plugins.vs_code_metrics.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

import hudson.FilePath;
import hudson.model.AbstractBuild;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import org.jenkinsci.plugins.vs_code_metrics.bean.*;

public abstract class  CodeMetricsUtil {

    private CodeMetricsUtil() {}

    /**
     *
     * @param  path
     * @return
     */
    public static CodeMetrics createCodeMetrics(FilePath path) {
        InputStream stream = null;
        try {
            stream = path.read();

            Digester digester = new Digester();

            digester.addObjectCreate("*/CodeMetricsReport", CodeMetrics.class);

            digester.addObjectCreate("*/Module", Module.class);
            digester.addSetNext("*/Module", "addChild");
            digester.addSetProperties("*/Module", "Name", "name");
            digester.addObjectCreate("*/Module/Metrics/Metric", Metric.class);
            digester.addSetNext("*/Module/Metrics/Metric", "addMetric");
            digester.addSetProperties("*/Module/Metrics/Metric", "Name", "name");
            digester.addSetProperties("*/Module/Metrics/Metric", "Value", "value");

            digester.addObjectCreate("*/Namespace", Namespace.class);
            digester.addSetNext("*/Namespace", "addChild");
            digester.addSetProperties("*/Namespace", "Name", "name");
            digester.addObjectCreate("*/Namespace/Metrics/Metric", Metric.class);
            digester.addSetNext("*/Namespace/Metrics/Metric", "addMetric");
            digester.addSetProperties("*/Namespace/Metrics/Metric", "Name", "name");
            digester.addSetProperties("*/Namespace/Metrics/Metric", "Value", "value");

            digester.addObjectCreate("*/Type", Type.class);
            digester.addSetNext("*/Type", "addChild");
            digester.addSetProperties("*/Type", "Name", "name");
            digester.addObjectCreate("*/Type/Metrics/Metric", Metric.class);
            digester.addSetNext("*/Type/Metrics/Metric", "addMetric");
            digester.addSetProperties("*/Type/Metrics/Metric", "Name", "name");
            digester.addSetProperties("*/Type/Metrics/Metric", "Value", "value");

            digester.addObjectCreate("*/Member", Member.class);
            digester.addSetProperties("*/Member", "Name", "name");
            digester.addObjectCreate("*/Member/Metrics/Metric", Metric.class);
            digester.addSetNext("*/Member/Metrics/Metric", "addMetric");
            digester.addSetProperties("*/Member/Metrics/Metric", "Name", "name");
            digester.addSetProperties("*/Member/Metrics/Metric", "Value", "value");

            CodeMetrics bean = (CodeMetrics)digester.parse(stream);

            return bean;
        } catch (SAXException e) {
            return null;
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
    public static CodeMetrics getCodeMetrics(AbstractBuild<?, ?> build) throws IOException, InterruptedException {
        File reportFolder = getReportDir(build);
        try {
            FilePath[] reports = getReports(reportFolder);

            CodeMetrics total = null;

            for (FilePath report : reports) {
                CodeMetrics bean = createCodeMetrics(report);
                if (bean == null) continue;

                if (total != null) {
                    for (Entry<String, Module> entry : bean.getChildren().entrySet()) {
                        if (!total.getChildren().containsKey(entry.getKey())) {
                            total.addChild(entry.getValue());
                        }
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

}
