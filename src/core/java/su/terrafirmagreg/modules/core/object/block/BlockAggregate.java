package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.block.spi.BaseBlockFalling;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


public class BlockAggregate extends BaseBlockFalling implements IBlockSettings {

  public BlockAggregate() {
    super(Settings.of(Material.SAND));

    getSettings()
      .registryKey("aggregate")
      .oreDict("aggregate")
      .sound(SoundType.SAND)
      .hardness(0.4f);
  }
}
