package su.terrafirmagreg.modules.wood.objects.items;

import net.minecraft.world.World;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodPlow;

public class ItemWoodPlow extends ItemWoodSupplyCart {

    public ItemWoodPlow(WoodItemVariant variant, WoodType type) {
        super(variant, type);

    }

    @Override
    public EntityWoodCart newCart(World worldIn) {
        return new EntityWoodPlow(worldIn);
    }
}
