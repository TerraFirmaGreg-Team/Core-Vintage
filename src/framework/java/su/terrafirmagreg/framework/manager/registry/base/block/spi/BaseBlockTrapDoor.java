package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockTrapDoor;

import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements IBlockEntry {

  protected final Settings settings;

  public BaseBlockTrapDoor(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;
  }
}
