package su.terrafirmagreg.api.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IModuleContainer {

  /**
   * The ID of this container. If this is your mod's only container, you should use your mod ID to prevent collisions.
   */
  String getID();

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Info {
  }
}
