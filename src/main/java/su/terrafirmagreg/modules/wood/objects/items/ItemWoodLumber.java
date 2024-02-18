package su.terrafirmagreg.modules.wood.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.ModelManager;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

@Getter
public class ItemWoodLumber extends ItemBase implements IWoodItem {

	private final WoodItemVariant itemVariant;
	private final WoodType type;


	public ItemWoodLumber(WoodItemVariant itemVariant, WoodType type) {
		this.type = type;
		this.itemVariant = itemVariant;

		setMaxDamage(0);

//        OreDictionaryHelper.register(this, variant.toString());
//        OreDictionaryHelper.register(this, variant.toString(), type.toString());
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.SMALL;
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.VERY_LIGHT;
	}

	@Override
	public void onStateMapperRegister() {
		ModelManager.registerItemModel(this, this.getResourceLocation().toString());

	}

	@Override
	public IItemColor getColorHandler() {
		return (s, i) -> this.getType().getColor();
	}
}
