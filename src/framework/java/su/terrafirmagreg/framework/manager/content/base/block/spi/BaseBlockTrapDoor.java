package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockTrapDoor;

import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockTrapDoor(BlockSettings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }
}
