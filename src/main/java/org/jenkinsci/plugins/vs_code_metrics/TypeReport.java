package org.jenkinsci.plugins.vs_code_metrics;

import hudson.model.AbstractBuild;

import org.jenkinsci.plugins.vs_code_metrics.bean.Type;

public final class TypeReport extends AbstractReport {

   /**
    *
    * @param build
    * @param result
    */
   public TypeReport(AbstractBuild<?, ?> build, Type result) {
       super(build, result.getName(), result);
       setDepthOfInheritance(false);
       setChildUrlLink(false);
   }

   @Override
   public Object getReport(String token) {
       return this;
   }

}
