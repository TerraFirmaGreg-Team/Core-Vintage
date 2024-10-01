package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodLadder extends BlockLadder implements IWoodBlock {

  protected final Settings settings;
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodLadder(WoodBlockVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.CIRCUITS);

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .sound(SoundType.LADDER)
      .oreDict(variant)
      .oreDict(variant, "wood");

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }
}
