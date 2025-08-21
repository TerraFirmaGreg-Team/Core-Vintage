package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

public class BlockAlabasterRaw extends BaseBlock {

  public BlockAlabasterRaw() {
    this(EnumDyeColor.WHITE);

    getSettings()
      .registryKey("alabaster/raw/plain");

//    for (var color : EnumDyeColor.values()) {
//      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(RAW, color), new BlockAlabasterRaw(color));
//    }
  }

  public BlockAlabasterRaw(EnumDyeColor color) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .mapColor(color)
      .registryKey("alabaster/raw/" + color.getName())
      .addOreDict("alabaster")
      .addOreDict("alabaster", "raw")
      .sound(SoundType.STONE)
      .hardness(1.0F)
    );

  }

}
