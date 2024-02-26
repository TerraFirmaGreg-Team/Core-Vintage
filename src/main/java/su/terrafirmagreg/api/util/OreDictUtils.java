package su.terrafirmagreg.api.util;

import net.darkhax.bookshelf.Bookshelf;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

public final class OreDictUtils {

	private OreDictUtils() {
		throw new IllegalAccessError("Utility class");
	}

	private static void registerOre(String name, Item ore) {

		registerOre(name, new ItemStack(ore));
	}

	private static void registerOre(String name, Block ore) {

		registerOre(name, new ItemStack(ore));
	}

	public static void registerOre(String name, ItemStack... ores) {

		for (ItemStack ore : ores) {
			registerOre(name, ore);
		}
	}

	private static void registerOre(String name, ItemStack ore) {

		if (ore != null && !ore.isEmpty()) {

			OreDictionary.registerOre(name, ore);
		} else {

			Bookshelf.LOG.error("Failed to register ore dict entry for {}. Another unknown mod is likely responsible.", name);
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

	public static boolean contains(String oreDict, ItemStack itemStack) {

		if (itemStack.isEmpty()) {
			return false;
		}

		int logWood = OreDictionary.getOreID(oreDict);
		int[] oreIDs = OreDictionary.getOreIDs(itemStack);

		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < oreIDs.length; i++) {

			if (oreIDs[i] == logWood) {
				return true;
			}
		}

		return false;
	}

}
