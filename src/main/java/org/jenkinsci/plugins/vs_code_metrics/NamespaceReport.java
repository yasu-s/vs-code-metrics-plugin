package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.Namespace;
import org.jenkinsci.plugins.vs_code_metrics.bean.Type;

public final class NamespaceReport extends AbstractReport {

   /**
    *
    * @param build
    * @param result
    * @param tokens
    */
   public NamespaceReport(AbstractBuild<?, ?> build, Namespace result, String... tokens) {
       super(build, result.getName(), result);
       setBuildTokens(getName(), tokens);
   }

   @Override
   public Object getReport(String token) {
       if ((getChildren() != null) && getChildren().containsKey(token))
           return new TypeReport(getBuild(), (Type)getChildren().get(token), getBuildTokens());
       else
           return this;
   }

}
