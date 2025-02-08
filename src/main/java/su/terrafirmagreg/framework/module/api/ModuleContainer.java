package su.terrafirmagreg.framework.module.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleContainer {

  /**
   * The ID of the container to associate this module with.
   */
  String containerID();

  boolean enabled() default true;
}
