package net.dries007.tfc.objects.items.ceramics;

import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ItemUnfiredMold extends ItemPottery {
	private static final Map<OrePrefix, ItemUnfiredMold> MOLD_STORAGE_MAP = new HashMap<>();
	public final OrePrefix orePrefix;

	public ItemUnfiredMold(OrePrefix orePrefix) {
		this.orePrefix = orePrefix;

		if (MOLD_STORAGE_MAP.put(orePrefix, this) != null) {
			throw new IllegalStateException("There can only be one.");
		}
	}

	public static ItemUnfiredMold get(OrePrefix orePrefix) {
		return MOLD_STORAGE_MAP.get(orePrefix);
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		return
				new TextComponentTranslation(
						"item.tfc.ceramics.unfired.mold.name",
						new TextComponentTranslation("item.material.oreprefix." + orePrefix.name).getFormattedText().replaceFirst(" ", "")
				).getFormattedText();
	}
}
