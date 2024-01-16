package tfcflorae.objects.items.ceramics;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ItemUnfiredUrn extends ItemPottery {
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
