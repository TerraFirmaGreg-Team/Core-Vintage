package su.terrafirmagreg.modules.device.objects.items;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.device.ModuleDeviceConfig;

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

	@Override
	public @NotNull Size getSize(@NotNull ItemStack stack) {
		return Size.NORMAL;
	}

	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack stack) {
		return Weight.HEAVY;
	}

}
