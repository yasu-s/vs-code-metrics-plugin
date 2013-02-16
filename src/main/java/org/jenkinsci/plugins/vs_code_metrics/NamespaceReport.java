package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.Namespace;
import org.jenkinsci.plugins.vs_code_metrics.bean.Type;

public final class NamespaceReport extends AbstractReport {

   /**
    *
    * @param build
    * @param result
    */
   public NamespaceReport(AbstractBuild<?, ?> build, Namespace result) {
       super(build, result.getName(), result);
   }

   @Override
   public Object getReport(String token) {
       if ((getChildren() != null) && getChildren().containsKey(token))
           return new TypeReport(getBuild(), (Type)getChildren().get(token));
       else
           return this;
   }

}
