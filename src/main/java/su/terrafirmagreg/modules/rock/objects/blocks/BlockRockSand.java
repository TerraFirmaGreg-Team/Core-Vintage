package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypes;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockSand extends BlockRockFallable {

  public BlockRockSand(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.SAND), variant, type);

    getSettings()
        .oreDict(isSilica(type) ? "sandSilica" : null)
        .sound(SoundType.SAND);
    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.SANDLIKE);
  }

  private static boolean isSilica(RockType type) {
    return BlockUtils.isType(type, RockTypes.CHERT, RockTypes.GRANITE, RockTypes.QUARTZITE,
        RockTypes.RHYOLITE, RockTypes.PHYLLITE);
  }

}
