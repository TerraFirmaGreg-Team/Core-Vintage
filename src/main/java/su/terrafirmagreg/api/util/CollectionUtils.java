package su.terrafirmagreg.api.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
