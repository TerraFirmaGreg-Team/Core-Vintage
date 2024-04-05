package lyeoj.tfcthings.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemMetalBracing extends ItemTFC {

	public ItemMetalBracing() {
		this.setRegistryName("metal_bracing");
		this.setTranslationKey("metal_bracing");
		this.setCreativeTab(CreativeTabsTFC.CT_METAL);
	}


	@Override
	public @NotNull Size getSize(@NotNull ItemStack itemStack) {
		return Size.SMALL;
	}


	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
		return Weight.LIGHT;
	}
}
