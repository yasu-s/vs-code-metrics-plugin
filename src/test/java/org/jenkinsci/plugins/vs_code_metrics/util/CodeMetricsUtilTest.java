package org.jenkinsci.plugins.vs_code_metrics.util;

import static org.junit.Assert.*;

import java.io.File;

import hudson.FilePath;

import org.jenkinsci.plugins.vs_code_metrics.bean.*;
import org.junit.Test;

public class CodeMetricsUtilTest {

    @Test
    public void testCreateCodeMetrics() throws Exception {
        File file = new File(this.getClass().getResource("test_createbean.xml").getPath());
        FilePath path = new FilePath(file);

        CodeMetrics bean = CodeMetricsUtil.createCodeMetrics(path);

        assertNotNull(bean);
        assertEquals(2, bean.getChildren().size());
    }

    @Test
    public void testSaveReports() throws Exception {
        File file = new File(this.getClass().getResource("test_save.xml").getPath());
        FilePath folder = new FilePath(file.getParentFile());
        FilePath[] files = new FilePath[1];
        files[0] = new FilePath(file);

        boolean r = CodeMetricsUtil.saveReports(folder, files);
        assertTrue("saveReports method success.", r);
    }

    @Test
    public void testSearchBean() throws Exception {
        File file = new File(this.getClass().getResource("test_createbean.xml").getPath());
        FilePath path = new FilePath(file);

        CodeMetrics bean = CodeMetricsUtil.createCodeMetrics(path);

        AbstractBean<?> result = CodeMetricsUtil.searchBean(bean, new String[0]);
        assertEquals(bean, result);

        Module module = bean.getChildren().get("CodeMetricsTest.exe");
        result = CodeMetricsUtil.searchBean(bean, new String[] { "CodeMetricsTest.exe" });
        assertEquals(module, result);

        result = CodeMetricsUtil.searchBean(bean, new String[] { "dummy" });
        assertNull(result);

        Namespace namespace = module.getChildren().get("CodeMetricsTest");
        result = CodeMetricsUtil.searchBean(bean, new String[] { "CodeMetricsTest.exe", "CodeMetricsTest" });
        assertEquals(namespace, result);

        Type type = namespace.getChildren().get("Program");
        result = CodeMetricsUtil.searchBean(bean, new String[] { "CodeMetricsTest.exe", "CodeMetricsTest", "Program" });
        assertEquals(type, result);
        assertEquals(2, result.getChildren().size());
        assertEquals(100, type.getMaintainabilityIndex());

    }
}
