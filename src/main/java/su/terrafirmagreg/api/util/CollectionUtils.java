package su.terrafirmagreg.api.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@UtilityClass
@SuppressWarnings("unused")
public final class CollectionUtils {

  /**
   * Used because {@link Collections#singletonList(Object)} is immutable
   */
  public static <T> List<T> listOf(T element) {
    List<T> list = new ArrayList<>(1);
    list.add(element);
    return list;
  }

  /**
   * Used because {@link Arrays#asList(Object[])} is immutable
   */
  @SafeVarargs
  public static <T> List<T> listOf(T... elements) {
    List<T> list = new ArrayList<>(elements.length);
    Collections.addAll(list, elements);
    return list;
  }

  public static <K, V> Map<V, K> invertMap(Map<K, V> map) {
    return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
  }

  public static <E> E combine(E[] a) {

    if (a.length == 0) {
      throw new IllegalArgumentException("Zero-length array");
    }

    E result = a[0];

    for (int i = 1; i < a.length; i++) {
      result = CollectionUtils.combine(result, a[i]);
    }

    return result;
  }

  /**
   * Combines two arrays of the same or most general type and returns a new array.
   * <p>
   * <a href="https://stackoverflow.com/a/80503">...</a>
   */
  public static <E> E combine(E a, E b) {

    if (!a.getClass().isArray() || !b.getClass().isArray()) {
      throw new IllegalArgumentException();
    }

    Class<?> resCompType;
    Class<?> aCompType = a.getClass().getComponentType();
    Class<?> bCompType = b.getClass().getComponentType();

    if (aCompType.isAssignableFrom(bCompType)) {
      resCompType = aCompType;

    } else if (bCompType.isAssignableFrom(aCompType)) {
      resCompType = bCompType;

    } else {
      throw new IllegalArgumentException();
    }

    int aLen = Array.getLength(a);
    int bLen = Array.getLength(b);

    @SuppressWarnings("unchecked")
    E result = (E) Array.newInstance(resCompType, aLen + bLen);
    //noinspection SuspiciousSystemArraycopy
    System.arraycopy(a, 0, result, 0, aLen);
    //noinspection SuspiciousSystemArraycopy
    System.arraycopy(b, 0, result, aLen, bLen);

    return result;
  }

  public static String[] copy(String[] toCopy) {

    String[] result = new String[toCopy.length];
    System.arraycopy(toCopy, 0, result, 0, toCopy.length);
    return result;
  }

  public static int[] copy(int[] toCopy) {

    int[] result = new int[toCopy.length];
    System.arraycopy(toCopy, 0, result, 0, toCopy.length);
    return result;
  }

  public static <T> boolean contains(T[] array, T element) {

    return Arrays.asList(array).contains(element);
  }

  public static boolean containsInt(int[] array, int element) {

    for (int arrayElement : array) {

      if (arrayElement == element) {
        return true;
      }
    }
    return false;
  }

  public static int getOrLast(int[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  public static double getOrLast(double[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  public static <T> T getOrLast(T[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  public static <T> T randomElement(T[] array, Random random) {

    return array[random.nextInt(array.length)];
  }
}
