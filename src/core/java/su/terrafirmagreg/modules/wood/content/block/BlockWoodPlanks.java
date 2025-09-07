package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodPlanks extends BaseBlock implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodPlanks(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey(type.getRegistryKey("planks"))
      .customResource(type.getResource("planks"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .addOreDict("planks")
      .hardness(2.0F)
      .resistance(5.0F)
    );

    this.type = type;
  }
}
