package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockFarmland;

import lombok.Getter;

@Getter
public abstract class BaseBlockFarmland extends BlockFarmland implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockFarmland(BlockSettings settings) {
    super();
    this.settings = settings;

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }
}
