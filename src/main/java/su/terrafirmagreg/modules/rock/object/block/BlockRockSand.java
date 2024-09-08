package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypes;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;


import net.dries007.tfc.objects.items.itemblock.ItemBlockHeat;

import org.jetbrains.annotations.Nullable;

public class BlockRockSand extends BlockRockFallable {

  public BlockRockSand(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.SAND), variant, type);

    getSettings()
            .oreDict(isSilica(type) ? "sandSilica" : null)
            .sound(SoundType.SAND);
    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.SANDLIKE);
  }

  @Override
  public @Nullable Item getItemBlock() {
    return new ItemBlockHeat(this, 1, 600);
  }

  private static boolean isSilica(RockType type) {
    return BlockUtils.isType(type, RockTypes.CHERT, RockTypes.GRANITE, RockTypes.QUARTZITE,
            RockTypes.RHYOLITE, RockTypes.PHYLLITE);
  }

}
