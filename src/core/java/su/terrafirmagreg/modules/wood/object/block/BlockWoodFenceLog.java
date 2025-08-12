package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockFence;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodFenceLog extends BaseBlockFence implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodFenceLog(WoodType type) {
    super(Settings.of(Material.WOOD));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("fence_log"))
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .harvestLevel(ToolClasses.AXE, 0)
      .fireInfo(5, 20)
      .addOreDict("fence", "wood")
      .addOreDict("fence", "wood", type);

  }
}
