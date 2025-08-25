package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockFalling;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityProviderHeat;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.CHERT;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.GRANITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.PHYLLITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.QUARTZITE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.RHYOLITE;

@Getter
public class BlockRockSand extends BaseBlockFalling implements IRockEntry {

  protected final RockType type;

  public BlockRockSand(RockType type) {
    super(BlockSettings.of()
      .material(Material.SAND)
      .hardness(type.getHardness(6f))
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .sound(SoundType.SAND)
      .addOreDict(isSilica(type))
      .addOreDict("sand")
      .addOreDict("sand", type)
      .capability(stack -> CapabilityProviderHeat.of(stack.getTagCompound(), 1, 600))
    );

    this.type = type;

    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.SANDLIKE);
    FallingBlockManager.registerFallable(this, Specification.VERTICAL_AND_HORIZONTAL);
  }

  private static String isSilica(RockType type) {
    return Type.isType(type, CHERT, GRANITE, QUARTZITE, RHYOLITE, PHYLLITE) ? "silica" : null;
  }


}
