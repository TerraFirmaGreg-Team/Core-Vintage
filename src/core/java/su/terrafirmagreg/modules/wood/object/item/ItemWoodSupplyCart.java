package su.terrafirmagreg.modules.wood.object.item;


import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodSupplyCart;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWoodCart;

import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemWoodSupplyCart extends ItemWoodCart {


  public ItemWoodSupplyCart(WoodType type) {
    super(type, "supply_cart");

  }

  public EntityWoodCart newCart(World worldIn) {
    return new EntityWoodSupplyCart(worldIn);
  }
}
