package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockFarmland;

import lombok.Getter;

@Getter
public abstract class BaseBlockFarmland extends BlockFarmland implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockFarmland(BlockSettings settings) {
    super();
    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
