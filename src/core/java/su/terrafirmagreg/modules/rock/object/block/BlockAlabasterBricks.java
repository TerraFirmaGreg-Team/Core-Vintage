package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

public class BlockAlabasterBricks extends BaseBlock {

//  public BlockAlabasterBricks() {
//    this(EnumDyeColor.WHITE);
//
//    getSettings()
//      .registryKey("alabaster/bricks/plain");
//

  /// /    for (var color : EnumDyeColor.values()) { /      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(BRICKS, color), new BlockAlabasterBricks(color)); /    }
//  }
  public BlockAlabasterBricks(EnumDyeColor color) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .mapColor(color)
      .registryKey("alabaster/bricks/" + color.getName())
      .addOreDict("alabaster")
      .addOreDict("alabaster", "bricks")
      .sound(SoundType.STONE)
      .hardness(1.0F)
    );
  }

}
