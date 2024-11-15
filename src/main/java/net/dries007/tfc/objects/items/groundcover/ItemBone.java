package net.dries007.tfc.objects.items.groundcover;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.groundcover.BlockSurfaceBones;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemBone extends ItemBlockTFC {

  public ItemBone(BlockSurfaceBones block) {
    super(block);
    OreDictionaryHelper.register(this, "bone");
    OreDictionaryHelper.register(this, "bones");
    OreDictionaryHelper.register(this, "dye_white");
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.SMALL;
  }
}
