package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockStairs;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import lombok.Getter;

@Getter
public class BlockWoodStairs extends BaseBlockStairs implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
    super(modelBlock.get(type));

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .customResource(variant.getCustomResource())
      .harvestLevel(ToolClasses.AXE, 0)
      .oreDict("stairs")
      .oreDict("stairs", "wood");

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }
}
