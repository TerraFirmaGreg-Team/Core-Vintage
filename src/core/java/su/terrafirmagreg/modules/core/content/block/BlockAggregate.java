package su.terrafirmagreg.modules.core.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFalling;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


public class BlockAggregate extends BaseBlockFalling implements IBlockEntry {

  public BlockAggregate() {
    super(BlockSettings.of()
      .material(Material.SAND)
      .sound(SoundType.SAND)
      .hardness(0.4f)
    );
  }
}
