package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public abstract class BlockSoilMudSlab extends BaseBlockSlab implements IType<SoilType> {

  protected final SoilType type;

  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockSoilMudSlab(SoilType type) {
    super(Settings.of(BlocksSoil.MUD_BRICKS.get(type)));

    this.type = type;

    getSettings()
      .oreDict("slab")
      .oreDict("slab", "mud", "bricks");
  }

  public static class Double extends BlockSoilMudSlab {

    public Double(SoilType type) {
      super(type);

      getSettings()
        .registryKey(type.getRegistryKey("mud_bricks/slab_double"))
        .renderLayer(BlockRenderLayer.CUTOUT);
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

    public Half(SoilType type) {
      super(type);

      this.doubleSlab = BlocksSoil.MUD_BRICKS_SLAB_DOUBLE.get(type);
      this.doubleSlab.halfSlab = this;
      this.halfSlab = this;

      getSettings()
        .registryKey(type.getRegistryKey("mud_bricks/slab"));
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
