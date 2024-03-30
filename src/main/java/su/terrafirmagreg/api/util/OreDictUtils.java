package su.terrafirmagreg.api.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.TerraFirmaGreg;

import java.util.HashSet;
import java.util.Set;

public final class OreDictUtils {

	private OreDictUtils() {
		throw new IllegalAccessError("Utility class");
	}


	public static void register(Item item, Object... parts) {
		register(new ItemStack(item), toString(parts));
	}

	public static void register(Block block, Object... parts) {
		register(new ItemStack(block), toString(parts));
	}

	private static void register(ItemStack itemStack, String parts) {
		if (itemStack != null && !itemStack.isEmpty()) {
			OreDictionary.registerOre(parts, itemStack);
		} else {
			TerraFirmaGreg.LOGGER.error("Failed to register ore dict entry for {}. Another unknown mod is likely responsible.", parts);
		}
	}

	/**
	 * Gets all of the ore dictionary names for an ItemStack.
	 *
	 * @param stack The ItemStack to look at.
	 * @return A set of the ore names.
	 */
	public static Set<String> getOreNames(ItemStack stack) {
		final Set<String> names = new HashSet<>();

		for (final int id : OreDictionary.getOreIDs(stack)) {
			names.add(OreDictionary.getOreName(id));
		}

		return names;
	}

	public static String toString(@NotNull Object... parts) {
		return CaseFormat.UPPER_UNDERSCORE
				.converterTo(CaseFormat.LOWER_CAMEL)
				.convert(Joiner.on('_').skipNulls().join(parts));
	}

	public static boolean contains(ItemStack itemStack, String oreDict) {
		if (!OreDictionary.doesOreNameExist(oreDict)) {
			TerraFirmaGreg.LOGGER.warn("Method called with non-existing name. stack: {} name: {}", itemStack, oreDict);
			return false;
		}

		if (itemStack.isEmpty()) return false;

		int needle = OreDictionary.getOreID(oreDict);
		int[] oreIDs = OreDictionary.getOreIDs(itemStack);

		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < oreIDs.length; i++) {
			if (oreIDs[i] == needle) {
				return true;
			}
		}

		return false;
	}

}
