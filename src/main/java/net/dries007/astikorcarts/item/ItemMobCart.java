package net.dries007.astikorcarts.item;

import net.minecraft.world.World;

import net.dries007.astikorcarts.entity.AbstractDrawn;
import net.dries007.astikorcarts.entity.EntityMobCart;

public class ItemMobCart extends AbstractCartItem {

  public ItemMobCart() {
    super("mobcart");
  }

  @Override
  public AbstractDrawn newCart(World worldIn) {
    return new EntityMobCart(worldIn);
  }
}
