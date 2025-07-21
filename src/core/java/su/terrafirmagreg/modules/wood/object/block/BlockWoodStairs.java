package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import lombok.Getter;

@Getter
public class BlockWoodStairs extends BaseBlockStairs implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodStairs(WoodType type) {
    super(BlocksWood.PLANKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("planks/stairs"))
      .customResource(type.getResource("planks/stairs"))
      .harvestLevel(ToolClasses.AXE, 0)
      .fireInfo(5, 20)
      .addOreDict("stairs")
      .addOreDict("stairs", "wood");
  }
}
