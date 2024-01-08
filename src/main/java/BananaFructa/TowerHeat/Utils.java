package BananaFructa.TowerHeat;

import java.lang.reflect.Field;

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
}
