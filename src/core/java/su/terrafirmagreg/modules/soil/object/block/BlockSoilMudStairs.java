package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.soil.api.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BaseBlockStairs implements ISoilEntry {

  protected final SoilType type;

  public BlockSoilMudStairs(SoilType type) {
    super(BlocksSoil.MUD_BRICKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("mud_bricks/stairs"))
      .renderLayer(BlockRenderLayer.CUTOUT)
      .oreDict("stairs")
      .oreDict("stairs", "mud", "bricks");
  }

}
