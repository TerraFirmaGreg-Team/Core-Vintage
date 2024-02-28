package su.terrafirmagreg.api.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import su.terrafirmagreg.TerraFirmaGreg;

import java.util.HashSet;
import java.util.Set;

public final class OreDictUtils {

	private static final Multimap<String, ItemStack> MAP_ORE = HashMultimap.create();

	private OreDictUtils() {
		throw new IllegalAccessError("Utility class");
	}


	public static void init() {
		MAP_ORE.forEach(OreDictionary::registerOre);
		MAP_ORE.clear();
	}

	public static void register(Item item, Object... parts) {
		register(new ItemStack(item), toString(parts));
	}

	public static void register(Block block, Object... parts) {
		register(new ItemStack(block), toString(parts));
	}

	public static void register(ItemStack itemStack, String parts) {
		if (itemStack != null && !itemStack.isEmpty()) {
			MAP_ORE.put(toString(parts), itemStack);
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

	/**
	 * Конвертирует массив из строк в oreDict, передавай в массив строки содержащие только буквы,
	 * регистр значения не имеет, каждый элемент массива будет отформатирован в соответствие с camelCase.
	 */
	public static String toString(Object... parts) {
		if (parts.length > 1) {
			return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, Joiner.on('_').skipNulls().join(parts)
					.replace("/", "_").toUpperCase());
		}

		return (String) parts[0];
	}

	public static boolean contains(ItemStack itemStack, String oreDict) {
		if (!OreDictionary.doesOreNameExist(oreDict)) {
			TerraFirmaGreg.LOGGER.warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", itemStack, oreDict);
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
