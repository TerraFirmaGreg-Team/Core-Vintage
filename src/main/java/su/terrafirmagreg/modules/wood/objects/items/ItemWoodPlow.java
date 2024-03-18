package su.terrafirmagreg.modules.wood.objects.items;

import net.minecraft.world.World;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodPlowCart;

public class ItemWoodPlow extends ItemWoodSupplyCart {

	public ItemWoodPlow(WoodItemVariant itemVariant, WoodType type) {
		super(itemVariant, type);

	}

	@Override
	public EntityWoodCart newCart(World worldIn) {
		return new EntityWoodPlowCart(worldIn);
	}
}
