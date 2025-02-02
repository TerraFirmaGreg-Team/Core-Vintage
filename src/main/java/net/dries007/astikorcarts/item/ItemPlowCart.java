package net.dries007.astikorcarts.item;

import net.minecraft.world.World;

import net.dries007.astikorcarts.entity.AbstractDrawn;
import net.dries007.astikorcarts.entity.EntityPlowCart;

public class ItemPlowCart extends AbstractCartItem {

  public ItemPlowCart() {
    super("plowcart");
  }

  @Override
  public AbstractDrawn newCart(World worldIn) {
    return new EntityPlowCart(worldIn);
  }
}
