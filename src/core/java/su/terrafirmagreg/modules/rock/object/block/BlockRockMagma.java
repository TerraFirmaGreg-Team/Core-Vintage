package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockMagma;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockRockMagma extends BaseBlockMagma implements IRockEntry {


  public static final String NAME = "magma";
  protected final RockType type;

  public BlockRockMagma(RockType type) {
    super(BlockSettings.of()
      .registryKey(type.getRegistryKey(NAME))
      .material(Material.ROCK)
      .mapColor(MapColor.NETHERRACK)
      .lightValue(0.2F)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict(NAME)
      .addOreDict(NAME, type)
    );

    this.type = type;

    FallingBlockManager.registerFallable(this, Specification.COLLAPSABLE_ROCK);
  }
}
