package net.dries007.astikorcarts.item;

import net.minecraft.world.World;

import net.dries007.astikorcarts.entity.AbstractDrawn;
import net.dries007.astikorcarts.entity.EntityCargoCart;

public class ItemCargoCart extends AbstractCartItem {

  public ItemCargoCart() {
    super("cargocart");
  }

  @Override
  public AbstractDrawn newCart(World worldIn) {
    return new EntityCargoCart(worldIn);
  }
}
