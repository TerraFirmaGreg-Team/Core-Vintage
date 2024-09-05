package su.terrafirmagreg.api.module;

import su.terrafirmagreg.modules.ModuleContainer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All of your {@link ModuleBase} classes must be annotated with this to be registered.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

  /**
   * The ID of this module. Must be unique within its container.
   */
  ModuleContainer moduleID();

  /**
   * Whether this module is the "core" module for its container. Each container must have exactly one core module, which will be loaded before all
   * other modules in the container.
   * <p>
   * Core modules shouldn't have mod dependencies.
   */
  boolean coreModule() default false;

  String author() default "";

  String version() default "";

  /**
   * A description of this module in the module configuration file.
   */
  String description() default "";
}
