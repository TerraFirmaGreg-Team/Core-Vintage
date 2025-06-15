package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudWall extends BaseBlockWall implements IType<SoilType> {

  protected final SoilType type;

  public BlockSoilMudWall(SoilType type) {
    super(BlocksSoil.MUD_BRICKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("mud_bricks/wall"))
      .renderLayer(BlockRenderLayer.CUTOUT)
      .oreDict("wall")
      .oreDict("wall", "mud", "bricks");
  }
}
