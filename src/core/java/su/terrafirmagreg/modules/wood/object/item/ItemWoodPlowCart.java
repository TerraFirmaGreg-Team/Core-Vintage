package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWoodCart;

import net.minecraft.world.World;

public class ItemWoodPlowCart extends ItemWoodCart {

  public ItemWoodPlowCart(WoodType type) {
    super(type, "plow_cart");

  }

  @Override
  public EntityWoodCart newCart(World worldIn) {
    return new EntityWoodPlowCart(worldIn);
  }
}
