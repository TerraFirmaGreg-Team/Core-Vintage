package su.terrafirmagreg.api.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All of your {@link ModuleBase} classes must be annotated with this to be registered.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleTFG {

    /**
     * The ID of this module. Must be unique within its container.
     */
    String moduleID();

    /**
     * A human-readable name for this module.
     */
    String name();

    /**
     * A list of mod IDs that this module depends on. If any mods specified are not present, the module will not load.
     */
    String[] modDependencies() default {};

    String author() default "";

    String version() default "";

    /**
     * A description of this module in the module configuration file.
     */
    String description() default "";
}
