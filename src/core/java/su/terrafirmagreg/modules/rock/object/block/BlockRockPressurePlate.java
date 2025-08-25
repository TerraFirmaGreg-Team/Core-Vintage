package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockPressurePlate;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockRockPressurePlate extends BaseBlockPressurePlate implements IRockEntry {
  
  protected final RockType type;

  public BlockRockPressurePlate(RockType type) {
    super(Sensitivity.MOBS, BlockSettings.of()
      .material(Material.ROCK)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .hardness(0.5f)
      .addOreDict("pressure_plate")
      .addOreDict("pressure_plate", "stone")
      .addOreDict("pressure_plate", "stone", type)
    );

    this.type = type;

  }
}
