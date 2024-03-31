package su.terrafirmagreg.modules.wood.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

@Getter
public class ItemWoodWheel extends ItemBase implements IWoodItem {

	private final WoodItemVariant itemVariant;
	private final WoodType type;


	public ItemWoodWheel(WoodItemVariant itemVariant, WoodType type) {

		this.itemVariant = itemVariant;
		this.type = type;

//        OreDictionaryHelper.onRegister(this, variant.toString());
//        OreDictionaryHelper.onRegister(this, variant.toString(), type.toString());
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack itemStack) {
		return Size.NORMAL;
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack itemStack) {
		return Weight.HEAVY;
	}

	@Override
	public void onModelRegister() {
		ModelUtils.registerInventoryModel(this, getResourceLocation());

	}

	@Override
	public IItemColor getColorHandler() {
		return (s, i) -> this.getType().getColor();
	}
}
