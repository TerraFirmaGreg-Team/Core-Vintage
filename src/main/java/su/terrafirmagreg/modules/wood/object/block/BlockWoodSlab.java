package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockSlab;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;


import lombok.Getter;

@Getter
public abstract class BlockWoodSlab extends BaseBlockSlab implements IWoodBlock {

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockWoodSlab(Block model, WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD));

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .customResource(variant.getCustomResource())
            .harvestLevel(ToolClasses.AXE, model.getHarvestLevel(model.getDefaultState()))
            .sound(SoundType.WOOD);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

  @Override
  public IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }

  public static class Double extends BlockWoodSlab {

    public Double(Block model, WoodBlockVariant variant, WoodType type) {
      super(model, variant, type);

    }

    @Override
    public boolean isDouble() {
      return true;
    }
  }

  public static class Half extends BlockWoodSlab {

    public Half(Block model, Block doubleSlab, WoodBlockVariant variant, WoodType type) {
      super(model, variant, type);

      this.doubleSlab = (Double) doubleSlab;
      this.doubleSlab.halfSlab = this;
      this.halfSlab = this;

      getSettings()
              .oreDict("slab", "wood")
              .oreDict("slab", "wood", type);

    }

    @Override
    public boolean isDouble() {
      return false;
    }
  }
}
