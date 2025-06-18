package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public abstract class BlockWood extends BaseBlock implements IType<WoodType>, IWoodBlock {

  protected final WoodType type;

  protected BlockWood(WoodType type, String variant) {
    super(Settings.of(Material.WOOD));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(type.getResource(variant))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .oreDict(variant);
  }
}
