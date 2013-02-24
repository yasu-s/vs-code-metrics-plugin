package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.Module;
import org.jenkinsci.plugins.vs_code_metrics.bean.Namespace;

public final class ModuleReport extends AbstractReport {

   /**
    *
    * @param build
    * @param result
    * @param tokens
    */
   public ModuleReport(AbstractBuild<?, ?> build, Module result) {
       super(build, result.getName(), result);
       setBuildTokens(getName(), null);
   }

   @Override
   public Object getReport(String token) {
       if ((getChildren() != null) && getChildren().containsKey(token))
           return new NamespaceReport(getBuild(), (Namespace)getChildren().get(token), getBuildTokens());
       else
           return this;
   }

}
