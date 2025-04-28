package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;

import net.minecraft.block.BlockTrapDoor;

import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockTrapDoor(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;
  }
}
