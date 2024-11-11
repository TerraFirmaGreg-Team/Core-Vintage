package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

import static su.terrafirmagreg.modules.rock.init.BlocksRock.RAW;

public class BlockAlabasterRaw extends BlockRockDecorative {

  public BlockAlabasterRaw() {
    this(EnumDyeColor.WHITE);

    getSettings()
      .registryKey("rock/alabaster/raw/plain");

    for (var color : EnumDyeColor.values()) {
      BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(RAW, color), new BlockAlabasterRaw(color));
    }
  }

  public BlockAlabasterRaw(EnumDyeColor color) {
    super(Settings.of(Material.ROCK, color));

    getSettings()
      .registryKey("rock/alabaster/raw/" + color.getName())
      .oreDict("alabaster")
      .oreDict("alabaster", "raw");
  }

}
