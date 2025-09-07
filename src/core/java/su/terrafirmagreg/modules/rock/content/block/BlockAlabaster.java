package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.api.data.enums.EnumColor;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


public class BlockAlabaster extends BaseBlock {

  public BlockAlabaster(EnumColor color) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .mapColor(color.getDyeColor())
      .addOreDict("alabaster")
      .addOreDict("alabaster", color)
      .sound(SoundType.STONE)
      .hardness(1.0F)
    );

  }

}
