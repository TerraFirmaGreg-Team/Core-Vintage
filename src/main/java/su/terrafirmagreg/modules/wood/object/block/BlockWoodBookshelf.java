package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockBookshelf;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockWoodBookshelf extends BaseBlockBookshelf implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodBookshelf(WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD));
    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .hardness(2.0F)
      .resistance(5.0F)
      .sound(SoundType.WOOD)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .harvestLevel(ToolClasses.AXE, 0)
      .oreDict(variant)
      .oreDict(variant, type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

  
}
