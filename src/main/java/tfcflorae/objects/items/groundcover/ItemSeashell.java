package tfcflorae.objects.items.groundcover;

import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import net.minecraft.item.ItemStack;

import tfcflorae.objects.blocks.groundcover.BlockSurfaceSeashells;
import tfcflorae.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemSeashell extends ItemBlockTFC {

  public ItemSeashell(BlockSurfaceSeashells block) {
    super(block);
    OreDictionaryHelper.register(this, "seashell");
    OreDictionaryHelper.register(this, "seashells");
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }
}
