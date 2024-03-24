package su.terrafirmagreg.modules.device.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;

import javax.annotation.Nonnull;

public class ItemSlingAmmo extends ItemBase {

	@Getter
	private final int type;
	private final String name;

	public ItemSlingAmmo(int type, String name) {
		this.type = type;
		this.name = name;
	}


	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack itemStack) {
		return Size.SMALL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack itemStack) {
		return Weight.LIGHT;
	}


	@Override
	public @NotNull String getName() {
		return "device/sling/ammo/" + name;
	}
}
