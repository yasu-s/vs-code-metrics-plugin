package org.jenkinsci.plugins.vs_code_metrics.util;

public abstract class Constants {

    private Constants() {}

    /** Report Directory */
    public static final String REPORT_DIR = "vs_code_metrics";

    /** TOOL URL */
    public static final String ACTION_URL = "vs_code_metrics";

    /** TOOL ICON */
    public static final String ACTION_ICON = "graph.gif";


    /** MaintainabilityIndex */
    public static final String MAINTAINABILITY_INDEX = "MaintainabilityIndex";

    /** CyclomaticComplexity */
    public static final String CYCLOMATIC_COMPLEXITY = "CyclomaticComplexity";

    /** ClassCoupling */
    public static final String CLASS_COUPLING = "ClassCoupling";

    /** DepthOfInheritance */
    public static final String DEPTH_OF_INHERITANCE = "DepthOfInheritance";

    /** LinesOfCode */
    public static final String LINES_OF_CODE = "LinesOfCode";


    /** Trend Graph Width */
    public static final int TREND_GRAPH_WIDTH = 400;

    /** Trend Graph Height */
    public static final int TREND_GRAPH_HEIGHT = 200;

    /** Report Graph Width */
    public static final int REPORT_GRAPH_WIDTH = 350;

    /** Report Graph Height */
    public static final int REPORT_GRAPH_HEIGHT = 200;


    /** Min MaintainabilityIndex */
    public static final int MIN_MAINTAINABILITY_INDEX = 10;

    /** Max MaintainabilityIndex */
    public static final int MAX_MAINTAINABILITY_INDEX = 20;


    /** Convert Xsl File */
    public static final String CONVERT_XSLFILE = "metrics-convert.xsl";
}
