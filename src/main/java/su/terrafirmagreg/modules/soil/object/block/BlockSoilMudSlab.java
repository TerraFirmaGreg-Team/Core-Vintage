package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockSlab;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public abstract class BlockSoilMudSlab extends BaseBlockSlab implements ISoilBlock {

  protected final SoilBlockVariant variant;
  protected final SoilType type;

  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockSoilMudSlab(Block model, SoilBlockVariant variant, SoilType type) {
    super(Settings.of(Material.GROUND));

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .sound(SoundType.GROUND)
            .harvestLevel(ToolClasses.PICKAXE, model.getHarvestLevel(model.getDefaultState()))
            .oreDict("slab")
            .oreDict("slab", "mud", "bricks");
  }

  public static class Double extends BlockSoilMudSlab {

    public Double(Block model, SoilBlockVariant variant, SoilType type) {
      super(model, variant, type);
    }

    @Override
    public boolean isDouble() {
      return true;
    }

    @Override
    public Double getDoubleSlab() {
      return this;
    }

  }

  public static class Half extends BlockSoilMudSlab {

    public Half(Block model, Block doubleSlab, SoilBlockVariant variant, SoilType type) {
      super(model, variant, type);

      this.doubleSlab = (Double) doubleSlab;
      this.doubleSlab.halfSlab = this;
      this.halfSlab = this;
    }

    @Override
    public boolean isDouble() {
      return false;
    }

    @Override
    public Half getHalfSlab() {
      return this;
    }

  }
}
