package org.jenkinsci.plugins.vs_code_metrics.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public final class ReportConverter {

    private boolean initFlg = false;
    private Transformer metricsTransformer;

    /**
     * initialize
     * @throws TransformerConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws ParserConfigurationException
     */
    public void initialize() throws TransformerConfigurationException, TransformerFactoryConfigurationError, ParserConfigurationException {
        if (initFlg) return;
        metricsTransformer = TransformerFactory.newInstance().newTransformer(new StreamSource(this.getClass().getResourceAsStream(Constants.CONVERT_XSLFILE)));
        initFlg = true;
    }

    /**
     *
     * @param metricsFileStream
     * @param outputPath
     * @return
     */
    public boolean convertFile(InputStream metricsFileStream, File outputPath) {
        FileOutputStream os = null;
        try {
            if (!initFlg) initialize();
            os = new FileOutputStream(outputPath);
            metricsTransformer.transform(new StreamSource(metricsFileStream), new StreamResult(os));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try { if (os != null) os.close(); } catch (Exception e) {}
            try { if (metricsFileStream != null) metricsFileStream.close(); } catch (Exception e) {}
        }
    }

}
