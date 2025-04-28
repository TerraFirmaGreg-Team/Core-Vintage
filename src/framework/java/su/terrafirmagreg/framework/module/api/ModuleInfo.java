package su.terrafirmagreg.framework.module.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

  /**
   * The ID of this module. Must be unique within its container.
   */
  String id();


  String version() default "";

  String[] author() default "";

  /**
   * A list of mod IDs that this module depends on. If any mods specified are not present, the module will not load.
   */
  String[] modDependencies() default {};


  /**
   * A description of this module in the module configuration file.
   */
  String[] description() default "";


  boolean enabled() default true;
}
