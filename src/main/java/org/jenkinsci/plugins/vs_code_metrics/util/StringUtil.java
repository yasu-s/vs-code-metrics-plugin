package org.jenkinsci.plugins.vs_code_metrics.util;

import java.util.List;

public abstract class StringUtil {

    private StringUtil() {}

    /**
    *
    * @param option
    * @param param
    * @return
    */
   public static String convertArgument(String option, String param) {
       return String.format("/%s:%s", option, param);
   }

   /**
    *
    * @param option
    * @param param
    * @return
    */
   public static String convertArgumentWithQuote(String option, String param) {
       return String.format("/%s:\"%s\"", option, param);
   }

   /**
    *
    * @param value
    * @return
    */
   public static String appendQuote(String value) {
       return String.format("\"%s\"", value);
   }

   /**
    * Null or Space
    * @param value
    * @return
    */
   public static boolean isNullOrSpace(String value) {
       return (value == null || value.trim().length() == 0);
   }

   /**
    *
    * @param args
    * @return
    */
   public static String concatString(List<String> args) {
       StringBuilder buf = new StringBuilder();
       for (String arg : args) {
           if(buf.length() > 0)  buf.append(' ');
           buf.append(arg);
       }
       return buf.toString();
   }
}
