package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockGrassPath;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

@Getter
public abstract class BaseBlockGrassPath extends BlockGrassPath implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockGrassPath(BlockSettings settings) {
    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }
}
