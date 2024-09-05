package su.terrafirmagreg.api.util;

import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringUtils {

  /**
   * Returns a value indicating whether the given string is null or empty.
   */
  public static boolean isNullOrEmpty(@Nullable String string) {
    return org.apache.commons.lang3.StringUtils.isEmpty(string);
  }
}
