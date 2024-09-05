package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockWoodPressurePlate extends BlockPressurePlate implements IWoodBlock {

  protected final Settings settings;
  private final WoodBlockVariant variant;
  private final WoodType type;

  public BlockWoodPressurePlate(WoodBlockVariant variant, WoodType type) {
    super(Material.WOOD, Sensitivity.EVERYTHING);

    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.WOOD)
        .sound(SoundType.WOOD)
        .hardness(0.5F)
        .oreDict(variant)
        .oreDict(variant, "wood")
        .oreDict(variant, "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }
}
