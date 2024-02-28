package de.mennomax.astikorcarts.item;

import de.mennomax.astikorcarts.init.ModCreativeTabs;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.lib.Constants.MODID_ASTIKORCARTS;

public class ItemWheel extends ItemTFC {
	public ItemWheel() {
		this.setRegistryName(MODID_ASTIKORCARTS, "wheel");
		this.setTranslationKey(this.getRegistryName().toString());
		this.setCreativeTab(ModCreativeTabs.astikor);
	}

	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack itemStack) {
		return Size.NORMAL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack itemStack) {
		return Weight.HEAVY;
	}
}
