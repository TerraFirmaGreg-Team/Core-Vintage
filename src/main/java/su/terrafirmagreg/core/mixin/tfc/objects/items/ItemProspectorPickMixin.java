package su.terrafirmagreg.core.mixin.tfc.objects.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import gregtech.common.blocks.BlockOre;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfc.objects.items.metal.ItemProspectorPick;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(value = ItemProspectorPick.class, remap = false)
@ParametersAreNonnullByDefault
public class ItemProspectorPickMixin extends ItemMetalTool {

  @Mutable
  @Final
  @Shadow
  private static int PROSPECT_RADIUS = 35;

  public ItemProspectorPickMixin(Metal metal, Metal.ItemType type) {
    super(metal, type);
  }

  /**
   * @author SpeeeDCraft
   * @reason Enable to allocate GT ores with Prospect Picks
   */
  @Nonnull
  @Overwrite
  private ItemStack getOreStack(World world, BlockPos pos, IBlockState state, boolean ignoreGrade) {
    if (state.getBlock() instanceof BlockOre) {
      Block block = state.getBlock();

      return block.getPickBlock(state, null, world, pos, null);
    }
    return ItemStack.EMPTY;
  }

}
