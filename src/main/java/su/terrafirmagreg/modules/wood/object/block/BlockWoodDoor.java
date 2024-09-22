package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockDoor;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockWoodDoor extends BaseBlockDoor implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodDoor(WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD));

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .customResource(variant.getCustomResource())
            .ignoresProperties(BlockDoor.POWERED)
            .sound(SoundType.WOOD)
            .oreDict(variant, "wood")
            .oreDict(variant, "wood", type);

    disableStats();
    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

}
