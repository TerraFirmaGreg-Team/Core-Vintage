package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockBed;

import lombok.Getter;

@Getter
public abstract class BaseBlockBed extends BlockBed implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockBed(BlockSettings settings) {
    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
