package su.terrafirmagreg.modules.soil.object.block.spi;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockFalling;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

import static su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
public abstract class BlockSoil extends BaseBlockFalling implements ISoilEntry {

  protected final SoilType type;

  public BlockSoil(SoilType type) {
    this(type, BlockSettings.of()
      .material(Material.GROUND)
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .hardness(2.0F)
    );
  }

  public BlockSoil(SoilType type, BlockSettings settings) {
    super(settings);

    this.type = type;

    FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL);
  }


}
