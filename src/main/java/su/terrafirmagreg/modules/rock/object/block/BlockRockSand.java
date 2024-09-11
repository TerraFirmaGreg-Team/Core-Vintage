package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.base.item.BaseItemHeat;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.CHERT;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.GRANITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.PHYLLITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.QUARTZITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.RHYOLITE;

public class BlockRockSand extends BlockRockFallable {

  public BlockRockSand(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.SAND), variant, type);

    getSettings()
            .oreDict(isSilica(type))
            .sound(SoundType.SAND);

    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.SANDLIKE);
  }

  private static String isSilica(RockType type) {
    return BlockUtils.isType(type, CHERT, GRANITE, QUARTZITE, RHYOLITE, PHYLLITE) ? "silica" : null;
  }

  @Override
  public @Nullable BaseItemHeat getItemBlock() {
    return new BaseItemHeat(this, 1, 600);
  }

}
