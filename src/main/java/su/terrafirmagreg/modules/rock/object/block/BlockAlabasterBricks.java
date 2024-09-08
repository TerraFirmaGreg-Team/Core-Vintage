package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;


import static su.terrafirmagreg.modules.rock.init.BlocksRock.BRICKS;

public class BlockAlabasterBricks extends BlockRockDecorative {

  public BlockAlabasterBricks() {
    this(EnumDyeColor.WHITE);

    getSettings()
            .registryKey("rock/alabaster/bricks/plain");

    for (var color : EnumDyeColor.values()) {
      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(BRICKS, color),
              new BlockAlabasterBricks(color));
    }
  }

  public BlockAlabasterBricks(EnumDyeColor color) {
    super(Settings.of(Material.ROCK, color));

    getSettings()
            .oreDict("alabaster")
            .oreDict("alabaster", "bricks")
            .registryKey("rock/alabaster/bricks/" + color.getName());
  }
}
