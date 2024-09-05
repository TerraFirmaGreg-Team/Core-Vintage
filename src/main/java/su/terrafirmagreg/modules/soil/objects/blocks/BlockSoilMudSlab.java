package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockSlab;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public abstract class BlockSoilMudSlab extends BaseBlockSlab implements ISoilBlock {

  private final SoilBlockVariant variant;
  private final SoilType type;

  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockSoilMudSlab(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
    super(Settings.of(Material.GROUND));

    this.variant = variant;
    this.type = type;

    getSettings()
        .registryKey(variant.getRegistryKey(type))
        .sound(SoundType.GROUND)
        .oreDict("slab")
        .oreDict("slab", "mud", "bricks");

    setHarvestLevel(ToolClasses.PICKAXE,
        model.get(type).getHarvestLevel(model.get(type).getDefaultState()));
  }

  public static class Double extends BlockSoilMudSlab {

    public Double(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
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

    public Half(SoilBlockVariant model, SoilBlockVariant doubleSlab, SoilBlockVariant variant,
        SoilType type) {
      super(model, variant, type);

      this.doubleSlab = (Double) doubleSlab.get(type);
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
