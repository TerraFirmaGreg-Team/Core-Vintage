package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.helper.LoggingHelper;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@UtilityClass
@SuppressWarnings("unused")
public final class ClassUtils {

  /**
   * A cache of fields in a given class for faster lookup.
   */
  private static final Map<Class<?>, Field[]> FIELD_CACHE = new Object2ObjectOpenHashMap<>();

  private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
  private static final Map<Field, MethodHandle> GETTER_CACHE = new ConcurrentHashMap<>();
  private static final Map<Field, MethodHandle> SETTER_CACHE = new ConcurrentHashMap<>();


  public void processFields(Object target, BiConsumer<Object, Field> fieldProcessor) {
    try {
      final Field[] clFields = ClassUtils.getMutableFields(target.getClass());
      for (final Field field : clFields) {
        fieldProcessor.accept(target, field);
      }
    } catch (final Throwable e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets an array of fields from a class. These arrays will be cached.
   *
   * @param clazz The class to get fields for.
   * @return An array of fields held by the class.
   */
  public static Field[] getFields(Class<?> clazz) {

    return FIELD_CACHE.computeIfAbsent(clazz, aClass -> {
      Field[] fields = aClass.getDeclaredFields();
      Arrays.sort(fields, Comparator.comparing(Field::getName));
      return fields;
    });
  }

  public static Type[] getFieldTypes(Field[] fields) {
    Type[] types = new Type[fields.length];
    for (int i = 0; i < fields.length; i++) {types[i] = fields[i].getGenericType();}
    return types;
  }

  public static MethodHandle unreflectGetter(Field field) {

    return GETTER_CACHE.computeIfAbsent(field, f -> {
      try {
        return LOOKUP.unreflectGetter(f);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });

  }

  public static MethodHandle unreflectSetter(Field field) {
    return SETTER_CACHE.computeIfAbsent(field, f -> {
      try {
        return LOOKUP.unreflectSetter(f);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public static Field[] getMutableFields(Class<?> clazz) {
    List<Field> fields = getFieldList(clazz, ClassUtils::isFieldMutable);
    return fields.toArray(new Field[0]);
  }

  public static List<Field> getFieldList(@Nonnull Class<?> type, Predicate<Field> predicate) {
    List<Field> result = new ArrayList<>();
    Class<?> clazz = type;
    while (clazz != null) {
      Field[] fields = getFields(clazz);
      for (Field field : fields) {if (predicate.test(field)) {result.add(field);}}
      clazz = clazz.getSuperclass();
    }
    return result;
  }


  public static boolean isFieldMutable(Field field) {
    int modifiers = field.getModifiers();
    return !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
  }

  /**
   * Compares the class of an Object with another class. Useful for comparing a TileEntity or Item.
   *
   * @param obj:   The Object to compare.
   * @param clazz: The class to compare the Object to.
   * @return boolean: True if the Object is of the same class as the one provided.
   */
  public static boolean compareObjectToClass(Object obj, Class<?> clazz) {

    return compareClasses(obj.getClass(), clazz);
  }

  /**
   * A basic check to see if two classes are the same. For the classes to be the same, neither can be null, and they must share the same name.
   *
   * @param class1: The first class to compare.
   * @param class2: The second class to compare.
   * @return boolean: True if neither class is null, and both share the same name.
   */
  public static boolean compareClasses(Class<?> class1, Class<?> class2) {

    return class1 != null && class2 != null && class1.getName().equalsIgnoreCase(class2.getName());
  }

  /**
   * Provides a safe way to get a class by its name. This is essentially the same as Class.forName however it will handle any ClassNotFoundException automatically.
   *
   * @param name: The name of the class you are trying to get. Example: java.lang.String
   * @return Class: If a class could be found, it will be returned. Otherwise, null.
   */
  public static Class<?> getClassFromString(String name) {

    try {
      return Class.forName(name);
    } catch (final ClassNotFoundException e) {
      LoggingHelper.LOGGER.warn(e, "Could not load class {} ", name);
      return null;
    }
  }

  @Nullable
  public static <T> T createInstanceOf(Class<T> tClass, String path) {
    Object object = createObjectInstance(path);
    if (object != null) {
      return tClass.cast(object);
    }
    return null;
  }

  @Nullable
  public static Object createObjectInstance(String path) {
    return createObjectInstance(getClassFromString(path));
  }

  public static <T> Optional<T> createObjectInstanceOptional(Class<T> clazz) {
    return Optional.ofNullable(createObjectInstance(clazz));
  }

  @Nullable
  public static <T> T createObjectInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LoggingHelper.LOGGER.warn(e, "Could not create instance of {}", clazz);
    }
    return null;
  }
}
