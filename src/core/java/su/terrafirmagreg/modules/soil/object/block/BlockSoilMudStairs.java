package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BaseBlockStairs implements IType<SoilType> {

  protected final SoilType type;

  public BlockSoilMudStairs(Block model, SoilType type) {
    super(model);

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("mud_bricks/stairs"))
      .sound(SoundType.GROUND)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .oreDict("stairs")
      .oreDict("stairs", "mud", "bricks");
  }

}
