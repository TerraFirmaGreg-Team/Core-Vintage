package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodCart;

import net.minecraft.world.World;

public class ItemWoodAnimalCart extends ItemWoodSupplyCart {

  public ItemWoodAnimalCart(WoodItemVariant variant, WoodType type) {
    super(variant, type);
  }

  @Override
  public EntityWoodCart newCart(World worldIn) {
    return new EntityWoodAnimalCart(worldIn);
  }
}
