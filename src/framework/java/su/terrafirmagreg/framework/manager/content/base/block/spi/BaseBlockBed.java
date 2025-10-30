package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockBed;

import lombok.Getter;

@Getter
public abstract class BaseBlockBed extends BlockBed implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockBed(BlockSettings settings) {
    this.settings = settings;

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
  }
}
