package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockPressurePlate;

import lombok.Getter;

@Getter
public abstract class BaseBlockPressurePlate extends BlockPressurePlate implements IBlockEntry {

  protected final Settings settings;

  public BaseBlockPressurePlate(Settings settings, Sensitivity sensitivityIn) {
    super(settings.getMaterial(), sensitivityIn);

    this.settings = settings;
  }
}
