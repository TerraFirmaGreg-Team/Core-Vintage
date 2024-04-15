package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public final class AnnotationUtils {

    private static ASMDataTable asmData;

    private AnnotationUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static ASMDataTable getASMDataTable() {

        return asmData;
    }

    public static void setAnnotationData(ASMDataTable data) {

        asmData = data;
    }

    /**
     * Gets the ASMData for all classes annotated with the annotation class.
     *
     * @param annotation The annotation to search for.
     * @return A set of ASMData for classes with the passed annotation.
     */
    public static <A extends Annotation> Set<ASMData> getData(Class<A> annotation) {

        return getData(asmData, annotation);
    }

    /**
     * Gets the ASMData for all classes annotated with the annotation class.
     *
     * @param table      The ASMDataTable. This is generated from forge an can be accessed from the initialization events.
     * @param annotation The annotation to search for.
     * @return A set of ASMData for classes with the passed annotation.
     */
    public static <A extends Annotation> Set<ASMData> getData(ASMDataTable table, Class<A> annotation) {

        return table.getAll(annotation.getCanonicalName());
    }

    /**
     * Gets all classes annotated with the annotation class.
     *
     * @param annotation The annotation to search for.
     * @return A list of all classes with the passed annotation.
     */
    public static <A extends Annotation> List<Tuple<Class<?>, A>> getAnnotatedClasses(Class<A> annotation) {

        return getAnnotatedClasses(asmData, annotation);
    }

    /**
     * Gets all classes annotated with the annotation class.
     *
     * @param table      The ASMDataTable. This is generated from forge and can be accessed from the initialization events.
     * @param annotation The annotation to search for.
     * @return A list of all classes with the passed annotation.
     */
    public static <A extends Annotation> List<Tuple<Class<?>, A>> getAnnotatedClasses(ASMDataTable table, Class<A> annotation) {

        final List<Tuple<Class<?>, A>> classes = new ArrayList<>();

        for (final ASMData data : getData(table, annotation)) {

            final Class<?> clazz = ClassUtils.getClassFromString(data.getClassName());

            if (clazz != null) {
                classes.add(new Tuple<>(clazz, clazz.getAnnotation(annotation)));
            }
        }

        return classes;
    }

    /**
     * Gets all fields annotated with the annotation class.
     *
     * @param collection The list of classes to search through.
     * @param class1     The annotation to search for.
     * @return A list of all fields with the passed annotation.
     */
    public static <A extends Annotation> List<Field> getAnnotatedFields(Collection<Class<?>> collection, Class<A> class1) {

        final List<Field> fields = new ArrayList<>();

        for (final Class<?> clazz : collection) {

            for (final Field field : clazz.getFields()) {

                if (field.isAnnotationPresent(class1)) {
                    fields.add(field);
                }
            }
        }

        return fields;
    }

    /**
     * Finds all classes annotated with the annotation class. These classes are then instantiated, added to a list, and given to you.
     *
     * @param annotation The class of the annotation you're using to search for.
     * @param instance   The class of the thing you're trying to construct. This should be a shared interface, or parent class.
     * @return A list of all classes annotated with the annotation, as instances.
     */
    public static <T, A extends Annotation> Map<T, A> getAnnotations(Class<A> annotation, Class<T> instance) {

        return getAnnotations(asmData, annotation, instance);
    }

    /**
     * Finds all classes annotated with the annotation class. These classes are then instantiated, added to a list, and given to you.
     *
     * @param table      The ASMDataTable created by Forge. You can get this from most of the main mod loading stage events.
     * @param annotation The class of the annotation you're using to search for.
     * @param instance   The class of the thing you're trying to construct. This should be a shared interface, or parent class.
     * @return A list of all classes annotated with the annotation, as instances.
     */
    public static <T, A extends Annotation> Map<T, A> getAnnotations(ASMDataTable table, Class<A> annotation, Class<T> instance) {

        final Map<T, A> map = new HashMap<>();

        for (final ASMData asmData : getData(table, annotation)) {

            try {

                final Class<?> asmClass = Class.forName(asmData.getClassName());
                final Class<? extends T> asmInstanceClass = asmClass.asSubclass(instance);
                map.put(asmInstanceClass.getDeclaredConstructor().newInstance(), asmInstanceClass.getAnnotation(annotation));
            } catch (final ClassNotFoundException e) {

                // Ignore missing clases, because Forge changed this behaviour to allow these.
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {

                TerraFirmaGreg.LOGGER.warn(e, "Could not load class {}", asmData.getClassName());
            }
        }

        return map;
    }

}
