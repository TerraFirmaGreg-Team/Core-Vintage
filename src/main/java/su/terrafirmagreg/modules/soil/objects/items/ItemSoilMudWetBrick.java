package su.terrafirmagreg.modules.soil.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

@Getter
public class ItemSoilMudWetBrick extends ItemBase implements ISoilItem {

	private final SoilItemVariant itemVariant;
	private final SoilType type;

	public ItemSoilMudWetBrick(SoilItemVariant itemVariant, SoilType type) {
		this.itemVariant = itemVariant;
		this.type = type;
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, itemVariant);
	}


	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.SMALL; // Stored everywhere
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.LIGHT; // Stacksize = 32
	}
}
