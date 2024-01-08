package BananaFructa.tfcpassingdays;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {
	public static <T> T readDeclaredField(Class<?> targetType, Object target, String name) {
		try {
			Field f = targetType.getDeclaredField(name);
			f.setAccessible(true);
			return (T) f.get(target);
		} catch (Exception err) {
			err.printStackTrace();
			return null;
		}
	}

	public static void writeDeclaredField(Class<?> targetType, Object target, String name, Object value, boolean final_) {
		try {
			Field f = targetType.getDeclaredField(name);
			f.setAccessible(true);
			if (final_) {
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			}
			f.set(target, value);

		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
