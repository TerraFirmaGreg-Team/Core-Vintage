/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items.ceramics;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ItemUnfiredLargeVessel extends ItemPottery {
	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack stack) {
		return Size.VERY_LARGE; // Don't fit in chests
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.VERY_HEAVY; // Stack size = 1
	}
}
