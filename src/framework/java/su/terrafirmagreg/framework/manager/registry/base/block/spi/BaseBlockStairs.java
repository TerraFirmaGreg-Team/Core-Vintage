package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

@Getter
public abstract class BaseBlockStairs extends BlockStairs implements IBlockEntry {

  protected final BlockSettings settings;

  protected BaseBlockStairs(Block model) {
    this(BlockSettings.of(model), model);

  }

  protected BaseBlockStairs(BlockSettings settings, Block model) {
    super(model.getDefaultState());

    this.settings = settings;
  }


  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public String getHarvestTool(IBlockState state) {
    return this.settings.getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {
    return this.settings.getHarvestLevel();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
