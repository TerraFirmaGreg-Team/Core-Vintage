package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

@UtilityClass
@SuppressWarnings("unused")
public final class ClassUtils {

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
   * Provides a safe way to get a class by its name. This is essentially the same as Class.forName however it will handle any ClassNotFoundException
   * automatically.
   *
   * @param name: The name of the class you are trying to get. Example: java.lang.String
   * @return Class: If a class could be found, it will be returned. Otherwise, null.
   */
  public static Class<?> getClassFromString(String name) {

    try {
      return Class.forName(name);
    } catch (final ClassNotFoundException e) {
      TerraFirmaGreg.LOGGER.warn(e, "Could not load class {} ", name);
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

  @Nullable
  public static <T> T createObjectInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      TerraFirmaGreg.LOGGER.warn(e, "Could not create instance of {}", clazz);
    }
    return null;
  }
}
