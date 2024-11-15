package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public abstract class BlockWood extends BaseBlock implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  protected BlockWood(WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD));

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .oreDict(variant)
      .oreDict(variant, type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }
}
