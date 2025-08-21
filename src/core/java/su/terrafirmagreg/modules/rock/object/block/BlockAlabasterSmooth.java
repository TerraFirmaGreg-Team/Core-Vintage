package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

public class BlockAlabasterSmooth extends BaseBlock {

  public BlockAlabasterSmooth() {
    this(EnumDyeColor.WHITE);

    getSettings()
      .registryKey("alabaster/smooth/plain");

//    for (var color : EnumDyeColor.values()) {
//      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(SMOOTH, color), new BlockAlabasterSmooth(color));
//    }
  }

  public BlockAlabasterSmooth(EnumDyeColor color) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .mapColor(color)
      .registryKey("alabaster/smooth/" + color.getName())
      .addOreDict("alabaster")
      .addOreDict("alabaster", "smooth")
      .sound(SoundType.STONE)
      .hardness(1.0F)
    );
  }

}
