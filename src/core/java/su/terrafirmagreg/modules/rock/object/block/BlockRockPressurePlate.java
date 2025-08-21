package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockPressurePlate;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockRockPressurePlate extends BaseBlockPressurePlate implements IRockEntry {

  public static final String NAME = "pressure_plate";
  protected final RockType type;

  public BlockRockPressurePlate(RockType type) {
    super(Sensitivity.MOBS, BlockSettings.of()
      .material(Material.ROCK)
      .registryKey(type.getRegistryKey(NAME))
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .hardness(0.5f)
      .addOreDict(NAME)
      .addOreDict(NAME, "stone")
      .addOreDict(NAME, "stone", type)
    );

    this.type = type;

  }
}
