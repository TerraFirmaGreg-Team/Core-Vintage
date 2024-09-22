package su.terrafirmagreg.api.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
@SuppressWarnings("unused")
public final class StringUtils {

  /**
   * Returns a value indicating whether the given string is null or empty.
   */
  public static boolean isNullOrEmpty(@Nullable String string) {
    return org.apache.commons.lang3.StringUtils.isEmpty(string);
  }
}
