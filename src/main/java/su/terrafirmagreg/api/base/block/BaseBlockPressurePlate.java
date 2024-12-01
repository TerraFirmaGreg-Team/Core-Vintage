package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.BlockPressurePlate;

import lombok.Getter;

@Getter
public abstract class BaseBlockPressurePlate extends BlockPressurePlate implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockPressurePlate(Settings settings, BlockPressurePlate.Sensitivity sensitivityIn) {
    super(settings.getMaterial(), sensitivityIn);

    this.settings = settings;
  }
}
