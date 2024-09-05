package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class InjectorUtils {

  public static void setFinalStaticFieldWithReflection(Class<?> apiClass, String srgName,
      Object value) {
    try {
      Field field = ObfuscationReflectionHelper.findField(apiClass, srgName);
      InjectorUtils.setFinalStatic(field, value);
    } catch (final Exception e) {
      TerraFirmaGreg.LOGGER.catching(e);
    }
  }

  public static void setFinalStatic(Field field, Object newValue) throws Exception {

    field.setAccessible(true);
    final Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, newValue);
  }

  public void inject(Class<?> apiClass, String fieldName, Object value) {

    try {
      Field field = apiClass.getDeclaredField(fieldName);
      field.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(null, value);

    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Unable to inject [%s] into [%s]", fieldName, apiClass), e);
    }
  }

}
