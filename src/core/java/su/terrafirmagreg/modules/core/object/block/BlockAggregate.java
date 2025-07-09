package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockFalling;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


public class BlockAggregate extends BaseBlockFalling implements IBlockEntry {

  public BlockAggregate() {
    super(Settings.of(Material.SAND));

    getSettings()
      .registryKey("aggregate")
      .sound(SoundType.SAND)
      .hardness(0.4f);
  }
}
