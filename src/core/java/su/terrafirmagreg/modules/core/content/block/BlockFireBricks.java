package su.terrafirmagreg.modules.core.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFireBricks extends BaseBlock {

  public BlockFireBricks() {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .sound(SoundType.STONE)
      .hardness(1.0F)
      .capability(
        CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT)
      ));
  }
}
