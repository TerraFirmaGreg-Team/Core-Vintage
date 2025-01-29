package net.dries007.tfc.objects.items.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;

public class ItemBlockTFC extends ItemBlock implements ICapabilitySize {

  private final ICapabilitySize size;

  public ItemBlockTFC(Block block) {
    this(block, block instanceof ICapabilitySize ? (ICapabilitySize) block : CapabilityProviderSize.getDefault());
  }

  public ItemBlockTFC(Block block, ICapabilitySize size) {
    super(block);

    this.size = size;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return size.getSize(stack);
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return size.getWeight(stack);
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    return size.canStack(stack);
  }

  /**
   * @see net.dries007.tfc.objects.items.ItemTFC#getItemStackLimit(ItemStack)
   */
  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getWeight(stack).stackSize;
  }
}
