package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;


import static su.terrafirmagreg.modules.rock.init.BlocksRock.SMOOTH;

public class BlockAlabasterSmooth extends BlockRockDecorative {

  public BlockAlabasterSmooth() {
    this(EnumDyeColor.WHITE);

    getSettings()
            .registryKey("rock/alabaster/smooth/plain");

    for (var color : EnumDyeColor.values()) {
      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(SMOOTH, color),
              new BlockAlabasterSmooth(color));
    }
  }

  public BlockAlabasterSmooth(EnumDyeColor color) {
    super(Settings.of(Material.ROCK, color));

    getSettings()
            .registryKey("rock/alabaster/smooth/" + color.getName())
            .oreDict("alabaster")
            .oreDict("alabaster", "smooth");
  }

}
