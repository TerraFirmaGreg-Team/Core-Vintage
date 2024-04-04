package su.terrafirmagreg.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import org.jetbrains.annotations.NotNull;

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

	public static String getIDName(String string) {
		return String.format(MOD_ID + string);
	}


	/**
	 * Проверяет, включен ли мод JEI (Just Enough Items).
	 *
	 * @return true, если мод JEI включен; в противном случае - false
	 */
	public static boolean isJEIEnabled() {
		return Loader.isModLoaded("jei");
	}


	/**
	 * This is meant to avoid Intellij's warnings about null fields that are injected to at runtime
	 * Use this for things like @ObjectHolder, @CapabilityInject, etc.
	 * AKA - The @Nullable is intentional. If it crashes your dev env, then fix your dev env, not this. :)
	 *
	 * @param <T> anything and everything
	 * @return null, but not null
	 */
	@NotNull
	@SuppressWarnings("ConstantConditions")
	public static <T> T getNull() {
		return null;
	}
}
