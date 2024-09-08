package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockWall;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockSoilMudWall extends BaseBlockWall implements ISoilBlock {

  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoilMudWall(SoilBlockVariant modelBlock, SoilBlockVariant variant, SoilType type) {
    super(modelBlock.get(type));

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .sound(SoundType.STONE)
            .renderLayer(BlockRenderLayer.CUTOUT)
            .harvestLevel(ToolClasses.PICKAXE, 0)
            .oreDict("wall")
            .oreDict("wall", "mud", "bricks");
  }
}
