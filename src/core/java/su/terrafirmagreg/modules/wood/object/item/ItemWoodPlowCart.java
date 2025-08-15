package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodPlowCart extends ItemWoodCart {

  public ItemWoodPlowCart(WoodType type) {
    super(type, "plow_cart");

  }

  @Override
  public EntityWoodCart createEntity(World worldIn, Entity location, ItemStack itemstack) {
    return new EntityWoodPlowCart(worldIn);
  }
}
