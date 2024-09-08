package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFireBricks extends BaseBlock {

  public BlockFireBricks() {
    super(Settings.of(Material.ROCK));

    getSettings()
            .registryKey("core/fire_bricks")
            .sound(SoundType.STONE)
            .hardness(1.0F)
            .size(Size.SMALL)
            .weight(Weight.LIGHT);
  }
}
