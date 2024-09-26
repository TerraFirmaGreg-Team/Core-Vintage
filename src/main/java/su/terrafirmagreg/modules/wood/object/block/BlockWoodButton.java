package su.terrafirmagreg.modules.wood.object.block;

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
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodButton(WoodBlockVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.CIRCUITS);

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .customResource(variant.getCustomResource())
      .hardness(0.5F)
      .sound(SoundType.WOOD)
      .oreDict(variant, "wood")
      .oreDict(variant, "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

}
