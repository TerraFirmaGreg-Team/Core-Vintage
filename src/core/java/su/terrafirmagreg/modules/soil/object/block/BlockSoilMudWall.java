package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudWall extends BaseBlockWall implements IType<SoilType> {

  protected final SoilType type;

  public BlockSoilMudWall(Block model, SoilType type) {
    super(model);

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(model, "wall"))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .oreDict("wall")
      .oreDict("wall", "mud", "bricks");
  }
}
