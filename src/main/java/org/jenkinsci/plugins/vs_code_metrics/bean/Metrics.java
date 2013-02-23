package org.jenkinsci.plugins.vs_code_metrics.bean;

public class Metrics {

    private String name;
    private int maintainabilityIndex;
    private int cyclomaticComplexity;
    private int classCoupling;
    private int depthOfInheritance;
    private int linesOfCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaintainabilityIndex() {
        return maintainabilityIndex;
    }

    public void setMaintainabilityIndex(int maintainabilityIndex) {
        this.maintainabilityIndex = maintainabilityIndex;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public int getClassCoupling() {
        return classCoupling;
    }

    public void setClassCoupling(int classCoupling) {
        this.classCoupling = classCoupling;
    }

    public int getDepthOfInheritance() {
        return depthOfInheritance;
    }

    public void setDepthOfInheritance(int depthOfInheritance) {
        this.depthOfInheritance = depthOfInheritance;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public void setLinesOfCode(int linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

}
