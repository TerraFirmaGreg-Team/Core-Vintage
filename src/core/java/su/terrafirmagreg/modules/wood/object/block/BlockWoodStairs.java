package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import lombok.Getter;

@Getter
public class BlockWoodStairs extends BaseBlockStairs implements IWoodBlock {

  protected final WoodType type;

  public BlockWoodStairs(WoodType type) {
    super(BlocksWood.PLANKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("stairs/planks"))
      .customResource(type.getResource("stairs/planks"))
      .harvestLevel(ToolClasses.AXE, 0)
      .fireInfo(5, 20)
      .oreDict("stairs")
      .oreDict("stairs", "wood");
  }
}
