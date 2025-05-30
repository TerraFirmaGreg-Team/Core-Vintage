package su.terrafirmagreg.framework.module.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {


  String version() default "";

  String[] author() default "";


  /**
   * A description of this module in the module configuration file.
   */
  String[] description() default "";

  @Deprecated
  boolean enabled() default true;
}
