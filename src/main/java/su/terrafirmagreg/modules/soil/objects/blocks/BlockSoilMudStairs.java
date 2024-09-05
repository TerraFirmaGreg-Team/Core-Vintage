package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockStairs;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BaseBlockStairs implements ISoilBlock {

  private final SoilBlockVariant variant;
  private final SoilType type;

  public BlockSoilMudStairs(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
    super(model.get(type));

    this.variant = variant;
    this.type = type;

    getSettings()
        .registryKey(variant.getRegistryKey(type))
        .sound(SoundType.GROUND)
        .renderLayer(BlockRenderLayer.CUTOUT)
        .oreDict("stairs")
        .oreDict("stairs", "mud", "bricks");

    setHarvestLevel(ToolClasses.PICKAXE, 0);
  }

}
