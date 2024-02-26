package su.terrafirmagreg.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import static su.terrafirmagreg.Tags.MOD_ID;

public class ModUtils {

	private ModUtils() {

		throw new IllegalAccessError("Utility class");
	}

	/**
	 * Возвращает идентификатор ресурса на основе строки.
	 *
	 * @param string строка идентификатора ресурса
	 * @return идентификатор ресурса
	 */
	public static ResourceLocation getID(String string) {
		return new ResourceLocation(MOD_ID, string);
	}


	/**
	 * Проверяет, включен ли мод JEI (Just Enough Items).
	 *
	 * @return true, если мод JEI включен; в противном случае - false
	 */
	public static boolean isJEIEnabled() {
		return Loader.isModLoaded("jei");
	}
}
