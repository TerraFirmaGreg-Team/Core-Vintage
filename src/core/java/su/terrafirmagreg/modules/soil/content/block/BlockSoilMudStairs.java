package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BaseBlockStairs implements ISoilEntry {

  protected final SoilType type;

  public BlockSoilMudStairs(SoilType type) {
    super(BlockSettings.of(BlocksSoil.MUD_BRICKS.get(type))
      .registryKey(type.getRegistryKey("mud_bricks/stairs"))
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("stairs")
      .addOreDict("stairs", "mud", "bricks")
    );

    this.type = type;
  }

}
