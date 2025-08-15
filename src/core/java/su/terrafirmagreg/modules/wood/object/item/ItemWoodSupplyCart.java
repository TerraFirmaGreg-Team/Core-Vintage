package su.terrafirmagreg.modules.wood.object.item;


import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodSupplyCart;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemWoodSupplyCart extends ItemWoodCart {


  public ItemWoodSupplyCart(WoodType type) {
    super(type, "supply_cart");

  }

  @Override
  public EntityWoodCart createEntity(World worldIn, Entity location, ItemStack itemstack) {
    return new EntityWoodSupplyCart(worldIn);
  }
}
