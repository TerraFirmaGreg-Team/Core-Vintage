package de.mennomax.astikorcarts.item;

import net.minecraft.item.ItemStack;

import de.mennomax.astikorcarts.AstikorCarts;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import javax.annotation.Nonnull;

public class ItemWheel extends ItemTFC {

  public ItemWheel() {
    this.setRegistryName(AstikorCarts.MODID, "wheel");
    this.setTranslationKey(this.getRegistryName().toString());
    this.setCreativeTab(CreativeTabsTFC.CT_WOOD);
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.NORMAL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.HEAVY;
  }
}
