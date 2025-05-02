package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;

import net.minecraft.block.BlockPressurePlate;

import lombok.Getter;

@Getter
public abstract class BaseBlockPressurePlate extends BlockPressurePlate implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockPressurePlate(Settings settings, Sensitivity sensitivityIn) {
    super(settings.getMaterial(), sensitivityIn);

    this.settings = settings;
  }
}
