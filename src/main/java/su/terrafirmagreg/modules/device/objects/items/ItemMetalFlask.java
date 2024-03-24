package su.terrafirmagreg.modules.device.objects.items;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.device.ModuleDeviceConfig;

import javax.annotation.Nonnull;

public class ItemMetalFlask extends ItemFlask {

	protected static int capacity = ModuleDeviceConfig.ITEMS.WATER_FLASKS.ironCap;
	protected static int drink = 100; //matches amount of water in TFC Jug

	public ItemMetalFlask() {
		super(capacity, drink);
	}

	@Override
	public @NotNull String getName() {
		return "device/flask/metal";
	}

	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack stack) {
		return Size.NORMAL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.HEAVY;
	}

}
