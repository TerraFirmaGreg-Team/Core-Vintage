package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.modules.soil.api.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import lombok.Getter;

@Getter
public class BlockSoilMudSlab extends BaseBlockSlab implements ISoilEntry {

  protected final SoilType type;

  protected BlockSoilMudSlab halfSlab;
  protected BlockSoilMudSlab doubleSlab;

  public BlockSoilMudSlab(SoilType type) {
    super(BlocksSoil.MUD_BRICKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("mud_bricks/slab_double"))
      .addOreDict("slab")
      .addOreDict("slab", "mud", "bricks");
  }

  @Override
  public boolean isDouble() {
    return true;
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

  }
}
