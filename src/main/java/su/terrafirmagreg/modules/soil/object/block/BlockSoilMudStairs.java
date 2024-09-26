package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockStairs;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BaseBlockStairs implements ISoilBlock {

  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoilMudStairs(Block model, SoilBlockVariant variant, SoilType type) {
    super(model);

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .sound(SoundType.GROUND)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .oreDict("stairs")
      .oreDict("stairs", "mud", "bricks");
  }

}
