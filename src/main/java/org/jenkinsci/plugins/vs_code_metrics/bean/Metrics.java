package org.jenkinsci.plugins.vs_code_metrics.bean;

import org.jenkinsci.plugins.vs_code_metrics.util.Constants;

public class Metrics {

    private String name;
    private String maintainabilityIndex;
    private String cyclomaticComplexity;
    private String classCoupling;
    private String depthOfInheritance;
    private String linesOfCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaintainabilityIndex() {
        return maintainabilityIndex;
    }

    public void setMaintainabilityIndex(String maintainabilityIndex) {
        this.maintainabilityIndex = maintainabilityIndex;
    }

    public String getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(String cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public String getClassCoupling() {
        return classCoupling;
    }

    public void setClassCoupling(String classCoupling) {
        this.classCoupling = classCoupling;
    }

    public String getDepthOfInheritance() {
        return depthOfInheritance;
    }

    public void setDepthOfInheritance(String depthOfInheritance) {
        this.depthOfInheritance = depthOfInheritance;
    }

    public String getLinesOfCode() {
        return linesOfCode;
    }

    public void setLinesOfCode(String linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    public void addMetric(Metric metric) {

        if (Constants.MAINTAINABILITY_INDEX.equals(metric.getName()))
            this.maintainabilityIndex = metric.getValue();
        else if (Constants.CYCLOMATIC_COMPLEXITY.equals(metric.getName()))
            this.cyclomaticComplexity = metric.getValue();
        else if (Constants.CLASS_COUPLING.equals(metric.getName()))
            this.classCoupling = metric.getValue();
        else if (Constants.DEPTH_OF_INHERITANCE.equals(metric.getName()))
            this.depthOfInheritance = metric.getValue();
        else if (Constants.LINES_OF_CODE.equals(metric.getName()))
            this.linesOfCode = metric.getValue();

    }
}
