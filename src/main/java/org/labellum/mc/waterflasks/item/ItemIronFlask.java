package org.labellum.mc.waterflasks.item;


import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import org.labellum.mc.waterflasks.ConfigFlasks;

import javax.annotation.Nonnull;

public class ItemIronFlask extends ItemFlask {

  protected static int CAPACITY = ConfigFlasks.GENERAL.ironCap;
  protected static int DRINK = 100; //matches amount of water in TFC Jug

  public ItemIronFlask() {
    super("iron_flask", CAPACITY, DRINK);
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.NORMAL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.HEAVY;
  }

}
