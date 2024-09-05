package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.BlockTrapDoor;


import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements IBlockSettings {

  protected final Settings settings;

  protected BaseBlockTrapDoor(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;
  }
}
