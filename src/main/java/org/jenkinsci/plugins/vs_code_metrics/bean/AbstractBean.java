package org.jenkinsci.plugins.vs_code_metrics.bean;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBean<CHILD extends Metrics> extends Metrics {

    private Map<String, CHILD> children = new HashMap<String, CHILD>();

    public void addChild(CHILD child) {
        children.put(child.getName(), child);
    }

    public Map<String, CHILD> getChildren() {
        return children;
    }
}
