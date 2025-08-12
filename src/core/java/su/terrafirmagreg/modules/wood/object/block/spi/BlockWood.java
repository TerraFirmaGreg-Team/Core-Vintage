package su.terrafirmagreg.modules.wood.object.block.spi;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public abstract class BlockWood extends BaseBlock implements IWoodEntry {

  protected final WoodType type;

  protected BlockWood(WoodType type, String variant) {
    super(Settings.of(Material.WOOD));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(type.getResource(variant))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .addOreDict(variant);
  }
}
