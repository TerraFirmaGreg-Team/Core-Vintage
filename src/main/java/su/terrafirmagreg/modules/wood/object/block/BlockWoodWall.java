package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockWall;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;


import lombok.Getter;

@Getter
public class BlockWoodWall extends BaseBlockWall implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodWall(Block model, WoodBlockVariant variant, WoodType type) {
    super(model);

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .customResource(variant.getCustomResource())
            .ignoresProperties(BlockWall.VARIANT)
            .harvestLevel(ToolClasses.AXE, model.getHarvestLevel(model.getDefaultState()))
            .sound(SoundType.WOOD)
            .oreDict("wall", "wood")
            .oreDict("wall", "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

}
