package net.dries007.sharkbark.cellars.items;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.CreativeTabsTFC;

public class ItemIceShard extends ItemBase {

  public ItemIceShard(String name) {
    super(name);
    setCreativeTab(CreativeTabsTFC.CT_MISC);
  }

  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL;
  }

  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }
}
