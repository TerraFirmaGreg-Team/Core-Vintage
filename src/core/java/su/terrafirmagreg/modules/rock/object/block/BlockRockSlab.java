package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public abstract class BlockRockSlab extends BaseBlockSlab implements IRockEntry {

  protected final RockType type;
  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockRockSlab(Block model, RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)

    );

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("variant"))
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("slab")
      .addOreDict("slab", "stone");

    setHarvestLevel(ToolClasses.PICKAXE, model.getHarvestLevel(model.getDefaultState()));
  }

  public static class Double extends BlockRockSlab {

    public Double(Block model, RockType type) {
      super(model, type);

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

  public static class Half extends BlockRockSlab {

    public Half(Block model, Block doubleSlab, RockType type) {
      super(model, type);

      this.doubleSlab = (Double) doubleSlab;
      this.doubleSlab.halfSlab = this;
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
