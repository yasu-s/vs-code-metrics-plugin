package org.jenkinsci.plugins.vs_code_metrics.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    public static CodeMetricsReport createCodeMetricsReport(FilePath path) throws IOException {
        InputStream stream = null;
        try {
            stream = path.read();
            return JAXB.unmarshal(stream, CodeMetricsReport.class);
        } catch (IOException e) {
            throw e;
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

            CodeMetricsReport total = new CodeMetricsReport();

            for (FilePath report : reports) {
                CodeMetricsReport bean = createCodeMetricsReport(report);

                for (Target target : bean.getTargets().getTarget()) {
                    total.getTargets().getTarget().add(target);
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
