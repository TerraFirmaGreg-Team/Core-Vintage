package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockGrassPath;

import lombok.Getter;

@Getter
public abstract class BaseBlockGrassPath extends BlockGrassPath implements IBlockEntry {

  protected final Settings settings;

  public BaseBlockGrassPath(Settings settings) {
    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
