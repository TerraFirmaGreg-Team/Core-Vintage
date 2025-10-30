package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockLeaves extends BlockLeaves implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockLeaves() {
    this(BlockSettings.of()
      .material(Material.LEAVES));
  }

  public BaseBlockLeaves(BlockSettings settings) {

    this.settings = settings;
    this.leavesFancy = true; // Fast / Fancy graphics works correctly
    this.blockState = this.createBlockState();

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }


  @SideOnly(Side.CLIENT)
  @Override
  public BlockRenderLayer getRenderLayer() {
    /*
     * This is a way to make sure the leave settings are updated.
     * The result of this call is cached somewhere, so it's not that important, but:
     * The alternative would be to use `GameUtils.getGameSettings().fancyGraphics` directly in the 2 relevant methods.
     * It's better to do that than to refer to Blocks.LEAVES, for performance reasons.
     */
    this.leavesFancy = GameUtils.getGameSettings().fancyGraphics;
    return super.getRenderLayer();
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return asItem();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    /*
     * See comment on getRenderLayer()
     */
    this.leavesFancy = GameUtils.getGameSettings().fancyGraphics;
    return true;// super.shouldSideBeRendered(blockState, blockAccess, pos, side);
  }

  @Override
  public BlockPlanks.EnumType getWoodType(int meta) {
    // Unused so return whatever
    return BlockPlanks.EnumType.OAK;
  }

}
