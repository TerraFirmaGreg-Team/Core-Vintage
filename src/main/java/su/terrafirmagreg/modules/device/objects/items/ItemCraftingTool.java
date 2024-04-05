package su.terrafirmagreg.modules.device.objects.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemCraftingTool extends ItemDeviceMisc {
	public ItemCraftingTool(String name, int durability, Size size, Weight weight, Object... oreNameParts) {
		super(name, size, weight, oreNameParts);
		setMaxDamage(durability);
		setMaxStackSize(1);
		setNoRepair();
	}

	@Override
	public boolean canStack(@NotNull ItemStack stack) {
		return false;
	}
}
