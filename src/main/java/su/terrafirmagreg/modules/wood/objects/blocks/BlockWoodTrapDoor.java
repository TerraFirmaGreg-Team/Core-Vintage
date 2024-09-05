package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockTrapDoor;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockWoodTrapDoor extends BaseBlockTrapDoor implements IWoodBlock {

  private final WoodBlockVariant variant;
  private final WoodType type;

  public BlockWoodTrapDoor(WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD));

    this.variant = variant;
    this.type = type;

    getSettings()
        .sound(SoundType.WOOD)
        .hardness(0.5F)
        .oreDict(variant, "wood")
        .oreDict(variant, "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }
}
