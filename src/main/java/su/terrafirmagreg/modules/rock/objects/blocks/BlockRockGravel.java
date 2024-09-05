package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGravel extends BlockRockFallable {

  public BlockRockGravel(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.SAND), variant, type);

    getSettings()
        .sound(SoundType.GROUND);
    //DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
  }

}
