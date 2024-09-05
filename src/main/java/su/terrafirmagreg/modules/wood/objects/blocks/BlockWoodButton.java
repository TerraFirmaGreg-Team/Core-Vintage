package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockWoodButton extends BlockButtonWood implements IWoodBlock {

  protected final Settings settings;
  private final WoodBlockVariant variant;
  private final WoodType type;

  public BlockWoodButton(WoodBlockVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.CIRCUITS)
        .hardness(0.5F)
        .sound(SoundType.WOOD)
        .oreDict(variant, "wood")
        .oreDict(variant, "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

}
